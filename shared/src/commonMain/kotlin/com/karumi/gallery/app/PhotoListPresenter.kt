package com.karumi.gallery.app

import com.karumi.gallery.logError
import com.karumi.gallery.logInfo
import com.karumi.gallery.model.PhotoShot
import com.karumi.gallery.usecase.GetPhotos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class PhotoListPresenter(
  private val view: View,
  private val getAllPhotos: GetPhotos
) : CoroutineScope {

  override val coroutineContext: CoroutineContext
    get() = job

  companion object {
    private const val TAG = "PhotoListPresenter"
  }

  private val job = Job()

  fun onCreate() = launchInMain {
    view.showLoader()
    getAllPhotos()
      .flowOnBackground()
      .catch {
        logError(TAG, "Load photos error: ${it.message}")
        view.hideLoader()
        view.onLoadError()
      }.collect {
        logInfo(TAG, "${it.size} photos received")
        view.hideLoader()
        view += it
      }
  }

  fun detachView() {
    job.cancel()
  }

  interface View {
    operator fun plusAssign(shots: List<PhotoShot>)
    fun showLoader()
    fun hideLoader()
    fun onLoadError()
  }
}