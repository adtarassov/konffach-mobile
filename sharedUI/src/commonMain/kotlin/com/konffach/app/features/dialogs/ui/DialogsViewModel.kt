package com.konffach.app.features.dialogs.ui

import androidx.lifecycle.ViewModel
import com.konffach.app.features.chat.api.ChatRepository
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@AssistedInject
class DialogsViewModel(
    repository: ChatRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        DialogsScreenState(
            dialogs = repository.getChats(),
        )
    )
    val state: StateFlow<DialogsScreenState> = _state.asStateFlow()

    private fun onIntent(intent: DialogsIntent) = Unit

    @AssistedFactory
    fun interface Factory {
        fun create(): DialogsViewModel
    }
}
