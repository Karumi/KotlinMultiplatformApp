package com.karumi.dribbble.usecase

import com.karumi.dribbble.data.PhotosApiClient
import com.karumi.dribbble.model.Photos

class GetPhotos(private val photosApiClient: PhotosApiClient) {
    suspend operator fun invoke(): Photos = photosApiClient.getPhotos()
}