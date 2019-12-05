package com.karumi.gallery.data.test

import com.karumi.gallery.data.getEngine
import com.karumi.gallery.data.network.PhotosApiClient
import com.karumi.gallery.model.Photos
import kotlinx.coroutines.delay

class PhotoApiClientStub(
  private val stub: PhotosStub
) : PhotosApiClient(getEngine(), "") {

  override suspend fun getPhotos(): Photos =
    when (stub) {
      is PhotosStub.Success -> stub.photos
      is PhotosStub.Error -> throw RuntimeException()
      is PhotosStub.Loading -> {
        delay(10000)
        listOf()
      }
    }
}