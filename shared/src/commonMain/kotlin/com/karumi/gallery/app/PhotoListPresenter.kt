package com.karumi.gallery.app

import com.karumi.gallery.logError
import com.karumi.gallery.logInfo
import com.karumi.gallery.model.PhotoShot
import com.karumi.gallery.usecase.GetPhotos
import kotlinx.coroutines.Job

class PhotoListPresenter(
  private val view: View,
  private val getAllPhotos: GetPhotos
) {

  companion object {
    private val TAG = PhotoListPresenter::class.simpleName!!
  }

  private var getPhotosJob: Job? = null

  fun onCreate() {
    view.showLoader()
    try {
      getPhotosJob = launchInMain {
        logInfo(TAG, "Start getting photos")

        val allShots = getAllPhotos()
        view += allShots
        logInfo(TAG, "${allShots.size} photos received")
      }
    } catch (ex: Exception) {
      logError(TAG, "Load photos error: ${ex.message}")
      view.onLoadError()
    } finally {
      view.hideLoader()
    }
  }

  fun detachView() {
    getPhotosJob?.cancel()
  }

  interface View {
    operator fun plusAssign(shots: List<PhotoShot>)
    fun showLoader()
    fun hideLoader()
    fun onLoadError()
  }
}