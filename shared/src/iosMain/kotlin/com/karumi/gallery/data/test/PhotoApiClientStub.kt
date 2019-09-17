package com.karumi.gallery.data.test

import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.data.getEngine
import com.karumi.gallery.model.Photos
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class PhotoApiClientStub(
  private val stub: Stub
) : PhotosApiClient(getEngine(), "") {

  override suspend fun getPhotos(): Photos =
    when (stub) {
      is Stub.Success -> stub.photos
      is Stub.Error -> throw RuntimeException()
      is Stub.Loading -> {
        delay(10000)
        listOf()
      }
    }

  sealed class Stub {
    data class Success(val photos: Photos) : Stub()
    object Error : Stub()
    object Loading : Stub()
  }
}
