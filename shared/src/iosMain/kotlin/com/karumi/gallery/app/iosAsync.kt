package com.karumi.gallery.app

import com.karumi.gallery.logError
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.dispatch_after
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time
import kotlin.coroutines.CoroutineContext

actual fun CoroutineScope.launchInMain(block: suspend CoroutineScope.() -> Unit): Job =
  GlobalScope.launch(MainDispatcher) { block() }

actual fun <T> Flow<T>.flowOnBackground(): Flow<T> = flowOn(MainDispatcher)

actual fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T =
  kotlinx.coroutines.runBlocking(context, block)

@UseExperimental(InternalCoroutinesApi::class)
internal object MainDispatcher : CoroutineDispatcher(), Delay {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    dispatch_async(dispatch_get_main_queue()) {
      block.run()
    }
  }

  @InternalCoroutinesApi
  override fun scheduleResumeAfterDelay(
    timeMillis: Long,
    continuation: CancellableContinuation<Unit>
  ) {
    dispatch_after(
      dispatch_time(DISPATCH_TIME_NOW, timeMillis * 1_000_000),
      dispatch_get_main_queue()
    ) {
      try {
        with(continuation) {
          resumeUndispatched(Unit)
        }
      } catch (err: Throwable) {
        logError("UNCAUGHT", err.message ?: "", err)
        throw err
      }
    }
  }

  @InternalCoroutinesApi
  override fun invokeOnTimeout(timeMillis: Long, block: Runnable): DisposableHandle {
    val handle = object : DisposableHandle {
      var disposed = false
        private set

      override fun dispose() {
        disposed = true
      }
    }
    dispatch_after(
      dispatch_time(DISPATCH_TIME_NOW, timeMillis * 1_000_000),
      dispatch_get_main_queue()
    ) {
      try {
        if (!handle.disposed) {
          block.run()
        }
      } catch (err: Throwable) {
        logError("UNCAUGHT", err.message ?: "", err)
        throw err
      }
    }

    return handle
  }
}