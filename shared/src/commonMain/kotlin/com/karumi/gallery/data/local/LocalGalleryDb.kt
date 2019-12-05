package com.karumi.gallery.data.local

import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.concurrency.value
import co.touchlab.stately.freeze
import com.karumi.gallery.GalleryDb
import com.karumi.gallery.PhotosQueries
import com.squareup.sqldelight.db.SqlDriver

object LocalGalleryDb {
  private val driverRef = AtomicReference<SqlDriver?>(null)
  private val dbRef = AtomicReference<GalleryDb?>(null)

  internal fun dbSetup(driver: SqlDriver) {
    driverRef.value = driver.freeze()
    dbRef.value = GalleryDb(driver).freeze()
    instance.photosQueries
  }

  internal val instance: GalleryDb
    get() = dbRef.value!!

  val photosQueries: PhotosQueries
    get() = instance.photosQueries
}