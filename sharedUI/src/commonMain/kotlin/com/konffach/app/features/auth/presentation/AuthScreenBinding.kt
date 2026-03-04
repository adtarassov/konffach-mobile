package com.konffach.app.features.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.konffach.app.di.LocalAppScope
import com.konffach.app.navigation.AppNavKey
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreenBinding(
    entryKey: AppNavKey.Auth,
    onSignedIn: () -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel = remember(entryKey) { appGraph.authViewModelFactory.create() }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                AuthEffect.NavigateToDialogs -> onSignedIn()
            }
        }
    }

    AuthScreen(state = state)
}
