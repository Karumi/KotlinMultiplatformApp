package com.karumi.gallery.data.local

import com.karumi.gallery.data.local.LocalGalleryDb.photosQueries
import com.karumi.gallery.domain.TTLCache
import com.karumi.gallery.model.PhotoShot
import com.karumi.gallery.model.Photos

open class LocalPhotosDataSource(private val cache: TTLCache) {

  open fun insert(photos: Photos) {
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

  open fun getPhotos(): Photos = photosQueries.getAll(::toDomain).executeAsList()

  open fun removeAll() {
    photosQueries.transaction {
      photosQueries.deleteAll()
    }
  }

  open fun isExpired(): Boolean = cache.isExpired()
}