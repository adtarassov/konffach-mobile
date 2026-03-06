package com.konffach.app.features.auth.screen

import com.konffach.app.di.AppScope
import com.konffach.app.features.auth.api.AuthRepository
import com.konffach.app.network.safeApiCall
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(AppScope::class)
class AuthRepositoryImpl(
    private val authApi: AuthApi,
) : AuthRepository {

    override suspend fun signIn(login: String, password: String) =
        safeApiCall { authApi.signIn(AuthRequest(login = login, password = password)) }

    override suspend fun signUp(login: String, password: String) =
        safeApiCall { authApi.signUp(AuthRequest(login = login, password = password)) }
}
