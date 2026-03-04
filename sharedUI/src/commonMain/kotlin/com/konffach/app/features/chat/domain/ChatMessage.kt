package com.konffach.app.features.chat.domain

data class ChatMessage(
    val id: String,
    val dialogId: String,
    val author: String,
    val text: String,
    val isMine: Boolean,
)
