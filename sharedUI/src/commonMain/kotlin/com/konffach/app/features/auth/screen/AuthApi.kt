package com.konffach.app.features.auth.screen

import com.konffach.app.di.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

private const val PATH_SIGN_IN = "users/login"
private const val PATH_SIGN_UP = "users/register"

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
