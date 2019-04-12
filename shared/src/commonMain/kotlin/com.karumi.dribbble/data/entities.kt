package com.karumi.dribbble.data

import com.karumi.dribbble.model.Photo
import com.karumi.dribbble.model.Photos
import kotlinx.serialization.Serializable

@Serializable
data class PhotosResponse(
    val photos: PhotosEntity,
    val stat: String
)

@Serializable
data class PhotosEntity(
    val page: Int,
    val pages: String,
    val perpage: Int,
    val total: String,
    val photo: List<PhotoEntity>
)

@Serializable
data class PhotoEntity(
    val id: String,
    val ownername: String,
    val secret: String,
    val server: String,
    val title: String
)

private const val STATIC_FLICKR = "https://live.staticflickr.com"

internal fun PhotosResponse.toDomain(): Photos =
    photos.photo.map { it.toDomain() }

internal fun PhotoEntity.toDomain(): Photo = Photo(
    photoUrl = "$STATIC_FLICKR/$server/${id}_$secret.jpg",
    title = title,
    author = ownername
)