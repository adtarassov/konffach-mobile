package com.konffach.app.features.posts.data

import com.konffach.app.network.ApiResult

interface PostsRepository {
    suspend fun getPosts(): ApiResult<List<PostDto>>
}
