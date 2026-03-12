package com.konffach.app.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konffach.app.features.auth.api.TokenRepository
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Inject
class NavigationViewModel(
    tokenRepository: TokenRepository
) : ViewModel() {

    private val tokensFlow = tokenRepository.tokensFlow

    val state = tokensFlow
        .map { tokens ->
            val accessToken = tokens?.accessToken
            NavigationState(
                root = if (accessToken.isNullOrBlank()) AppNavKey.Auth else AppNavKey.Home
            )
        }
        .stateIn(
            scope = viewModelScope,
            initialValue =
                NavigationState(
                    root = if (tokensFlow.value?.accessToken.isNullOrBlank()) {
                        AppNavKey.Auth
                    } else {
                        AppNavKey.Home
                    }
                ),
            started = SharingStarted.WhileSubscribed()
        )
}
