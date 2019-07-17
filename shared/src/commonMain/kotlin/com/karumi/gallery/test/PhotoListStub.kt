package com.karumi.gallery.test

import com.karumi.gallery.app.InjectionModule
import com.karumi.gallery.data.PhotosApiClient

class PhotoListStub(private val apiClient: PhotosApiClient) : InjectionModule() {
  override fun getPhotosApiClient(): PhotosApiClient = apiClient
}