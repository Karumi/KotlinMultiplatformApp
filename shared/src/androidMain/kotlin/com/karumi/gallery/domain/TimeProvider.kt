package com.karumi.gallery.domain

import android.content.SharedPreferences
import java.util.Calendar

actual class TimeProvider(
  private val sharedPreferences: SharedPreferences
) {
  companion object {
    private const val TIME_ARG = "time_arg"
  }

  actual fun persistTime() {
    sharedPreferences.edit().apply {
      putLong(TIME_ARG, getCurrentTime())
      apply()
    }
  }

  actual fun getPersistedTime(): Long = sharedPreferences.getLong(TIME_ARG, timeAgo())

  actual fun getCurrentTime(): Long = System.currentTimeMillis()

  private fun timeAgo(): Long {
    val cal = Calendar.getInstance()
    cal.set(1969, Calendar.JULY, 21)
    return cal.timeInMillis
  }
}