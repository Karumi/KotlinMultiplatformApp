package com.karumi.gallery.app

import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.data.getEngine
import com.karumi.gallery.generated.KotlinConfig
import com.karumi.gallery.usecase.GetPhotos

object GalleryInjector {
  private var galleryInjector: InjectionModule? = null

  operator fun invoke(): InjectionModule =
    galleryInjector ?: InjectionModule()

  val use: InjectionModule
    get() = invoke()

  fun config(injector: InjectionModule) {
    galleryInjector = injector
  }
}

open class InjectionModule {

  open val getPhotosApiClient: PhotosApiClient by lazy {
    PhotosApiClient(getEngine(), KotlinConfig.UNPLASH_KEY)
  }

  open val getPhotos: GetPhotos by lazy {
    GetPhotos(getPhotosApiClient)
  }
}