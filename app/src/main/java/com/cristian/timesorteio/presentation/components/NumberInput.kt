package com.cristian.timesorteio.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NumberInput(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String,
    modifier: androidx.compose.ui.Modifier = Modifier,
    min: Int = 0,
    max: Int = 1000,
    error: String? = null
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value.toString(),
            onValueChange = { newValue ->
                val parsed = newValue.toIntOrNull()
                if (parsed != null) {
                    onValueChange(parsed)
                }
            },
            label = { Text(label) },
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors()
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
