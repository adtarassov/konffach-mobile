package com.konffach.app.features.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expirationDate: String,
)
