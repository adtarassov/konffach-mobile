package com.konffach.app.features.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konffach.app.components.LoadingOverlay
import konffach.sharedui.generated.resources.Res
import konffach.sharedui.generated.resources.auth_login
import konffach.sharedui.generated.resources.auth_password
import konffach.sharedui.generated.resources.auth_sign_in
import konffach.sharedui.generated.resources.auth_sign_up
import konffach.sharedui.generated.resources.auth_signing_in
import konffach.sharedui.generated.resources.auth_signing_up
import org.jetbrains.compose.resources.stringResource

data class AuthScreenState(
    val login: String,
    val password: String,
    val isLoading: Boolean,
    val errorMessage: String?,
    val onIntent: (AuthIntent) -> Unit,
) {
    companion object {
        fun preview() = AuthScreenState(
            login = "",
            password = "",
            isLoading = false,
            errorMessage = "Test Error",
            onIntent = {}
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AuthScreen(
    state: AuthScreenState,
    modifier: Modifier = Modifier,
) {
    LoadingOverlay(isLoading = state.isLoading) {
        val verticalScrollState = rememberScrollState()
        Column(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(state = verticalScrollState),
            verticalArrangement = spacedBy(12.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Konffach",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
            )
            OutlinedTextField(
                value = state.login,
                onValueChange = { state.onIntent(AuthIntent.LoginChanged(it)) },
                label = { Text(stringResource(Res.string.auth_login)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(AuthScreenTestTags.LOGIN_INPUT),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                enabled = !state.isLoading,
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = { state.onIntent(AuthIntent.PasswordChanged(it)) },
                label = { Text(stringResource(Res.string.auth_password)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(AuthScreenTestTags.PASSWORD_INPUT),
                enabled = !state.isLoading,
            )
            state.errorMessage?.let { msg ->
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(AuthScreenTestTags.SIGN_IN_BUTTON),
                onClick = { state.onIntent(AuthIntent.SignInClicked) },
                enabled = !state.isLoading,
            ) {
                Text(
                    stringResource(
                        if (state.isLoading) {
                            Res.string.auth_signing_in
                        } else {
                            Res.string.auth_sign_in
                        }
                    )
                )
            }
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { state.onIntent(AuthIntent.SignUpClicked) },
                enabled = !state.isLoading,
            ) {
                Text(
                    stringResource(
                        if (state.isLoading) {
                            Res.string.auth_signing_up
                        } else {
                            Res.string.auth_sign_up
                        }
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    AuthScreen(state = AuthScreenState.preview())
}
