package com.karumi.gallery

import java.util.logging.Level
import java.util.logging.Logger

actual fun logError(tag: String, message: String) {
    Logger.getLogger(tag).log(Level.SEVERE, message)
}

actual fun logInfo(tag: String, message: String) {
    Logger.getLogger(tag).log(Level.INFO, message)
}

actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "JVM"
}