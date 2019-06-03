package com.karumi.dribbble.data

import com.karumi.dribbble.model.Photos
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

// 5ba090b720d65a5d989be6955ad808eea4dae16abdc357f7f987ef9a9e48ccf1
class PhotosApiClient(
    engine: HttpClientEngine,
    private val apiKey: String
) {

    companion object {
        private const val PHOTOS_API = "https://api.unsplash.com"
        private const val PATH = "photos"
        private const val CLIENT_ID = "Client-ID"
        private const val PER_PAGE_PARAMETER = "per_page"
        private const val VALUES_PER_PAGE = "20"
    }

    private val client: HttpClient = HttpClient(engine) {
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                setMapper(UserEntity::class, UserEntity.serializer())
                setMapper(PhotoUrlsEntity::class, PhotoUrlsEntity.serializer())
                setMapper(PhotoEntity::class, PhotoEntity.serializer())
            }
        }
    }

    suspend fun getPhotos(): Photos = client.get<String>("$PHOTOS_API/$PATH") {
        header(HttpHeaders.Authorization, "$CLIENT_ID $apiKey")
        parameter(PER_PAGE_PARAMETER, VALUES_PER_PAGE)
    }.let {
        Json(strictMode = false).parse(PhotoEntity.serializer().list, it)
    }.toDomain()
}