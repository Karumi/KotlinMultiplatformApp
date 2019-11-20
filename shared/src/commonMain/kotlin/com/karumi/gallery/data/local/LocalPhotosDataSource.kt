package com.karumi.gallery.data.local

import com.karumi.gallery.data.local.LocalGalleryDb.photosQueries
import com.karumi.gallery.domain.TTLCache
import com.karumi.gallery.model.PhotoShot
import com.karumi.gallery.model.Photos

class LocalPhotosDataSource(private val cache: TTLCache) {

  fun insert(photos: Photos) {
    photos.forEach(::insert)
  }

  private fun insert(photos: PhotoShot) {
    photosQueries.transaction {
      photosQueries.insertPhoto(
        photos.id,
        photos.thumbnailUrl,
        photos.authorName,
        photos.numberOfLikes
      )
    }
    cache.persistTime()
  }

  fun getPhotos(): Photos = photosQueries.getAll(::toDomain).executeAsList()

  fun removeAll() {
    photosQueries.transaction {
      photosQueries.deleteAll()
    }
  }

  fun isExpired(): Boolean = cache.isExpired()
}