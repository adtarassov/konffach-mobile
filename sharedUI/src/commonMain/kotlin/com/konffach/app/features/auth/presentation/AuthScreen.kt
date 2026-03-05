package com.konffach.app.features.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
            errorMessage = null,
            onIntent = {}
        )
    }
}

@Composable
fun AuthScreen(
    state: AuthScreenState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = state.login,
            onValueChange = { state.onIntent(AuthIntent.LoginChanged(it)) },
            label = { Text("Login") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            enabled = !state.isLoading,
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = { state.onIntent(AuthIntent.PasswordChanged(it)) },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            enabled = !state.isLoading,
        )
        state.errorMessage?.let { msg ->
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            onClick = { state.onIntent(AuthIntent.SignInClicked) },
            enabled = !state.isLoading,
        ) {
            Text(if (state.isLoading) "Signing in…" else "Sign in")
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = { state.onIntent(AuthIntent.SignUpClicked) },
            enabled = !state.isLoading,
        ) {
            Text(if (state.isLoading) "Signing up…" else "Sign up")
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    AuthScreen(state = AuthScreenState.preview())
}
