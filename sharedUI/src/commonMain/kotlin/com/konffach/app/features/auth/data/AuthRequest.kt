package com.konffach.app.features.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val login: String,
    val password: String,
)
