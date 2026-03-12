package com.konffach.app.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.konffach.app.di.LocalAppScope
import com.konffach.app.navigation.AppNavKey
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreenBinding(
    entryKey: AppNavKey.Home,
    onOpenDialogs: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel = remember(entryKey) { appGraph.homeViewModelFactory.create() }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                HomeEffect.NavigateToDialogs -> onOpenDialogs()
                HomeEffect.NavigateToSettings -> onOpenSettings()
                is HomeEffect.ItemSelected -> { }
            }
        }
    }

    HomeScreen(state = state)
}
