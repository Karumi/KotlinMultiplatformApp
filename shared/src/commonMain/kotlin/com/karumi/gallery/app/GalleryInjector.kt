package com.karumi.gallery.app

import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.data.getEngine
import com.karumi.gallery.generated.KotlinConfig
import com.karumi.gallery.usecase.GetPhotos
import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic

object GalleryInjector {
  private val galleryInjector: AtomicRef<InjectionModule?> = atomic(null)
  private val defaultInjector = InjectionModule()

  operator fun invoke(): InjectionModule =
    galleryInjector.value ?: defaultInjector

  val use: InjectionModule
    get() = invoke()

  fun config(injector: InjectionModule) {
    galleryInjector.lazySet(injector)
  }
}

open class InjectionModule {
  open fun getPhotosApiClient(): PhotosApiClient =
    PhotosApiClient(getEngine(), KotlinConfig.UNPLASH_KEY)

  open fun getPhotos(): GetPhotos =
    GetPhotos(getPhotosApiClient())
}