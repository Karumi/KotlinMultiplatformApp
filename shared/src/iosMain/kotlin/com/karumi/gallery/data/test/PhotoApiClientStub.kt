package com.karumi.gallery.data.test

import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.data.getEngine
import com.karumi.gallery.model.Photos
import kotlinx.coroutines.runBlocking

class PhotoApiClientStub(
  private val photos: Photos,
  private val withErrors: Boolean = false
) : PhotosApiClient(getEngine(), "") {

  override suspend fun getPhotos(): Photos = runBlocking {
    throwErroIfNeeded()
    photos
  }

  private fun throwErroIfNeeded() {
    if (withErrors) {
      throw RuntimeException()
    }
  }
}