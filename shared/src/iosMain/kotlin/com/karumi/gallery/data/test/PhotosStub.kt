package com.karumi.gallery.data.test

import com.karumi.gallery.model.Photos

sealed class PhotosStub {
  data class Success(val photos: Photos) : PhotosStub()
  object Error : PhotosStub()
  object Loading : PhotosStub()
}