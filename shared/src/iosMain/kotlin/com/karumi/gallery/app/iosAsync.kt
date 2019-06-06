package com.karumi.gallery.app

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext

actual fun launchInMain(block: suspend CoroutineScope.() -> Unit): Job =
  GlobalScope.launch(MainDispatcher) { block() }

actual suspend fun <T> async(block: suspend CoroutineScope.() -> T): T =
  withContext(Dispatchers.Default, block)

private val MainDispatcher: CoroutineDispatcher = NsQueueDispatcher(dispatch_get_main_queue())

internal class NsQueueDispatcher(
  private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    dispatch_async(dispatchQueue) {
      block.run()
    }
  }
}