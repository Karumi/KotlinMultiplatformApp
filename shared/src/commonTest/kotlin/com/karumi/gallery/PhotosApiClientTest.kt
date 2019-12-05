package com.karumi.gallery

import com.karumi.gallery.app.runBlocking
import com.karumi.gallery.data.network.PhotosApiClient
import com.karumi.gallery.resources.getEmptyPhotosList
import com.karumi.gallery.resources.getInvalidBody
import com.karumi.gallery.resources.getPhotosResponse
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.serialization.json.JsonParsingException

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
  fun sends_get_photos_request_to_the_correct_path() = runBlocking {
    enqueueMockResponse(200, getPhotosResponse)

    apiClient.getPhotos()

    assertGetRequestSentTo("/photos")
  }

  @Test
  fun get_empty_photo_list_when_call_get_photos() = runBlocking {
    enqueueMockResponse(200, getEmptyPhotosList)

    val result = apiClient.getPhotos()

    assertEquals(result.size, 0)
  }

  @Test
  fun get_2_photos_when_call_get_photos() = runBlocking {
    enqueueMockResponse(200, getPhotosResponse)

    val result = apiClient.getPhotos()

    assertEquals(result.size, 2)
  }

  @Test
  fun send_twenty_per_page_parameter() = runBlocking {
    enqueueMockResponse(200, getPhotosResponse)

    apiClient.getPhotos()

    assertRequestContainsParameter("per_page", "20")
  }

  @Test
  fun send_authorization_header() = runBlocking {
    enqueueMockResponse(200, getPhotosResponse)

    apiClient.getPhotos()

    assertRequestContainsHeader("Authorization", "Client-ID $API_KEY")
  }

  @Test
  fun throw_parse_exception_if_body_is_not_list() {
    assertFailsWith(JsonParsingException::class) {
      runBlocking {
        enqueueMockResponse(200, getInvalidBody)

        apiClient.getPhotos()
      }
    }
  }
}