package com.konffach.app.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import kotlin.time.Duration.Companion.seconds

private val REQUEST_TIMEOUT = 20.seconds
private val CONNECT_TIMEOUT = 10.seconds
private val SOCKET_TIMEOUT = 20.seconds

private const val DEFAULT_BASE_URL = "https://konffach-lranger123.amvera.io/api/"

private val jsonConfig = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
}

/**
 * Creates the single shared [HttpClient] with hardcoded defaults.
 * Used by DI to provide a singleton; no config object from outside.
 */
fun createHttpClient(): HttpClient = createPlatformHttpClient {
    install(ContentNegotiation) {
        json(jsonConfig)
    }
    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT.inWholeMilliseconds
        connectTimeoutMillis = CONNECT_TIMEOUT.inWholeMilliseconds
        socketTimeoutMillis = SOCKET_TIMEOUT.inWholeMilliseconds
    }
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.BODY
    }
    defaultRequest {
        url(DEFAULT_BASE_URL)
        header(HttpHeaders.Accept, ContentType.Application.Json.toString())
        header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}
