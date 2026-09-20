package com.cristian.timesorteio.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cristian.timesorteio.presentation.components.PlayerNameField
import com.cristian.timesorteio.presentation.components.PrimaryButton
import com.cristian.timesorteio.presentation.components.SecondaryButton

@Composable
fun PlayerNamesScreen(
    uiState: com.cristian.timesorteio.presentation.viewmodel.MatchUiState,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onNameChanged: (Int, String) -> Unit,
    errorMessage: String?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Informe os nomes dos jogadores",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )
        }
        itemsIndexed(uiState.playerNames) { index, name ->
            PlayerNameField(
                value = name,
                onValueChange = { onNameChanged(index, it) },
                index = index,
                isError = name.isBlank(),
                errorMessage = if (name.isBlank()) "Nome não pode ser vazio" else null,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (errorMessage != null) {
            item {
                com.cristian.timesorteio.presentation.components.ErrorMessage(
                    message = errorMessage,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SecondaryButton(
                text = "Voltar",
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(
                text = "Revisar",
                onClick = onNext,
                enabled = uiState.playerNames.all { it.isNotBlank() } && uiState.playerNames.distinctBy { it.lowercase() }.size == uiState.playerNames.size,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
