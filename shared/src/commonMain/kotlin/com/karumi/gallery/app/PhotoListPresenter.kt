package com.karumi.gallery.app

import com.karumi.gallery.logError
import com.karumi.gallery.logInfo
import com.karumi.gallery.model.PhotoShot
import com.karumi.gallery.usecase.GetPhotos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PhotoListPresenter(
  private val view: View,
  private val getAllPhotos: GetPhotos
) {
  companion object {
    private const val TAG = "PhotoListPresenter"
  }

  private lateinit var scope: PresenterCoroutineScope

  fun onCreate() {
    scope = PresenterCoroutineScope(Dispatchers.Main)

    scope.launch {
      view.showLoader()
      getAllPhotos()
        .flowOn(Dispatchers.Default)
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
  }

  fun detachView() {
    scope.viewDetached()
  }

  interface View {
    operator fun plusAssign(shots: List<PhotoShot>)
    fun showLoader()
    fun hideLoader()
    fun onLoadError()
  }
}

private class PresenterCoroutineScope(
  context: CoroutineContext
) : CoroutineScope {

  private var onViewDetachJob = Job()
  override val coroutineContext: CoroutineContext = context + onViewDetachJob

  fun viewDetached() {
    onViewDetachJob.cancel()
  }
}