package com.konffach.app.features.settings.ui

import androidx.lifecycle.ViewModel
import com.konffach.app.features.auth.api.TokensRepository
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@AssistedInject
class SettingsViewModel(
    private val tokenRepository: TokensRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(
        SettingsScreenState()
    )
    val state: StateFlow<SettingsScreenState> = _state.asStateFlow()

    fun onIntent(intent: SettingsIntent) {
        when (intent) {
            SettingsIntent.ClearTokensClicked -> {
                tokenRepository.clear()
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): SettingsViewModel
    }
}
