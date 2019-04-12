package com.karumi.dribbble.usecase

import com.karumi.dribbble.data.PhotosNetworkDataSource
import com.karumi.dribbble.model.Photos

class GetPhotos(private val photosNetworkDataSource: PhotosNetworkDataSource) {
    suspend operator fun invoke(): Photos = photosNetworkDataSource.getPhotos()
}