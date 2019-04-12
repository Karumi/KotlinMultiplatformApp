package com.karumi.dribbble.data

import com.karumi.dribbble.model.Photos
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get

class PhotosNetworkDataSource(private val endpointURL: String) {

    companion object {
        private const val PATH = "/services/rest/?method=flickr.groups.pools.getPhotos&api_key=3448776af67ef35537f29" +
                "41ef7d3773c&group_id=2414141%40N20&per_page=10&page=0&format=json&nojsoncallback=1"
    }

    private val client: HttpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                setMapper(PhotosResponse::class, PhotosResponse.serializer())
            }
        }
    }

    suspend fun getPhotos(): Photos = client.get<PhotosResponse> {
        url {
            host = endpointURL
            encodedPath = PATH
        }
    }.toDomain()
}