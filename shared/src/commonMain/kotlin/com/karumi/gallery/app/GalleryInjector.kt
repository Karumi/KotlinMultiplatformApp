package com.karumi.gallery.app

import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.data.getEngine
import com.karumi.gallery.generated.KotlinConfig
import com.karumi.gallery.usecase.GetPhotos

object GalleryInjector {
  private var galleryInjector: InjectionModule? = null
  private val defaultInjector = InjectionModule()

  operator fun invoke(): InjectionModule =
    galleryInjector ?: defaultInjector

  fun config(injector: InjectionModule) {
    galleryInjector = injector
  }
}

open class InjectionModule {
  open fun getPhotosApiClient(): PhotosApiClient =
    PhotosApiClient(getEngine(), KotlinConfig.UNPLASH_KEY)

  open fun getPhotos(): GetPhotos =
    GetPhotos(getPhotosApiClient())
}