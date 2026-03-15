package com.konffach.app.features.chat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konffach.app.features.home.ui.HomeScreenTestTags
import konffach.sharedui.generated.resources.Res
import konffach.sharedui.generated.resources.home_settings_action
import konffach.sharedui.generated.resources.ic_settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

sealed interface ChatIntent {
    data class InputFieldChanged(val value: String) : ChatIntent
    data object SendMessage : ChatIntent
}

data class ChatScreenState(
    val dialogId: String,
    val inputFieldState: InputFiledState,
    val messages: List<ChatMessageItemState>,
    val onIntent: (ChatIntent) -> Unit,
) {
    companion object {
        fun preview() = ChatScreenState(
            dialogId = "",
            inputFieldState = InputFiledState.preview(),
            messages = listOf(
                ChatMessageItemState.previewOther(),
                ChatMessageItemState.previewMine(),
                ChatMessageItemState.previewMine(),
            ),
            onIntent = {}
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
    val listState = rememberLazyListState()

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    Column(
        modifier = modifier
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
            )
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
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .imePadding() // need to check
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                reverseLayout = true
            ) {
                items(
                    items = state.messages.asReversed(),
                    key = { message -> message.id },
                ) { message ->
                    ChatMessageItem(state = message)
                }
            }

            InputFiled(
                state = state.inputFieldState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }
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
