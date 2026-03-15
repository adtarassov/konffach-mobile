package com.konffach.app.features.chat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.konffach.app.features.chat.screen.ChatMessageContent

@Immutable
data class ChatMessageItemState(
    val id: String,
    val author: String,
    val isMine: Boolean,
    val contents: List<ChatMessageContent>,
    val text: String?,
) {
    companion object {
        fun previewMine() = ChatMessageItemState(
            id = "preview-mine",
            author = "Me",
            isMine = true,
            contents = listOf(ChatMessageContent.Text("Hi! This is my message.")),
            text = "Hi! This is my message.",
        )

        fun previewOther() = ChatMessageItemState(
            id = "preview-other",
            author = "Alice",
            isMine = false,
            contents = listOf(ChatMessageContent.Text("And this one came from another person.")),
            text = "And this one came from another person.",
        )
    }
}

@Composable
fun ChatMessageItem(
    state: ChatMessageItemState,
    modifier: Modifier = Modifier,
) {
    val text = state.text ?: return
    val bubbleColor = if (state.isMine) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (state.isMine) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (state.isMine) Arrangement.End else Arrangement.Start,
    ) {
        Column(
            modifier = Modifier.widthIn(max = 320.dp),
            horizontalAlignment = if (state.isMine) Alignment.End else Alignment.Start,
        ) {
            Text(
                text = state.author,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp),
            )
            Surface(
                color = bubbleColor,
                contentColor = contentColor,
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                    bottomStart = if (state.isMine) 24.dp else 6.dp,
                    bottomEnd = if (state.isMine) 6.dp else 24.dp,
                ),
                tonalElevation = 2.dp,
                shadowElevation = 1.dp,
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChatMessageItemPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ChatMessageItem(state = ChatMessageItemState.previewOther())
            ChatMessageItem(state = ChatMessageItemState.previewMine())
        }
    }
}
