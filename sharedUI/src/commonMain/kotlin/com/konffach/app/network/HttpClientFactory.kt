package com.konffach.app.network

import com.konffach.app.features.auth.api.TokensRepository
import com.konffach.app.features.auth.screen.AuthTokens
import com.konffach.app.features.auth.screen.PATH_REFRESH_TOKEN
import com.konffach.app.features.auth.screen.PATH_SIGN_IN
import com.konffach.app.features.auth.screen.PATH_SIGN_UP
import com.konffach.app.features.auth.screen.RefreshTokenRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

private val REQUEST_TIMEOUT = 20.seconds
private val CONNECT_TIMEOUT = 10.seconds
private val SOCKET_TIMEOUT = 20.seconds

private const val DEFAULT_HOST = "konffach-lranger123.amvera.io/api"

/**
 * Creates the single shared [HttpClient] with hardcoded defaults.
 * Used by DI to provide a singleton; no config object from outside.
 */
fun createHttpClient(tokenRepository: TokensRepository): HttpClient = createPlatformHttpClient {
    expectSuccess = true

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = true
                prettyPrint = true
            }
        )
    }
    install(Auth) {
        bearer {
            cacheTokens = false
            nonCancellableRefresh = true
            loadTokens {
                tokenRepository.currentBearerTokens()
            }

            refreshTokens {
                val refreshToken = oldTokens?.refreshToken ?: return@refreshTokens null
                try {
                    val refreshedTokens = client.post(PATH_REFRESH_TOKEN) {
                        markAsRefreshTokenRequest()
                        setBody(RefreshTokenRequest(refreshToken = refreshToken))
                    }.body<AuthTokens>()

                    tokenRepository.save(refreshedTokens)
                    BearerTokens(
                        accessToken = refreshedTokens.accessToken,
                        refreshToken = refreshedTokens.refreshToken,
                    )
                } catch (_: Exception) {
                    tokenRepository.clear()
                    null
                }
            }

            sendWithoutRequest { request ->
                !request.url.toString().contains(PATH_REFRESH_TOKEN) &&
                        !request.url.toString().contains(PATH_SIGN_IN) &&
                        !request.url.toString().contains(PATH_SIGN_UP)
            }
        }
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
        url {
            protocol = URLProtocol.HTTPS
            host = DEFAULT_HOST
        }
        contentType(ContentType.Application.Json)
    }
}

private fun TokensRepository.currentBearerTokens(): BearerTokens? {
    val authTokens = currentAuthTokens() ?: return null
    return BearerTokens(
        accessToken = authTokens.accessToken,
        refreshToken = authTokens.refreshToken,
    )
}


