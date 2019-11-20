package com.karumi.gallery.test

import com.karumi.gallery.app.InjectionModule
import com.karumi.gallery.data.local.LocalPhotosDataSource
import com.karumi.gallery.data.network.PhotosApiClient

class PhotoListStub(
  private val apiClient: PhotosApiClient,
  private val localDataSource: LocalPhotosDataSource
) : InjectionModule() {
  override fun getPhotosApiClient(): PhotosApiClient = apiClient
  override fun getLocalPhotosDataSource(): LocalPhotosDataSource = localDataSource
}