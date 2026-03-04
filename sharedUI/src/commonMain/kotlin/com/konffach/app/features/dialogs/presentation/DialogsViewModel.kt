package com.konffach.app.features.dialogs.presentation

import androidx.lifecycle.ViewModel
import com.konffach.app.features.chat.data.ChatRepository
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DialogsViewModel @AssistedInject constructor(
    private val repository: ChatRepository,
) : ViewModel() {

    @AssistedFactory
    fun interface Factory {
        fun create(): DialogsViewModel
    }

    private val _state = MutableStateFlow(DialogsScreenState(dialogs = repository.getChats()))
    val state: StateFlow<DialogsScreenState> = _state.asStateFlow()
}
