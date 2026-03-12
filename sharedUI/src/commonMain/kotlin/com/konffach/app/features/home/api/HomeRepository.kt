package com.konffach.app.features.home.api

import com.konffach.app.features.home.screen.HomeItem

interface HomeRepository {

    suspend fun loadItems(): List<HomeItem>
}
