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

private const val REQUEST_TIMEOUT_MS = 30_000L
private const val CONNECT_TIMEOUT_MS = 15_000L
private const val SOCKET_TIMEOUT_MS = 30_000L

private const val DEFAULT_BASE_URL = "https://jsonplaceholder.typicode.com"

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
        requestTimeoutMillis = REQUEST_TIMEOUT_MS
        connectTimeoutMillis = CONNECT_TIMEOUT_MS
        socketTimeoutMillis = SOCKET_TIMEOUT_MS
    }
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.INFO
    }
    defaultRequest {
        url(DEFAULT_BASE_URL)
        header(HttpHeaders.Accept, ContentType.Application.Json.toString())
        header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}
