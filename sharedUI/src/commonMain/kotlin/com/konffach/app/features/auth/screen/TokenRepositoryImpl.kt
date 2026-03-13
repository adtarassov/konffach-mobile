package com.konffach.app.features.auth.screen

import com.konffach.app.di.AppScope
import com.konffach.app.features.auth.api.SessionState
import com.konffach.app.features.auth.api.TokenRepository
import com.russhwolf.settings.Settings
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val KEY_ACCESS = "auth_access_token"
private const val KEY_REFRESH = "auth_refresh_token"

@Inject
@SingleIn(AppScope::class)
class TokenRepositoryImpl(
    private val settings: Settings,
) : TokenRepository {

    private val _sessionState = MutableStateFlow(loadFromSettings().toSessionState())

    override val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    override suspend fun save(tokens: AuthTokens) {
        settings.putString(KEY_ACCESS, tokens.accessToken)
        settings.putString(KEY_REFRESH, tokens.refreshToken)
        _sessionState.value = SessionState.Authenticated(tokens)
    }

    override fun clear() {
        settings.remove(KEY_ACCESS)
        settings.remove(KEY_REFRESH)
        _sessionState.value = SessionState.Unauthenticated
    }

    private fun loadFromSettings(): AuthTokens? {
        val accessToken = settings.getStringOrNull(KEY_ACCESS) ?: return null
        val refreshToken = settings.getStringOrNull(KEY_REFRESH) ?: return null
        return AuthTokens(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    private fun AuthTokens?.toSessionState(): SessionState {
        return if (this == null) {
            SessionState.Unauthenticated
        } else {
            SessionState.Authenticated(this)
        }
    }
}
