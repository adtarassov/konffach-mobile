package com.konffach.app.features.chat.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.konffach.app.features.auth.ui.AuthScreenTestTags
import konffach.sharedui.generated.resources.Res
import konffach.sharedui.generated.resources.ic_send
import konffach.sharedui.generated.resources.message
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Immutable
data class InputFiledState(
    val value: String,
    val onIntent: (ChatIntent) -> Unit = {},
) {
    companion object {
        fun preview() = InputFiledState(
            value = "Test",
        )
    }
}

@Composable
fun InputFiled(
    state: InputFiledState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = state.value,
            onValueChange = { state.onIntent(ChatIntent.InputFieldChanged(it)) },
            label = { Text(stringResource(Res.string.message)) },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .testTag(AuthScreenTestTags.LOGIN_INPUT),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
        IconButton(onClick = { state.onIntent(ChatIntent.SendMessage) }) {
            Icon(
                painter = painterResource(Res.drawable.ic_send),
                contentDescription = null,
            )
        }
    }
}

@Composable
@Preview
private fun InputFiled_Preview() {
    InputFiled(
        state = InputFiledState(
            value = "Test",
        ),
        modifier = Modifier
    )
}


