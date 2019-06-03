package com.karumi.dribbble.app.usecase

data class DribbbleShot(
  val id: String,
  val name: String,
  val thumbnailUrl: String,
  val authorName: String
)

class GetAllDribbbleShots {
  operator fun invoke(): List<DribbbleShot> =
    (0..100).map { DribbbleShot("", "Saguaro National Park", "", "Nick Slater") }
}