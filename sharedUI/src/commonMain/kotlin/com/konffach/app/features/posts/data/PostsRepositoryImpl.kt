package com.konffach.app.features.posts.data

import com.konffach.app.di.AppScope
import com.konffach.app.network.ApiResult
import com.konffach.app.network.safeApiCall
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

@SingleIn(AppScope::class)
@Inject
class PostsRepositoryImpl(
    private val httpClient: HttpClient,
) : PostsRepository {

    override suspend fun getPosts(): ApiResult<List<PostDto>> = safeApiCall {
        httpClient.get("posts").body<List<PostDto>>()
    }
}
