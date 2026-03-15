package com.konffach.app.features.chat.screen

sealed interface ChatMessageContent {
    data class Text(
        val value: String,
    ) : ChatMessageContent

    data class Video(
        val url: String,
        val previewUrl: String? = null,
        val durationSeconds: Long? = null,
    ) : ChatMessageContent
}
