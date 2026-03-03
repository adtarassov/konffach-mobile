package com.konffach.app.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey

@Serializable
data object DialogsRoute : AppRoute

@Serializable
data class ChatRoute(val chatId: Int) : AppRoute

