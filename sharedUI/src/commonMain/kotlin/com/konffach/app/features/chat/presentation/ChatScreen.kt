package com.konffach.app.features.chat.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konffach.app.features.chat.domain.ChatMessage

data class ChatScreenState(
    val dialogId: String,
    val messages: List<ChatMessage>,
) {
    companion object {
        val Default = ChatScreenState(
            dialogId = "",
            messages = emptyList(),
        )
    }
}

@Composable
fun ChatScreen(
    state: ChatScreenState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Chat ${state.dialogId}",
                style = MaterialTheme.typography.headlineMedium,
            )
            Button(onClick = onBack) {
                Text("Back")
            }
        }
        Spacer(Modifier.height(8.dp))
        Divider()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(state.messages) { message ->
                Text(
                    text = "${message.author}: ${message.text}",
                    style = if (message.isMine) {
                        MaterialTheme.typography.bodyLarge
                    } else {
                        MaterialTheme.typography.bodyMedium
                    },
                )
            }
        }

        Divider()
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            label = { Text("Message input (mock)") },
        )
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    val previewState = ChatScreenState.Default
    ChatScreen(
        state = previewState,
        onBack = {},
    )
}
