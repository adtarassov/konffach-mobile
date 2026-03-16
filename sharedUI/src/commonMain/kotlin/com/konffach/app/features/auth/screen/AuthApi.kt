package com.konffach.app.features.auth.screen

import com.konffach.app.di.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable

const val PATH_SIGN_IN = "api/users/login"
const val PATH_SIGN_UP = "api/users/register"
const val PATH_REFRESH_TOKEN = "api/users/refresh"

@Inject
@SingleIn(AppScope::class)
class AuthApi(
    private val httpClient: HttpClient,
) {

    suspend fun signIn(request: AuthRequest): AuthTokens {
        return httpClient.post(PATH_SIGN_IN) {
            setBody(request)
        }.body()
    }

    suspend fun signUp(request: AuthRequest): AuthTokens {
        return httpClient.post(PATH_SIGN_UP) {
            setBody(request)
        }.body()
    }
}

@Serializable
data class AuthRequest(
    val login: String,
    val password: String,
)

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String,
)
