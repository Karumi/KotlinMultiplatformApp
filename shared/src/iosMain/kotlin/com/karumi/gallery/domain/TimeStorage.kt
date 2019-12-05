package com.karumi.gallery.domain

import kotlin.time.days
import platform.Foundation.NSUserDefaults

actual class TimeStorage {

  companion object {
    private const val TIME_ARG = "time_arg"
    private val TIME_IN_MS_TO_EXPIRE_DATA = 90.days.toLongMilliseconds()
  }

  private val userDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults

  actual fun persistTime(timestamp: Long) {
    userDefaults.setInteger(timestamp, TIME_ARG)
  }

  actual fun getPersistedTimeInMs(): Long = userDefaults.integerForKey(TIME_ARG).let {
    if (it.equals(0)) timeAgo() else it
  }

  private fun timeAgo(): Long = TIME_IN_MS_TO_EXPIRE_DATA
}