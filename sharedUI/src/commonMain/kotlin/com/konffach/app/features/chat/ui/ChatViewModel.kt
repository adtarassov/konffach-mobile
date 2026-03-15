package com.konffach.app.features.chat.ui

import androidx.lifecycle.ViewModel
import com.konffach.app.features.chat.api.ChatRepository
import com.konffach.app.features.chat.screen.ChatMessage
import com.konffach.app.features.chat.screen.ChatMessageContent
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@AssistedInject
class ChatViewModel(
    @Assisted val dialogId: String,
    repository: ChatRepository,
    private val chatMessageItemViewModelFactory: ChatMessageItemViewModel.Factory,
) : ViewModel() {
    private val messageItemViewModels = mutableListOf<ChatMessageItemViewModel>()
    private val initialMessages = repository.getMessages(dialogId)
    private var nextMessageId = initialMessages.size + 1

    private val _state = MutableStateFlow(
        ChatScreenState(
            dialogId = dialogId,
            messages = initialMessages.map(::createMessageItemState),
            onIntent = ::onIntent,
            inputFieldState = InputFiledState(
                value = "",
                onIntent = ::onIntent,
            )
        ),
    )
    val state: StateFlow<ChatScreenState> = _state.asStateFlow()

    private fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.InputFieldChanged -> _state.update {
                it.copy(inputFieldState = it.inputFieldState.copy(value = intent.value))
            }

            ChatIntent.SendMessage -> {
                sendMessage()
            }
        }
    }

    private fun sendMessage() {
        val text = _state.value.inputFieldState.value.trim()
        if (text.isBlank()) return

        val newMessage = ChatMessage(
            id = "local-$nextMessageId",
            dialogId = dialogId,
            author = "Me",
            contents = listOf(ChatMessageContent.Text(text)),
            isMine = true,
        )
        nextMessageId += 1

        _state.update {
            it.copy(
                messages = it.messages + createMessageItemState(newMessage),
                inputFieldState = it.inputFieldState.copy(value = ""),
            )
        }
    }

    private fun createMessageItemState(message: ChatMessage): ChatMessageItemState {
        val viewModel = chatMessageItemViewModelFactory.create(message)
        messageItemViewModels += viewModel
        return viewModel.state.value
    }

    @AssistedFactory
    fun interface Factory {
        fun create(dialogId: String): ChatViewModel
    }
}
