package com.konffach.app.features.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konffach.app.di.LocalAppScope
import com.konffach.app.di.metroViewModel

@Composable
fun SettingsScreenBinding(
    onBack: () -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel: SettingsViewModel = metroViewModel {
        appGraph.settingsViewModelFactory.create()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state.copy(onIntent = viewModel::onIntent),
        onBack = onBack,
    )
}
