package com.konffach.app.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konffach.app.features.home.api.HomeRepository
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@AssistedInject
class HomeViewModel(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        HomeScreenState(
            isLoading = true,
            errorMessage = null,
            items = emptyList(),
        )
    )
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        loadContent()
    }

    private fun onIntent(intent: HomeIntent) = Unit

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

    @AssistedFactory
    fun interface Factory {
        fun create(): HomeViewModel
    }
}
