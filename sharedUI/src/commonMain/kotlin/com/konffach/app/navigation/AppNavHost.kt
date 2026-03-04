package com.konffach.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.konffach.app.features.auth.presentation.AuthScreenBinding
import com.konffach.app.features.chat.presentation.ChatScreenBinding
import com.konffach.app.features.dialogs.presentation.DialogsScreenBinding
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

/**
 * Root navigation host. Owns the back stack and maps [AppNavKey] entries to screens.
 */
@Composable
fun AppRoot() {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(AppNavKey.Auth::class, AppNavKey.Auth.serializer())
                    subclass(AppNavKey.Dialogs::class, AppNavKey.Dialogs.serializer())
                    subclass(AppNavKey.Chat::class, AppNavKey.Chat.serializer())
                }
            }
        },
        AppNavKey.Auth
    )

    NavDisplay(
        backStack = backStack,
        entryProvider = { key ->
            when (key) {
                is AppNavKey.Auth -> NavEntry(key) {
                    AuthScreenBinding(
                        entryKey = key,
                        onSignedIn = { backStack.add(AppNavKey.Dialogs) }
                    )
                }

                is AppNavKey.Chat -> NavEntry(key) {
                    ChatScreenBinding(
                        entryKey = key,
                        onBack = { backStack.removeLastOrNull() }
                    )
                }

                is AppNavKey.Dialogs -> NavEntry(key) {
                    DialogsScreenBinding(
                        entryKey = key,
                        onOpenChat = { dialogId -> backStack.add(AppNavKey.Chat(dialogId)) }
                    )
                }

                else -> throw IllegalStateException("Unsupported AppNavKey")
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}


