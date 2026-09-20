package com.cristian.timesorteio.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cristian.timesorteio.presentation.components.PrimaryButton
import com.cristian.timesorteio.presentation.components.SecondaryButton

@Composable
fun TeamCountScreen(
    uiState: com.cristian.timesorteio.presentation.viewmodel.MatchUiState,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onTeamCountChanged: (Int) -> Unit,
    errorMessage: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quantos times deseja formar?",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
        )
        com.cristian.timesorteio.presentation.components.NumberInput(
            value = uiState.teamCount,
            onValueChange = onTeamCountChanged,
            label = "Número de times",
            min = 2,
            max = uiState.totalPlayers,
            error = errorMessage,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        SecondaryButton(
            text = "Voltar",
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        PrimaryButton(
            text = "Continuar",
            onClick = onNext,
            enabled = uiState.teamCount >= 2 && uiState.teamCount <= uiState.totalPlayers,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
