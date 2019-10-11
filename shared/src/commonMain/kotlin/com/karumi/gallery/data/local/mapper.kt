package com.karumi.gallery.data.local

import com.karumi.gallery.model.PhotoShot

internal fun toDomain(
  id: String,
  thumbnail_url: String,
  author_name: String,
  number_of_likes: Int? = 0
): PhotoShot = PhotoShot(id, thumbnail_url, author_name, number_of_likes!!)