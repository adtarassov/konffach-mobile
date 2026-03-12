package com.konffach.app.features.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konffach.app.di.LocalAppScope
import com.konffach.app.di.metroViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreenBinding(
    onBack: () -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel: SettingsViewModel = metroViewModel {
        appGraph.settingsViewModelFactory.create()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> onBack()
            }
        }
    }

    SettingsScreen(state = state)
}
