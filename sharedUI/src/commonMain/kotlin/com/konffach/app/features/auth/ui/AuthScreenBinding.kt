package com.konffach.app.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                AuthEffect.AuthSucceeded -> onSignedIn()
            }
        }
    }

    AuthScreen(state = state)
}
