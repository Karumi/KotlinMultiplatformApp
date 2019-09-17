package com.karumi.gallery.usecase

import com.karumi.gallery.domain.PhotosFlow
import com.karumi.gallery.model.Photos
import kotlinx.coroutines.flow.Flow

class GetPhotos(private val photosFlow: PhotosFlow) {
  suspend operator fun invoke(): Flow<Photos> = photosFlow.get()
}