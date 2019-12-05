package com.karumi.gallery.app

import com.karumi.gallery.model.Photos
import com.karumi.gallery.usecase.GetPhotos
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class PhotoListPresenter(
  private val view: View,
  private val getAllPhotos: GetPhotos
) : CoroutineScope {

  override val coroutineContext: CoroutineContext
    get() = job

  private val job = Job()

  fun onCreate() = launchInMain {
    view.render(View.State.Loading)
    getAllPhotos()
      .flowOnBackground()
      .catch { view.render(View.State.Error) }
      .collect { view.render(View.State.Success(it)) }
  }

  fun detachView() {
    job.cancel()
  }

  interface View {
    fun render(state: State)

    sealed class State {
      object Loading : State()
      object Error : State()
      data class Success(val photos: Photos) : State()
    }
  }
}