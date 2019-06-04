package com.karumi.gallery.app

import com.karumi.gallery.app.usecase.PhotoShot
import com.karumi.gallery.app.usecase.GetAllPhotos

class PhotoListPresenter(
  private val view: View,
  private val getAllPhotos: GetAllPhotos
) {

  fun onCreate() {
    val allShots = getAllPhotos()
    view += allShots
  }

  interface View {
    operator fun plusAssign(shots: List<PhotoShot>)
  }
}