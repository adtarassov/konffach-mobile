package com.konffach.app.features.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class AuthScreenState(
    val isLoading: Boolean,
    val onIntent: (AuthIntent) -> Unit,
) {
    companion object {
        val Default = AuthScreenState(
            isLoading = false,
            onIntent = {},
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
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Auth screen (state: ${state.isLoading})")

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                state.onIntent(AuthIntent.SignInClicked)
            },
        ) {
            Text("Sign In")
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    val previewState = AuthScreenState.Default
    AuthScreen(state = previewState)
}
