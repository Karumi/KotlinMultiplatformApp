package com.karumi.gallery.data.network

import com.karumi.gallery.generated.KotlinConfig
import com.karumi.gallery.model.Photos
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list

open class PhotosApiClient(
  engine: HttpClientEngine,
  private val apiKey: String
) {

  companion object {
    private val PHOTOS_API = KotlinConfig.PHOTOS_API
    private const val PATH = "photos"
    private const val CLIENT_ID = "Client-ID"
    private const val PER_PAGE_PARAMETER = "per_page"
    private const val VALUES_PER_PAGE = "20"
  }

  private val client: HttpClient by lazy {
    HttpClient(engine) {
      install(JsonFeature) {
        serializer = KotlinxSerializer().apply {
          setMapper(UserEntity::class, UserEntity.serializer())
          setMapper(PhotoUrlsEntity::class, PhotoUrlsEntity.serializer())
          setMapper(PhotoEntity::class, PhotoEntity.serializer())
        }
      }
      install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.BODY
      }
    }
  }

  @UseExperimental(UnstableDefault::class)
  open suspend fun getPhotos(): Photos = client.get<String>("$PHOTOS_API/$PATH") {
    header(HttpHeaders.Authorization, "$CLIENT_ID $apiKey")
    parameter(PER_PAGE_PARAMETER, VALUES_PER_PAGE)
  }.let {
    Json(JsonConfiguration(strictMode = false)).parse(PhotoEntity.serializer().list, it)
  }.toDomain()
}