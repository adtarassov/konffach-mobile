package com.konffach.app.features.chat.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.konffach.app.di.LocalAppScope
import com.konffach.app.navigation.AppNavKey

@Composable
fun ChatScreenBinding(
    entryKey: AppNavKey.Chat,
    onBack: () -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel = remember(entryKey.dialogId) {
        appGraph.chatViewModelFactory.create(entryKey.dialogId)
    }
    val state by viewModel.state.collectAsState()

    ChatScreen(
        state = state,
        onBack = onBack,
    )
}
