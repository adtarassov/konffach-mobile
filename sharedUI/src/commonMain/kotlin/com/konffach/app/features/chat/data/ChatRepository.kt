package com.konffach.app.features.chat.data

import com.konffach.app.features.chat.domain.ChatMessage
import com.konffach.app.features.dialogs.domain.ChatSummary

interface ChatRepository {
    fun getChats(): List<ChatSummary>
    fun getMessages(dialogId: String): List<ChatMessage>
}
