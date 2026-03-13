package com.konffach.app.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konffach.app.features.auth.api.AuthRepository
import com.konffach.app.features.auth.api.TokensRepository
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AuthIntent {
    data class LoginChanged(val value: String) : AuthIntent
    data class PasswordChanged(val value: String) : AuthIntent
    data object SignInClicked : AuthIntent
    data object SignUpClicked : AuthIntent
}

@AssistedInject
class AuthViewModel(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokensRepository,
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
            try {
                val tokens = authRepository.signIn(login, password)
                tokenRepository.save(tokens)
                _state.update {
                    it.copy(isLoading = false)
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    private fun submitSignUp() {
        val login = _state.value.login
        val password = _state.value.password
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val tokens = authRepository.signUp(login, password)
                tokenRepository.save(tokens)
                _state.update {
                    it.copy(isLoading = false)
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): AuthViewModel
    }
}
