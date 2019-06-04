package com.karumi.gallery

import android.util.Log

actual fun logError(tag: String, message: String) {
    Log.e(tag, message)
}

actual fun logInfo(tag: String, message: String) {
    Log.i(tag, message)
}

actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "Android"
}