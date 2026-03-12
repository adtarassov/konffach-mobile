package com.konffach.app.features.settings.ui

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@AssistedInject
class SettingsViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        SettingsScreenState()
    )
    val state: StateFlow<SettingsScreenState> = _state.asStateFlow()

    private fun onIntent(intent: SettingsIntent) = Unit

    @AssistedFactory
    fun interface Factory {
        fun create(): SettingsViewModel
    }
}
