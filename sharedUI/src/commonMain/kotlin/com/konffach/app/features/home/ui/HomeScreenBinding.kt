package com.konffach.app.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konffach.app.di.LocalAppScope
import com.konffach.app.di.metroViewModel

@Composable
fun HomeScreenBinding(
    onOpenDialogs: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel: HomeViewModel = metroViewModel {
        appGraph.homeViewModelFactory.create()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onOpenDialogs = onOpenDialogs,
        onOpenSettings = onOpenSettings,
    )
}
