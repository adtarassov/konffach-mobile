package com.konffach.app.di

import com.konffach.app.network.createHttpClient
import com.russhwolf.settings.Settings
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient

/**
 * Binding container for core app dependencies (settings, HTTP client).
 * Analogous to a @Module in Dagger; included in [AppGraph] via [DependencyGraph.bindingContainers].
 */
@BindingContainer
object CoreProviders {

    @Provides
    @SingleIn(AppScope::class)
    fun provideSettings(): Settings = Settings()

    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClient(): HttpClient = createHttpClient()
}
