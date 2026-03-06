package com.konffach.app.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konffach.app.features.auth.api.AuthRepository
import com.konffach.app.features.auth.api.TokenRepository
import com.konffach.app.features.auth.screen.AuthTokens
import com.konffach.app.network.ApiResult
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AuthIntent {
    data class LoginChanged(val value: String) : AuthIntent
    data class PasswordChanged(val value: String) : AuthIntent
    data object SignInClicked : AuthIntent
    data object SignUpClicked : AuthIntent
}

sealed interface AuthEffect {
    data object AuthSucceeded : AuthEffect
}

@AssistedInject
class AuthViewModel(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        AuthScreenState(
            login = "",
            password = "",
            isLoading = false,
            errorMessage = null,
            onIntent = ::onIntent
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
            is AuthIntent.LoginChanged -> _state.update {
                it.copy(login = intent.value)
            }

            is AuthIntent.PasswordChanged -> _state.update {
                it.copy(password = intent.value)
            }

            AuthIntent.SignInClicked -> submitSignIn()
            AuthIntent.SignUpClicked -> submitSignUp()
        }
    }

    private fun submitSignIn() {
        val login = _state.value.login
        val password = _state.value.password
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = authRepository.signIn(login, password)) {
                is ApiResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = result.error.message)
                    }
                }

                is ApiResult.Success<AuthTokens> -> {
                    tokenRepository.save(result.data)
                    _state.update { it.copy(isLoading = false, errorMessage = null) }
                    _effects.tryEmit(AuthEffect.AuthSucceeded)
                }
            }
        }
    }

    private fun submitSignUp() {
        val login = _state.value.login
        val password = _state.value.password
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = authRepository.signUp(login, password)) {
                is ApiResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = "")
                    }
                }

                is ApiResult.Success<AuthTokens> -> {
                    tokenRepository.save(result.data)
                    _state.update { it.copy(isLoading = false, errorMessage = null) }
                    _effects.tryEmit(AuthEffect.AuthSucceeded)
                }
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): AuthViewModel
    }
}
