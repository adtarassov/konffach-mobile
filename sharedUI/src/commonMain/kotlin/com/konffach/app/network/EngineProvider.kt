package com.konffach.app.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

/**
 * Platform-specific HttpClient creation. Actual implementations use OkHttp (Android) or Darwin (iOS).
 */
expect fun createPlatformHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient
