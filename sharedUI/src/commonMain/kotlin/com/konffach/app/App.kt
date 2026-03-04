package com.konffach.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.konffach.app.di.LocalAppScope
import com.konffach.app.di.createAppGraph
import com.konffach.app.navigation.AppRoot
import com.konffach.app.theme.AppTheme

@Composable
fun App(
    onThemeChanged: @Composable (isDark: Boolean) -> Unit = {}
) {
    val appGraph = remember { createAppGraph() }

    AppTheme(onThemeChanged) {
        CompositionLocalProvider(LocalAppScope provides appGraph) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                AppRoot()
            }
        }
    }
}
