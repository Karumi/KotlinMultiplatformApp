package com.karumi.gallery.data

import com.karumi.gallery.model.PhotoShot
import com.karumi.gallery.model.Photos
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoEntity(
  val id: String,
  val color: String,
  val created_at: String,
  val urls: PhotoUrlsEntity,
  val description: String?,
  val likes: Int,
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
data class AvatarUrlsEntity(
  val large: String,
  val medium: String,
  val small: String
)

@Serializable
data class UserEntity(
  val id: String,
  val name: String,
  val username: String,
  val bio: String?,
  @SerialName("profile_image")
  val profileImage: AvatarUrlsEntity
)

internal fun List<PhotoEntity>.toDomain(): Photos =
  map { it.toDomain() }

internal fun PhotoEntity.toDomain(): PhotoShot = PhotoShot(
  id = id,
  authorName = user.name,
  thumbnailUrl = urls.thumb,
  numberOfLikes = likes
)