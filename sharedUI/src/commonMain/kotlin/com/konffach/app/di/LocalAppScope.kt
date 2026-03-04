package com.konffach.app.di

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppScope = staticCompositionLocalOf<AppGraph> {
    error("AppGraph is not provided. Make sure to wrap your root composable with a provider.")
}

