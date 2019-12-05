package com.karumi.gallery.data.test

import com.karumi.gallery.data.local.LocalPhotosDataSource
import com.karumi.gallery.domain.TTLCache
import com.karumi.gallery.domain.TimeProvider
import com.karumi.gallery.domain.TimeStorage
import com.karumi.gallery.model.Photos
import kotlin.time.minutes

class PhotoDataSourceStub(
  private val stub: PhotosStub = PhotosStub.Loading,
  private val isExpired: Boolean = true
) : LocalPhotosDataSource(TTLCache(TimeStorage(), TimeProvider(), 2.minutes)) {

  override fun getPhotos(): Photos =
    when (stub) {
      is PhotosStub.Success -> stub.photos
      is PhotosStub.Error -> throw RuntimeException()
      is PhotosStub.Loading -> emptyList()
    }

  override fun isExpired(): Boolean = isExpired
}