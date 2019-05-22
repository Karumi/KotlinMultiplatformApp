package com.karumi.dribbble

actual fun logError(tag: String, message: String) {
}

actual fun logInfo(tag: String, message: String) {
}

actual class Sample {
    actual fun checkMe() = 7
}

actual object Platform {
    actual val name: String = "iOS"
}