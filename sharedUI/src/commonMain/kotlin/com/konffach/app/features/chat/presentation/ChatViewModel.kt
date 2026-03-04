package com.konffach.app.features.chat.presentation

import androidx.lifecycle.ViewModel
import com.konffach.app.features.chat.data.ChatRepository
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel @AssistedInject constructor(
    @Assisted val dialogId: String,
    repository: ChatRepository,
) : ViewModel() {

    @AssistedFactory
    fun interface Factory {
        fun create(dialogId: String): ChatViewModel
    }

    private val _state = MutableStateFlow(
        ChatScreenState(
            dialogId = dialogId,
            messages = repository.getMessages(dialogId),
        ),
    )
    val state: StateFlow<ChatScreenState> = _state.asStateFlow()
}
