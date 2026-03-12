package com.konffach.app.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konffach.app.di.LocalAppScope
import com.konffach.app.di.metroViewModel

@Composable
fun AuthScreenBinding() {
    val appGraph = LocalAppScope.current
    val viewModel: AuthViewModel = metroViewModel {
        appGraph.authViewModelFactory.create()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    AuthScreen(state = state)
}
