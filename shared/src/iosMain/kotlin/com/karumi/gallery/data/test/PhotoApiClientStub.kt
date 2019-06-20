package com.karumi.gallery.data.test

import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.data.getEngine
import com.karumi.gallery.model.Photos
import kotlinx.coroutines.runBlocking

class PhotoApiClientStub(private val photos: Photos) : PhotosApiClient(getEngine(), "") {

  override suspend fun getPhotos(): Photos = runBlocking {
    photos
  }
}