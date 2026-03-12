package com.konffach.app.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konffach.app.di.LocalAppScope
import com.konffach.app.di.metroViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreenBinding(
    onSignedIn: () -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel: AuthViewModel = metroViewModel {
        appGraph.authViewModelFactory.create()
    }

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
