package com.karumi.gallery

import com.karumi.gallery.app.runBlocking
import com.karumi.gallery.data.PhotosApiClient
import com.karumi.gallery.resources.getEmptyPhotosList
import com.karumi.gallery.resources.getInvalidBody
import com.karumi.gallery.resources.getPhotosResponse
import kotlinx.serialization.json.JsonParsingException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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
    enqueueMockResponse(200, getPhotosResponse)

    apiClient.getPhotos()

    assertGetRequestSentTo("/photos")
  }

  @Test
  fun `get empty photo list when call get photos`() = runBlocking {
    enqueueMockResponse(200, getEmptyPhotosList)

    val result = apiClient.getPhotos()

    assertEquals(result.size, 0)
  }

  @Test
  fun `get 2 photos when call get photos`() = runBlocking {
    enqueueMockResponse(200, getPhotosResponse)

    val result = apiClient.getPhotos()

    assertEquals(result.size, 2)
  }

  @Test
  fun `send twenty per_page parameter`() = runBlocking {
    enqueueMockResponse(200, getPhotosResponse)

    apiClient.getPhotos()

    assertRequestContainsParameter("per_page", "20")
  }

  @Test
  fun `send authorization header`() = runBlocking {
    enqueueMockResponse(200, getPhotosResponse)

    apiClient.getPhotos()

    assertRequestContainsHeader("Authorization", "Client-ID $API_KEY")
  }

  @Test
  fun `throw parse exception if body isn't list`() {
    assertFailsWith(JsonParsingException::class) {
      runBlocking {
        enqueueMockResponse(200, getInvalidBody)

        apiClient.getPhotos()
      }
    }
  }
}