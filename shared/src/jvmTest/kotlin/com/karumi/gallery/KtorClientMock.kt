package com.karumi.gallery

import io.ktor.client.call.HttpClientCall
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpRequest
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.http.ContentType.Application
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.io.charsets.Charsets
import org.apache.commons.io.FileUtils
import java.io.File
import kotlin.jvm.javaClass
import kotlin.test.assertEquals
import kotlin.text.toByteArray

typealias EnqueueResponse = (HttpClientCall) -> MockHttpResponse

open class KtorClientMock {

    companion object {
        private const val FILE_ENCODING = "UTF-8"
    }

    private var enqueuedResponse: EnqueueResponse? = null
    private lateinit var request: MockHttpRequest

    protected fun engine(): MockEngine = MockEngine {
        request = this
        enqueuedResponse!!.invoke(call)
    }

    fun enqueueMockResponse(status: Int = 200, fileName: String? = null) {
        enqueuedResponse = getResponse(status, fileName)
    }

    private fun getResponse(
      status: Int,
      fileName: String?
    ): EnqueueResponse = {
        MockHttpResponse(
            it,
            HttpStatusCode.fromValue(status),
            ByteReadChannel(getContentFromFile(fileName).toByteArray(Charsets.UTF_8)),
            headersOf(HttpHeaders.ContentType to listOf(Application.Json.toString()))
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

    private fun getContentFromFile(fileName: String? = null): String {
        if (fileName == null) {
            return "[]"
        }

        val file = File(javaClass.getResource("/$fileName").file)
        val lines = FileUtils.readLines(file, FILE_ENCODING)
        val stringBuilder = StringBuilder()
        for (line in lines) {
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }
}