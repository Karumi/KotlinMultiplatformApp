package com.karumi.gallery.app.usecase

data class PhotoShot(
  val id: String,
  val name: String,
  val thumbnailUrl: String,
  val authorName: String
)

class GetAllPhotos {
  operator fun invoke(): List<PhotoShot> =
    (0..100).map { PhotoShot("", "Saguaro National Park", "", "Nick Slater") }
}