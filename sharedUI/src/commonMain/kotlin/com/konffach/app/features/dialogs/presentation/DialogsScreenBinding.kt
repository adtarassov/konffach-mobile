package com.konffach.app.features.dialogs.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.konffach.app.di.LocalAppScope
import com.konffach.app.navigation.AppNavKey

@Composable
fun DialogsScreenBinding(
    entryKey: AppNavKey.Dialogs,
    onOpenChat: (dialogId: String) -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel = remember(entryKey) { appGraph.dialogsViewModelFactory.create() }

    val state by viewModel.state.collectAsState()

    DialogsScreen(
        state = state,
        onOpenChat = onOpenChat,
    )
}
