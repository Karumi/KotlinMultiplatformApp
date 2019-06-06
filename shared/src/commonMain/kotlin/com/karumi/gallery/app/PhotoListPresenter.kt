package com.karumi.gallery.app

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
    getPhotosJob = launchInMain {
      logInfo(TAG, "Start getting photos")
      val allShots = getAllPhotos()
      view += allShots
      logInfo(TAG, "${allShots.size} photos received")
    }
  }

  interface View {
    operator fun plusAssign(shots: List<PhotoShot>)
  }
}