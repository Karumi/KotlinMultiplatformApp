package com.karumi.gallery.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual fun launchInMain(block: suspend CoroutineScope.() -> Unit) =
  GlobalScope.launch(Dispatchers.Main) { block() }

actual suspend fun <T> async(block: suspend CoroutineScope.() -> T): T =
  withContext(Dispatchers.Default, block)