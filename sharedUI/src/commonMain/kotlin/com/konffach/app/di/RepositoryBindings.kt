package com.konffach.app.di

import com.konffach.app.features.auth.api.AuthRepository
import com.konffach.app.features.auth.screen.AuthRepositoryImpl
import com.konffach.app.features.chat.api.ChatRepository
import com.konffach.app.features.chat.screen.InMemoryChatRepository
import com.konffach.app.features.home.api.HomeRepository
import com.konffach.app.features.home.screen.HomeRepositoryImpl
import com.konffach.app.features.auth.api.TokenRepository
import com.konffach.app.features.auth.screen.TokenRepositoryImpl
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.BindingContainer

/**
 * Binding container for repository implementations (interface → impl bindings).
 * Included in [AppGraph] via [DependencyGraph.bindingContainers].
 */
@BindingContainer
interface RepositoryBindings {

    @Binds val AuthRepositoryImpl.bind: AuthRepository
    @Binds val TokenRepositoryImpl.bind: TokenRepository
    @Binds val HomeRepositoryImpl.bind: HomeRepository
    @Binds val InMemoryChatRepository.bind: ChatRepository
}
