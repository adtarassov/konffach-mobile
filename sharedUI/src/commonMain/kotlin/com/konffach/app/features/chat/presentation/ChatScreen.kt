package com.konffach.app.features.chat.presentation

import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konffach.app.features.chat.domain.ChatMessage
import konffach.sharedui.generated.resources.Res
import konffach.sharedui.generated.resources.chat_back
import konffach.sharedui.generated.resources.chat_message_input_mock
import konffach.sharedui.generated.resources.chat_title
import org.jetbrains.compose.resources.stringResource

data class ChatScreenState(
    val dialogId: String,
    val messages: List<ChatMessage>,
    val onIntent: (ChatIntent) -> Unit,
) {
    companion object {
        val Default = ChatScreenState(
            dialogId = "",
            messages = emptyList(),
            onIntent = {},
        )
    }
}

@Composable
fun ChatScreen(
    state: ChatScreenState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(Res.string.chat_title, state.dialogId),
                style = MaterialTheme.typography.headlineMedium,
            )
            Button(onClick = { state.onIntent(ChatIntent.BackClicked) }) {
                Text(stringResource(Res.string.chat_back))
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider()

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

        HorizontalDivider()
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            label = { Text(stringResource(Res.string.chat_message_input_mock)) },
        )
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    val previewState = ChatScreenState.Default
    ChatScreen(state = previewState)
}
