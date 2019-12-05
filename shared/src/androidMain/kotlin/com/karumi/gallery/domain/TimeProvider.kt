package com.karumi.gallery.domain

actual class TimeProvider {

  actual fun getCurrentTime(): Long = System.currentTimeMillis()
}