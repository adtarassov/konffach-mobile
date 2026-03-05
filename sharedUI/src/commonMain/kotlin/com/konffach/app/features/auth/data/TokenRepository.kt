package com.konffach.app.features.auth.data

import kotlinx.coroutines.flow.StateFlow

interface TokenRepository {

    val tokensFlow: StateFlow<AuthTokens?>

    suspend fun save(tokens: AuthTokens)

    suspend fun clear()
}
