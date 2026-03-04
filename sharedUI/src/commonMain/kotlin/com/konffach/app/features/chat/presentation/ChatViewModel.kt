package com.konffach.app.features.chat.presentation

import androidx.lifecycle.ViewModel
import com.konffach.app.features.chat.data.ChatRepository
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface ChatIntent {
    data object BackClicked : ChatIntent
}

sealed interface ChatEffect {
    data object NavigateBack : ChatEffect
}

@AssistedInject
class ChatViewModel(
    @Assisted val dialogId: String,
    repository: ChatRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        ChatScreenState(
            dialogId = dialogId,
            messages = repository.getMessages(dialogId),
            onIntent = ::onIntent,
        ),
    )
    val state: StateFlow<ChatScreenState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<ChatEffect>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effects = _effects.asSharedFlow()

    private fun onIntent(intent: ChatIntent) {
        when (intent) {
            ChatIntent.BackClicked -> _effects.tryEmit(ChatEffect.NavigateBack)
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(dialogId: String): ChatViewModel
    }
}
