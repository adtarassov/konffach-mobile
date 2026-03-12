package com.konffach.app.features.settings.ui

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

@AssistedInject
class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsScreenState(onIntent = ::onIntent)
    )
    val state: StateFlow<SettingsScreenState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<SettingsEffect>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effects: SharedFlow<SettingsEffect> = _effects.asSharedFlow()

    private fun onIntent(intent: SettingsIntent) {
        when (intent) {
            SettingsIntent.BackClicked -> _effects.tryEmit(SettingsEffect.NavigateBack)
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): SettingsViewModel
    }
}

sealed interface SettingsIntent {
    data object BackClicked : SettingsIntent
}

sealed interface SettingsEffect {
    data object NavigateBack : SettingsEffect
}
