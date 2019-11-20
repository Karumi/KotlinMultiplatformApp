package com.karumi.gallery.domain

import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.posix.gettimeofday
import platform.posix.timeval

actual class TimeProvider {

  actual fun getCurrentTime(): Long = memScoped {
    val timeVal = alloc<timeval>()
    gettimeofday(timeVal.ptr, null)
    val sec = timeVal.tv_sec
    val usec = timeVal.tv_usec
    ((sec * 1_000L) + (usec / 1_000L))
  }
}