package com.konffach.app.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

inline fun <reified VM : ViewModel> metroViewModelFactory(
    crossinline create: () -> VM,
): ViewModelProvider.Factory = viewModelFactory {
    initializer { create() }
}

@Composable
inline fun <reified VM : ViewModel> rememberMetroViewModelFactory(
    vararg keys: Any?,
    noinline create: () -> VM,
): ViewModelProvider.Factory {
    val currentCreate by rememberUpdatedState(create)
    return remember(*keys) {
        metroViewModelFactory { currentCreate() }
    }
}

@Composable
inline fun <reified VM : ViewModel> metroViewModel(
    noinline create: () -> VM,
): VM = viewModel(
    factory = rememberMetroViewModelFactory(create = create)
)

@Composable
inline fun <reified VM : ViewModel> metroViewModel(
    key: String,
    noinline create: () -> VM,
): VM = viewModel(
    key = key,
    factory = rememberMetroViewModelFactory(key, create = create)
)
