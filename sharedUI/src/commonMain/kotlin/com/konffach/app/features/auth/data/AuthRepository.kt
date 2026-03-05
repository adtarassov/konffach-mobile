package com.konffach.app.features.auth.data

import com.konffach.app.network.ApiResult

interface AuthRepository {

    suspend fun signIn(login: String, password: String): ApiResult<AuthTokens>

    suspend fun signUp(login: String, password: String): ApiResult<AuthTokens>
}
