package com.karumi.gallery.domain

import android.content.SharedPreferences
import java.util.Calendar

actual class TimeStorage(
  private val sharedPreferences: SharedPreferences
) {
  companion object {
    private const val TIME_ARG = "time_arg"
  }

  actual fun persistTime(timestamp: Long) {
    sharedPreferences.edit().run {
      putLong(TIME_ARG, timestamp)
      apply()
    }
  }

  actual fun getPersistedTimeInMs(): Long = sharedPreferences.getLong(TIME_ARG, timeAgo())

  private fun timeAgo(): Long {
    val cal = Calendar.getInstance()
    cal.set(1969, Calendar.JULY, 21)
    return cal.timeInMillis
  }
}