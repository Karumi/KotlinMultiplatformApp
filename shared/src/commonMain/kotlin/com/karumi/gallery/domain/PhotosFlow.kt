package com.karumi.gallery.domain

import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.model.Photos
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.time.Duration
import kotlin.time.seconds

class PhotosFlow(private val photosApiClient: PhotosApiClient) {

  private companion object {
    val updateInterval = 5.seconds
  }

  suspend fun get(): Flow<Photos> =
    timer(repeatEvery = updateInterval)
      .map { photosApiClient.getPhotos() }
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