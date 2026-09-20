package com.cristian.timesorteio.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cristian.timesorteio.util.SoundManager

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: androidx.compose.ui.Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = {
            if (enabled) {
                SoundManager.playClick()
                onClick()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = MaterialTheme.shapes.large
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: androidx.compose.ui.Modifier = Modifier,
    enabled: Boolean = true
) {
    androidx.compose.material3.OutlinedButton(
        onClick = {
            if (enabled) {
                SoundManager.playClick()
                onClick()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = MaterialTheme.shapes.large
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}
