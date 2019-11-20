package com.karumi.gallery.domain

expect class TimeStorage {
  fun persistTime(timestamp: Long)
  fun getPersistedTime(): Long
}