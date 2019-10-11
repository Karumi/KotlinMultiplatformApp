package com.karumi.gallery.domain

import com.karumi.gallery.data.local.LocalPhotosDataSource
import com.karumi.gallery.data.network.PhotosApiClient
import com.karumi.gallery.logInfo
import com.karumi.gallery.model.Photos
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.time.Duration
import kotlin.time.seconds

class PhotosFlow(
  private val photosApiClient: PhotosApiClient,
  private val localPhotosDataSource: LocalPhotosDataSource
) {

  private companion object {
    val updateInterval = 5.seconds
    private const val TAG = "PhotoFlow"
  }

  suspend fun get(): Flow<Photos> =
    timer(repeatEvery = updateInterval)
      .map { getPhotos() }

  private suspend fun getPhotos(): Photos =
    if (localPhotosDataSource.isExpired()) {
      logInfo(TAG, "Getting from internet")
      photosApiClient.getPhotos().also { photos ->
        localPhotosDataSource.removeAll()
        localPhotosDataSource.insert(photos)
      }
    } else {
      logInfo(TAG, "Getting from local database")
      localPhotosDataSource.getPhotos()
    }
}

private fun timer(
  delay: Duration = Duration.ZERO,
  repeatEvery: Duration
) = flow {
  delay(delay.toLongMilliseconds())
  while (true) {
    emit(Unit)
    delay(repeatEvery.toLongMilliseconds())
  }
}