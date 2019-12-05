package com.karumi.gallery.domain

expect class TimeProvider {
  fun getCurrentTime(): Long
}