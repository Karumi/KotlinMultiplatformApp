package com.karumi.dribbble.app.usecase

data class Shot(
        val id: String,
        val name: String,
        val thumbnailUrl: String,
        val authorName: String
)

class GetAllDribbbleShots {
  operator fun invoke(): List<Shot> =
          (0..100).map { Shot("", "", "Saguaro National Park", "Nick Slater") }
}