package com.konffach.app.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konffach.app.features.home.api.HomeRepository
import com.konffach.app.features.home.screen.HomeItem
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface HomeIntent {
    data class ItemClicked(val item: HomeItem) : HomeIntent
    data object OpenDialogsClicked : HomeIntent
    data object SettingsClicked : HomeIntent
}

sealed interface HomeEffect {
    data object NavigateToDialogs : HomeEffect
    data object NavigateToSettings : HomeEffect
    data class ItemSelected(val item: HomeItem) : HomeEffect
}

@AssistedInject
class HomeViewModel(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        HomeScreenState(
            isLoading = true,
            errorMessage = null,
            items = emptyList(),
            onIntent = ::onIntent,
        )
    )
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<HomeEffect>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effects = _effects.asSharedFlow()

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val items = homeRepository.loadItems()
                _state.update {
                    it.copy(isLoading = false, errorMessage = null, items = items)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, errorMessage = e.message, items = emptyList())
                }
            }
        }
    }

    private fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ItemClicked -> _effects.tryEmit(HomeEffect.ItemSelected(intent.item))
            HomeIntent.OpenDialogsClicked -> _effects.tryEmit(HomeEffect.NavigateToDialogs)
            HomeIntent.SettingsClicked -> _effects.tryEmit(HomeEffect.NavigateToSettings)
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): HomeViewModel
    }
}
