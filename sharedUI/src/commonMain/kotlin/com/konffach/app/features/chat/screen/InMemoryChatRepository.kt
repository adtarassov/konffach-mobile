package com.konffach.app.features.chat.screen

import com.konffach.app.features.chat.api.ChatRepository
import com.konffach.app.features.dialogs.screen.ChatSummary
import com.konffach.app.di.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@Inject
class InMemoryChatRepository : ChatRepository {
    private val chats = listOf(
        ChatSummary(
            id = "1",
            title = "Compose Multiplatform",
            lastMessage = "Let's ship KMP chat!",
            avatarUrl = "https://avatars.githubusercontent.com/u/3170703?v=4",
        ),
        ChatSummary(
            id = "2",
            title = "Navigation3",
            lastMessage = "Back stack owned by us.",
            avatarUrl = "https://developer.android.com/static/images/logos/android.svg",
        ),
        ChatSummary(
            id = "3",
            title = "General",
            lastMessage = "Hello from Konffach 👋",
            avatarUrl = "https://placekitten.com/200/200",
        ),
    )

    override fun getChats(): List<ChatSummary> = chats

    override fun getMessages(dialogId: String): List<ChatMessage> {
        return listOf(
            ChatMessage(
                id = "m1",
                dialogId = dialogId,
                author = "Alice",
                text = "Hi! This is dialog $dialogId",
                isMine = false,
            ),
            ChatMessage(
                id = "m2",
                dialogId = dialogId,
                author = "Me",
                text = "Looks good, navigation works.",
                isMine = true,
            ),
        )
    }
}
