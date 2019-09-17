package com.karumi.gallery.model

typealias Photos = List<PhotoShot>

data class PhotoShot(
  val id: String,
  val thumbnailUrl: String,
  val authorName: String,
  val numberOfLikes: Int
)