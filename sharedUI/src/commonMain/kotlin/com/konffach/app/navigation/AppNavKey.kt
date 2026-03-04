package com.konffach.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey : NavKey {

    @Serializable
    data object Auth : AppNavKey

    @Serializable
    data object Dialogs : AppNavKey

    @Serializable
    data class Chat(val dialogId: String) : AppNavKey
}

