package com.karumi.dribbble.data

import com.karumi.dribbble.model.Photo
import com.karumi.dribbble.model.Photos
import kotlinx.serialization.Serializable

@Serializable
data class PhotoEntity(
    val id: String,
    val created_at: String,
    val urls: PhotoUrlsEntity,
    val description: String?,
    val user: UserEntity
)

@Serializable
data class PhotoUrlsEntity(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

@Serializable
data class UserEntity(
    val id: String,
    val name: String,
    val username: String
)

internal fun List<PhotoEntity>.toDomain(): Photos =
    map { it.toDomain() }

internal fun PhotoEntity.toDomain(): Photo = Photo(
    photoUrl = urls.regular,
    title = description ?: "",
    author = user.name
)