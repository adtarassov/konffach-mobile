package com.konffach.app.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konffach.app.di.LocalAppScope
import com.konffach.app.di.metroViewModel
import kotlinx.coroutines.flow.collectLatest

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
