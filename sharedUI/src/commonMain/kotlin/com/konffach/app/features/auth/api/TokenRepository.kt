package com.konffach.app.features.auth.api

import com.konffach.app.features.auth.screen.AuthTokens
import kotlinx.coroutines.flow.StateFlow

sealed interface SessionState {
    data class Authenticated(val tokens: AuthTokens) : SessionState
    data object Unauthenticated : SessionState
}

interface TokenRepository {

    val sessionState: StateFlow<SessionState>

    suspend fun save(tokens: AuthTokens)

    fun clear()
}
