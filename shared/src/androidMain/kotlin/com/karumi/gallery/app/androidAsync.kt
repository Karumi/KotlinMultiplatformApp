package com.karumi.gallery.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

actual fun CoroutineScope.launchInMain(block: suspend CoroutineScope.() -> Unit) =
  launch(Dispatchers.Main) { block() }

actual fun <T> Flow<T>.flowOnBackground(): Flow<T> = flowOn(Dispatchers.Default)

actual fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T =
  kotlinx.coroutines.runBlocking(context, block)