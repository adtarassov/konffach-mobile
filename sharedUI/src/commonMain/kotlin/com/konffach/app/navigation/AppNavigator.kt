package com.konffach.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * Thin route-layer navigator for app-level back stack mutations.
 * Feature ViewModels should not depend on it directly; screens receive plain callbacks from routes.
 */
@Stable
class AppNavigator(
    private val backStack: NavBackStack<NavKey>,
) {
    fun openDialogs() {
        backStack.add(AppNavKey.Dialogs)
    }

    fun openSettings() {
        backStack.add(AppNavKey.Settings)
    }

    fun openChat(dialogId: String) {
        backStack.add(AppNavKey.Chat(dialogId))
    }

    fun goBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    fun replaceRoot(root: AppNavKey) {
        val alreadyAtRoot = backStack.size == 1 && backStack.lastOrNull() == root
        if (alreadyAtRoot) return

        backStack.clear()
        backStack.add(root)
    }
}

@Composable
fun rememberAppNavigator(
    backStack: NavBackStack<NavKey>,
): AppNavigator = remember(backStack) {
    AppNavigator(backStack)
}
