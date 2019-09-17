package com.karumi.gallery

import android.util.Log

actual fun logError(tag: String, message: String, error: Throwable?) {
  Log.e(tag, message, error)
}

actual fun logInfo(tag: String, message: String) {
  Log.i(tag, message)
}