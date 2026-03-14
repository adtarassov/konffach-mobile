package com.konffach.app.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konffach.app.features.auth.api.SessionState
import com.konffach.app.features.auth.api.TokensRepository
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Inject
class NavigationViewModel(
    tokenRepository: TokensRepository
) : ViewModel() {

    private val sessionState = tokenRepository.sessionState

    val state = sessionState
        .map { currentSessionState ->
            NavigationState(
                root = when (currentSessionState) {
                    is SessionState.Authenticated -> AppNavKey.Chat("konffach")
                    SessionState.Unauthenticated -> AppNavKey.Auth
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = NavigationState(
                root = when (sessionState.value) {
                    is SessionState.Authenticated -> AppNavKey.Chat("konffach")
                    SessionState.Unauthenticated -> AppNavKey.Auth
                }
            ),
            started = SharingStarted.WhileSubscribed()
        )
}
