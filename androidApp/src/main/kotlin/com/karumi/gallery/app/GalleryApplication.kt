package com.karumi.gallery.app

import android.app.Application
import android.content.Context
import com.karumi.gallery.GalleryDb
import com.karumi.gallery.domain.TimeProvider
import com.squareup.sqldelight.android.AndroidSqliteDriver

class GalleryApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    val androidSqliteDriver = AndroidSqliteDriver(
      GalleryDb.Schema, this, "gallerydb"
    )

    val sharedPreferences = getSharedPreferences("com.karumi.gallery", Context.MODE_PRIVATE)
    GalleryInjector().init(androidSqliteDriver, TimeProvider(sharedPreferences))
  }
}