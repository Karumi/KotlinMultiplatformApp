package com.karumi.gallery

import platform.Foundation.NSLog

actual fun logError(tag: String, message: String) {
  NSLog("$tag: $message")
}

actual fun logInfo(tag: String, message: String) {
  NSLog("$tag: $message")
}