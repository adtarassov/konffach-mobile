package com.konffach.app.features.chat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konffach.app.di.LocalAppScope
import com.konffach.app.di.metroViewModel

@Composable
fun ChatScreenBinding(
    dialogId: String,
    onBack: () -> Unit,
) {
    val appGraph = LocalAppScope.current
    val viewModel: ChatViewModel = metroViewModel {
        appGraph.chatViewModelFactory.create(dialogId)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        onBack = onBack,
    )
}
