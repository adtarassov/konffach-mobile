package com.konffach.app.features.chat.ui

import androidx.lifecycle.ViewModel
import com.konffach.app.features.chat.screen.ChatMessage
import com.konffach.app.features.chat.screen.ChatMessageContent
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@AssistedInject
class ChatMessageItemViewModel(
    @Assisted message: ChatMessage,
) : ViewModel() {

    private val _state = MutableStateFlow(message.toState())
    val state: StateFlow<ChatMessageItemState> = _state.asStateFlow()

    @AssistedFactory
    fun interface Factory {
        fun create(message: ChatMessage): ChatMessageItemViewModel
    }
}

private fun ChatMessage.toState(): ChatMessageItemState {
    val text = contents
        .filterIsInstance<ChatMessageContent.Text>()
        .joinToString(separator = "\n") { it.value }
        .takeIf { it.isNotBlank() }

    return ChatMessageItemState(
        id = id,
        author = author,
        isMine = isMine,
        contents = contents,
        text = text,
    )
}
