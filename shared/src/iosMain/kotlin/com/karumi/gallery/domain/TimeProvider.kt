package com.karumi.gallery.domain
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.Foundation.NSUserDefaults
import platform.posix.gettimeofday
import platform.posix.timeval

actual class TimeProvider {

  companion object {
    private const val TIME_ARG = "time_arg"
    private const val THREE_MONTHS_IN_MILLIS = 259200
  }

  private val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults

  actual fun persistTime() {
    val timestampMillis = getCurrentTime()
    delegate.setInteger(timestampMillis, TIME_ARG)
  }

  actual fun getPersistedTime(): Long = delegate.integerForKey(TIME_ARG).let {
    if (it.equals(0)) timeAgo() else it
  }

  actual fun getCurrentTime(): Long = memScoped {
    val timeVal = alloc<timeval>()
    gettimeofday(timeVal.ptr, null)
    val sec = timeVal.tv_sec
    val usec = timeVal.tv_usec
    ((sec * 1_000L) + (usec / 1_000L))
  }

  private fun timeAgo(): Long = getCurrentTime() - THREE_MONTHS_IN_MILLIS
}