package com.karumi.gallery.domain

expect class TimeProvider {

  fun persistTime()
  fun getPersistedTime(): Long
  fun getCurrentTime(): Long
}