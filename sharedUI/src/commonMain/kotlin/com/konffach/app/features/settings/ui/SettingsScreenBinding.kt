package com.konffach.app.features.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.konffach.app.di.LocalAppScope
import com.konffach.app.navigation.AppNavKey
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreenBinding(
    entryKey: AppNavKey.Settings,
    onBack: () -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel = remember(entryKey) { appGraph.settingsViewModelFactory.create() }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> onBack()
            }
        }
    }

    SettingsScreen(state = state)
}
