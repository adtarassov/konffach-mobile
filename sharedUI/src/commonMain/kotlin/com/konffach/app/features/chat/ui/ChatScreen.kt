package com.konffach.app.features.chat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konffach.app.components.Toolbar
import com.konffach.app.features.chat.screen.ChatMessage
import com.konffach.app.features.home.ui.HomeScreenTestTags
import konffach.sharedui.generated.resources.Res
import konffach.sharedui.generated.resources.chat_message_input_mock
import konffach.sharedui.generated.resources.home_settings_action
import konffach.sharedui.generated.resources.ic_settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

sealed interface ChatIntent

data class ChatScreenState(
    val dialogId: String,
    val messages: List<ChatMessage>,
    val onIntent: (ChatIntent) -> Unit = {},
) {
    companion object {
        fun preview() = ChatScreenState(
            dialogId = "",
            messages = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatScreenState,
    onBack: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Konffach",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            actions = {
                IconButton(
                    onClick = onOpenSettings,
                    modifier = Modifier.testTag(HomeScreenTestTags.SETTINGS_BUTTON),
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_settings),
                        contentDescription = stringResource(Res.string.home_settings_action),
                    )
                }
            }
        )
        HorizontalDivider()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
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
    val previewState = ChatScreenState.preview()
    ChatScreen(
        state = previewState,
        onBack = {},
        onOpenSettings = {},
    )
}
