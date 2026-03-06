package com.konffach.app.features.dialogs.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konffach.app.features.chat.api.ChatRepository
import com.konffach.app.network.onError
import com.konffach.app.network.onSuccess
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DialogsIntent {
    data class OpenChatClicked(val dialogId: String) : DialogsIntent
}

sealed interface DialogsEffect {
    data class NavigateToChat(val dialogId: String) : DialogsEffect
}

@AssistedInject
class DialogsViewModel(
    repository: ChatRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        DialogsScreenState(
            dialogs = repository.getChats(),
            onIntent = ::onIntent,
        )
    )
    val state: StateFlow<DialogsScreenState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<DialogsEffect>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effects = _effects.asSharedFlow()

    private fun onIntent(intent: DialogsIntent) {
        when (intent) {
            is DialogsIntent.OpenChatClicked -> _effects.tryEmit(DialogsEffect.NavigateToChat(intent.dialogId))
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): DialogsViewModel
    }
}
