package com.karumi.gallery.usecase

import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.model.Photos

class GetPhotos(private val photosApiClient: PhotosApiClient) {
    suspend operator fun invoke(): Photos = photosApiClient.getPhotos()
}