package com.karumi.gallery.domain

import platform.Foundation.NSUserDefaults

actual class TimeStorage {

  companion object {
    private const val TIME_ARG = "time_arg"
    private const val SEVENTIES_YEAR = 14745600L
  }

  private val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults

  actual fun persistTime(timestamp: Long) {
    delegate.setInteger(timestamp, TIME_ARG)
  }

  actual fun getPersistedTimeInMs(): Long = delegate.integerForKey(TIME_ARG).let {
    if (it.equals(0)) timeAgo() else it
  }

  private fun timeAgo(): Long = SEVENTIES_YEAR
}