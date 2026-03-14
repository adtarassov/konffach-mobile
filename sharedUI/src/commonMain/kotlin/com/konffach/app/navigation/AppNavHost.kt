package com.konffach.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.konffach.app.di.LocalAppScope
import com.konffach.app.features.auth.ui.AuthScreenBinding
import com.konffach.app.features.chat.ui.ChatScreenBinding
import com.konffach.app.features.dialogs.ui.DialogsScreenBinding
import com.konffach.app.features.home.ui.HomeScreenBinding
import com.konffach.app.features.settings.ui.SettingsScreenBinding
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Immutable
data class NavigationState(
    val root: AppNavKey
)

/**
 * Root navigation host. Owns the back stack and maps [AppNavKey] entries to screens.
 */
@Composable
fun AppRoot() {
    val appGraph = LocalAppScope.current
    val viewModel = appGraph.navigationViewModel
    val state by viewModel.state.collectAsStateWithLifecycle()

    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(AppNavKey.Auth::class, AppNavKey.Auth.serializer())
                    subclass(AppNavKey.Home::class, AppNavKey.Home.serializer())
                    subclass(AppNavKey.Dialogs::class, AppNavKey.Dialogs.serializer())
                    subclass(AppNavKey.Settings::class, AppNavKey.Settings.serializer())
                    subclass(AppNavKey.Chat::class, AppNavKey.Chat.serializer())
                }
            }
        },
        state.root
    )
    val navigator = rememberAppNavigator(backStack)

    LaunchedEffect(state.root) {
        navigator.replaceRoot(state.root)
    }

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = { key ->
            when (key) {
                is AppNavKey.Auth -> NavEntry(key) {
                    AuthScreenBinding()
                }

                is AppNavKey.Home -> NavEntry(key) {
                    HomeScreenBinding(
                        onOpenDialogs = navigator::openDialogs,
                        onOpenSettings = navigator::openSettings,
                    )
                }

                is AppNavKey.Settings -> NavEntry(key) {
                    SettingsScreenBinding(
                        onBack = navigator::goBack
                    )
                }

                is AppNavKey.Chat -> NavEntry(key) {
                    ChatScreenBinding(
                        dialogId = key.dialogId,
                        onBack = navigator::goBack,
                        onOpenSettings = navigator::openSettings
                    )
                }

                is AppNavKey.Dialogs -> NavEntry(key) {
                    DialogsScreenBinding(
                        onOpenChat = navigator::openChat
                    )
                }

                else -> throw IllegalStateException("Unsupported AppNavKey")
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
