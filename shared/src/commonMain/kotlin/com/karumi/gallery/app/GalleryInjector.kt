package com.karumi.gallery.app

import com.karumi.gallery.data.getEngine
import com.karumi.gallery.data.local.LocalGalleryDb
import com.karumi.gallery.data.local.LocalPhotosDataSource
import com.karumi.gallery.data.network.PhotosApiClient
import com.karumi.gallery.domain.PhotosFlow
import com.karumi.gallery.domain.TTLCache
import com.karumi.gallery.domain.TimeProvider
import com.karumi.gallery.domain.TimeStorage
import com.karumi.gallery.generated.KotlinConfig
import com.karumi.gallery.usecase.GetPhotos
import com.squareup.sqldelight.db.SqlDriver
import kotlin.native.concurrent.ThreadLocal
import kotlin.time.minutes

@ThreadLocal
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

  lateinit var timeProvider: TimeProvider
  lateinit var timeStorage: TimeStorage

  fun init(
    driver: SqlDriver,
    timeProvider: TimeProvider,
    timeStorage: TimeStorage
  ) {
    LocalGalleryDb.dbSetup(driver)
    this.timeProvider = timeProvider
    this.timeStorage = timeStorage
  }

  open fun getPhotosApiClient(): PhotosApiClient =
    PhotosApiClient(getEngine(), KotlinConfig.UNPLASH_KEY)

  open fun getPhotos(): GetPhotos =
    GetPhotos(getPhotosFlow())

  open fun ttlCache(): TTLCache = TTLCache(timeStorage, timeProvider, ttl = 2.minutes)

  open fun getLocalPhotosDataSource(): LocalPhotosDataSource =
    LocalPhotosDataSource(ttlCache())

  open fun getPhotosFlow(): PhotosFlow =
    PhotosFlow(getPhotosApiClient(), getLocalPhotosDataSource())
}