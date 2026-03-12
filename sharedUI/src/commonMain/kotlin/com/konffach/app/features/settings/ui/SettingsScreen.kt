package com.konffach.app.features.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import konffach.sharedui.generated.resources.Res
import konffach.sharedui.generated.resources.settings_clear_tokens
import konffach.sharedui.generated.resources.settings_content_placeholder
import konffach.sharedui.generated.resources.settings_title
import org.jetbrains.compose.resources.stringResource

sealed interface SettingsIntent {
    data object ClearTokensClicked : SettingsIntent
}

data class SettingsScreenState(
    val onIntent: (SettingsIntent) -> Unit = {},
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsScreenState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.settings_title),
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Text("←")
                }
            },
        )

        HorizontalDivider()

        Text(
            text = stringResource(Res.string.settings_content_placeholder),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp),
        )

        Button(
            onClick = { state.onIntent(SettingsIntent.ClearTokensClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(SettingsScreenTestTags.CLEAR_TOKENS_BUTTON),
        ) {
            Text(stringResource(Res.string.settings_clear_tokens))
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        state = SettingsScreenState(),
        onBack = {},
    )
}
