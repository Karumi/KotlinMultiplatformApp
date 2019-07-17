package com.karumi.gallery.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

actual fun launchInMain(block: suspend CoroutineScope.() -> Unit) =
  GlobalScope.launch(Dispatchers.Main) { block() }

actual fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T =
  kotlinx.coroutines.runBlocking(context, block)