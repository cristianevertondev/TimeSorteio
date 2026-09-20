package com.cristian.timesorteio.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cristian.timesorteio.domain.model.Sport
import com.cristian.timesorteio.presentation.components.PrimaryButton
import com.cristian.timesorteio.presentation.components.SecondaryButton

@Composable
fun ReviewScreen(
    uiState: com.cristian.timesorteio.presentation.viewmodel.MatchUiState,
    onDraw: () -> Unit,
    onBack: () -> Unit
) {
    val names = uiState.playerNames.filter { it.isNotBlank() }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Revisar configuração",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )
        }
        item {
            ReviewItem("Esporte", uiState.selectedSport?.displayName ?: "")
        }
        item {
            ReviewItem("Pessoas por time", "${uiState.playersPerTeam}")
        }
        item {
            ReviewItem("Total de pessoas", "${uiState.totalPlayers}")
        }
        item {
            ReviewItem("Times completos", "${uiState.teamCount}")
        }
        if (uiState.hasOutsideTeam) {
            item {
                ReviewItem("Time de fora", if (uiState.outsideTeamSameQuantity) "Mesma quantidade" else "Pessoas restantes")
            }
        }
        item {
            Text(
                text = "Participantes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }
        items(names) { name ->
            Text(
                text = "• $name",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
            )
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
                text = "Sortear times",
                onClick = onDraw,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ReviewItem(label: String, value: String) {
    Column(modifier = androidx.compose.ui.Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}
