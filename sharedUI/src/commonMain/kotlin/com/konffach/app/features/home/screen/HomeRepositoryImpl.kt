package com.konffach.app.features.home.screen

import com.konffach.app.di.AppScope
import com.konffach.app.features.home.api.HomeRepository
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.delay

@Inject
@SingleIn(AppScope::class)
class HomeRepositoryImpl(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSource: HomeLocalDataSource,
) : HomeRepository {

    override suspend fun loadItems(): List<HomeItem> {
        delay(300)
        val cached = localDataSource.getCachedItems()
        if (cached.isNotEmpty()) return cached
        val remote = remoteDataSource.fetchItems()
        return remote.ifEmpty { mockItems }
    }

    private val mockItems = listOf(
        HomeItem(id = "1", title = "Welcome", subtitle = "Your conversations"),
        HomeItem(id = "2", title = "Quick start", subtitle = "Open dialogs to chat"),
        HomeItem(id = "3", title = "Settings", subtitle = "Coming soon"),
    )
}
