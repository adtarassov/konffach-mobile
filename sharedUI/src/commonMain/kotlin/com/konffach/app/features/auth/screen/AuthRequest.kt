package com.konffach.app.features.auth.screen

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val login: String,
    val password: String,
)
