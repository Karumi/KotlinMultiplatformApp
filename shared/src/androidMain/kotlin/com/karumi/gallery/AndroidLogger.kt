package com.karumi.gallery

import android.util.Log

actual fun logError(tag: String, message: String) {
    Log.e(tag, message)
}

actual fun logInfo(tag: String, message: String) {
    Log.i(tag, message)
}