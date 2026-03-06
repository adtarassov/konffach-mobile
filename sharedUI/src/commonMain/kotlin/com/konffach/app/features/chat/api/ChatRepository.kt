package com.konffach.app.features.chat.api

import com.konffach.app.features.chat.screen.ChatMessage
import com.konffach.app.features.dialogs.screen.ChatSummary

interface ChatRepository {
    fun getChats(): List<ChatSummary>
    fun getMessages(dialogId: String): List<ChatMessage>
}
