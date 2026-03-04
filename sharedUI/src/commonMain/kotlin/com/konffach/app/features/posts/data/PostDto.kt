package com.konffach.app.features.posts.data

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)
