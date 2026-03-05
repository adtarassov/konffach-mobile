package com.konffach.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingOverlay(
    isLoading: Boolean,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()
        if (isLoading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.5f))
            ) {
                LoadingIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}


@Composable
@Preview
private fun LoadingOverlayPreview() {
    LoadingOverlay(isLoading = true) {

    }
}