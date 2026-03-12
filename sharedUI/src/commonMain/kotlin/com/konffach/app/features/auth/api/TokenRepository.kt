package com.konffach.app.features.auth.api

import com.konffach.app.features.auth.screen.AuthTokens
import kotlinx.coroutines.flow.StateFlow

interface TokenRepository {

    val tokensFlow: StateFlow<AuthTokens?>

    suspend fun save(tokens: AuthTokens)

    fun clear()
}
