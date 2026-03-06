package com.konffach.app.features.auth.api

import com.konffach.app.features.auth.screen.AuthTokens
import com.konffach.app.network.ApiResult

interface AuthRepository {

    suspend fun signIn(login: String, password: String): ApiResult<AuthTokens>

    suspend fun signUp(login: String, password: String): ApiResult<AuthTokens>
}
