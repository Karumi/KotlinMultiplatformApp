package com.karumi.gallery.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

expect fun launchInMain(block: suspend CoroutineScope.() -> Unit): Job

expect suspend fun <T> async(block: suspend CoroutineScope.() -> T): T