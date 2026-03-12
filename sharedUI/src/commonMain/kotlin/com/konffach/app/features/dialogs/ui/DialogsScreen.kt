package com.konffach.app.features.dialogs.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource
import com.konffach.app.features.dialogs.screen.ChatSummary
import konffach.sharedui.generated.resources.Res
import konffach.sharedui.generated.resources.dialogs_avatar
import konffach.sharedui.generated.resources.dialogs_title

sealed interface DialogsIntent

data class DialogsScreenState(
    val dialogs: List<ChatSummary>,
    val onIntent: (DialogsIntent) -> Unit = {},
) {
    companion object {
        val Default = DialogsScreenState(
            dialogs = emptyList(),
        )
    }
}


@Composable
fun DialogsScreen(
    state: DialogsScreenState,
    onOpenChat: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(Res.string.dialogs_title),
            style = MaterialTheme.typography.headlineMedium,
        )
        HorizontalDivider(Modifier.padding(vertical = 8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(state.dialogs) { chat ->
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenChat(chat.id) },
                    leadingContent = {
                        AsyncImage(
                            modifier = Modifier.height(40.dp),
                            model = chat.avatarUrl,
                            contentDescription = stringResource(Res.string.dialogs_avatar),
                        )
                    },
                    headlineContent = { Text(chat.title) },
                    supportingContent = { Text(chat.lastMessage) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun DialogsScreenPreview() {
    val previewState = DialogsScreenState.Default
    DialogsScreen(
        state = previewState,
        onOpenChat = {},
    )
}
