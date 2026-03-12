package com.konffach.app.features.dialogs.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konffach.app.di.LocalAppScope
import com.konffach.app.di.metroViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DialogsScreenBinding(
    onOpenChat: (dialogId: String) -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel: DialogsViewModel = metroViewModel {
        appGraph.dialogsViewModelFactory.create()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is DialogsEffect.NavigateToChat -> onOpenChat(effect.dialogId)
            }
        }
    }

    DialogsScreen(state = state)
}
