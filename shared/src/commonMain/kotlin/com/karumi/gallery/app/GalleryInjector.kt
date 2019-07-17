package com.karumi.gallery.app

import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.concurrency.value
import co.touchlab.stately.freeze
import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.data.getEngine
import com.karumi.gallery.generated.KotlinConfig
import com.karumi.gallery.usecase.GetPhotos

object GalleryInjector {
  private val galleryInjector = AtomicReference<InjectionModule?>(null)
  private val defaultInjector = InjectionModule()

  operator fun invoke(): InjectionModule =
    galleryInjector.value ?: defaultInjector

  fun config(injector: InjectionModule) {
    galleryInjector.value = injector.freeze()
  }
}

open class InjectionModule {
  open fun getPhotosApiClient(): PhotosApiClient =
    PhotosApiClient(getEngine(), KotlinConfig.UNPLASH_KEY)

  open fun getPhotos(): GetPhotos =
    GetPhotos(getPhotosApiClient())
}