package com.konffach.app.features.auth.api

import com.konffach.app.features.auth.screen.AuthTokens

interface AuthRepository {

    suspend fun signIn(login: String, password: String): AuthTokens

    suspend fun signUp(login: String, password: String): AuthTokens
}
