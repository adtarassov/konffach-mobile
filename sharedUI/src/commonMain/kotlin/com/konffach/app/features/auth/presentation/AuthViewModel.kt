package com.konffach.app.features.auth.presentation

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface AuthIntent {
    data object SignInClicked : AuthIntent
}

sealed interface AuthEffect {
    data object NavigateToDialogs : AuthEffect
}

@AssistedInject
class AuthViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        AuthScreenState(
            isLoading = false,
            onIntent = ::onIntent,
        )
    )
    val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<AuthEffect>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effects = _effects.asSharedFlow()

    private fun onIntent(intent: AuthIntent) {
        when (intent) {
            AuthIntent.SignInClicked -> _effects.tryEmit(AuthEffect.NavigateToDialogs)
        }
    }


    @AssistedFactory
    fun interface Factory {
        fun create(): AuthViewModel
    }
}
