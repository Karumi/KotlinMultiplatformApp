package com.karumi.dribbble

import com.karumi.dribbble.data.PhotosApiClient
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PhotosApiClientTest : KtorClientMock() {

    companion object {
        private const val API_KEY = "apiKey"
    }

    private lateinit var apiClient: PhotosApiClient

    @BeforeTest
    fun setUp() {
        apiClient = PhotosApiClient(engine(), API_KEY)
    }

    @Test
    fun `sends get photos request to the correct path`() = runBlocking {
        enqueueMockResponse(200, "getPhotosResponse.json")

        apiClient.getPhotos()

        assertGetRequestSentTo("/photos")
    }

    @Test
    fun `get 2 photos when call get photos`() = runBlocking {
        enqueueMockResponse(200, "getPhotosResponse.json")

        val result = apiClient.getPhotos()

        assertEquals(result.size, 2)
    }

    @Test
    fun `send twenty per_page parameter`() = runBlocking {
        enqueueMockResponse(200, "getPhotosResponse.json")

        apiClient.getPhotos()

        assertRequestContainsParameter("per_page", "20")
    }

    @Test
    fun `send authorization header`() = runBlocking {
        enqueueMockResponse(200, "getPhotosResponse.json")

        apiClient.getPhotos()

        assertRequestContainsHeader("Authorization", "Client-ID $API_KEY")
    }
}