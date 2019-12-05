package com.karumi.gallery

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType.Application
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.test.assertEquals
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.io.charsets.Charsets
import kotlinx.io.core.toByteArray

typealias EnqueueResponse = () -> HttpResponseData

open class KtorClientMock {

  lateinit var enqueuedResponse: EnqueueResponse
  private lateinit var request: HttpRequestData

  protected fun engine(): MockEngine = MockEngine {
    request = it
    enqueuedResponse.invoke()
  }

  fun enqueueMockResponse(status: Int = 200, response: String = "[]") {
    enqueuedResponse = getResponse(status, response)
  }

  private fun getResponse(
    status: Int,
    response: String
  ): EnqueueResponse = {
    respond(
      status = HttpStatusCode.fromValue(status),
      content = ByteReadChannel(response.toByteArray(Charsets.UTF_8)),
      headers = headersOf(HttpHeaders.ContentType to listOf(Application.Json.toString()))
    )
  }

  fun assertRequestContainsHeader(key: String, value: String) {
    assertEquals(value, request.headers[key])
  }

  fun assertGetRequestSentTo(expectedPath: String) {
    assertEquals(expectedPath, request.url.encodedPath)
    assertEquals("GET", request.method.value)
  }

  fun assertRequestContainsParameter(key: String, expectedValue: String) {
    assertEquals(expectedValue, request.url.parameters[key])
  }
}