package com.konffach.app.features.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import konffach.sharedui.generated.resources.action_back
import konffach.sharedui.generated.resources.ic_arrow_back_ios
import konffach.sharedui.generated.resources.log_out
import konffach.sharedui.generated.resources.settings_title
import org.jetbrains.compose.resources.painterResource
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
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_back_ios),
                        contentDescription = stringResource(Res.string.action_back),
                    )
                }
            },
        )
        HorizontalDivider()

        Button(
            onClick = { state.onIntent(SettingsIntent.ClearTokensClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag(SettingsScreenTestTags.LOG_OUT),
        ) {
            Text(stringResource(Res.string.log_out))
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
