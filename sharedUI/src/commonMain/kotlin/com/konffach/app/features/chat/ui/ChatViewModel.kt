package com.konffach.app.features.chat.ui

import androidx.lifecycle.ViewModel
import com.konffach.app.features.chat.api.ChatRepository
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@AssistedInject
class ChatViewModel(
    @Assisted val dialogId: String,
    repository: ChatRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        ChatScreenState(
            dialogId = dialogId,
            messages = repository.getMessages(dialogId),
        ),
    )
    val state: StateFlow<ChatScreenState> = _state.asStateFlow()

    private fun onIntent(intent: ChatIntent) = Unit

    @AssistedFactory
    fun interface Factory {
        fun create(dialogId: String): ChatViewModel
    }
}
