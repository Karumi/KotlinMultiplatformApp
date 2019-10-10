package com.karumi.gallery

expect fun logError(tag: String, message: String, error: Throwable? = null)
expect fun logInfo(tag: String, message: String)