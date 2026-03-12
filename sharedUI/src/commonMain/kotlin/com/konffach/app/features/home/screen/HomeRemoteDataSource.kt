package com.konffach.app.features.home.screen

import com.konffach.app.di.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(AppScope::class)
class HomeRemoteDataSource {

    suspend fun fetchItems(): List<HomeItem> = emptyList()
}
