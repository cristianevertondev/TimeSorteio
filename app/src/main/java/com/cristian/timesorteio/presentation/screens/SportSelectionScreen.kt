package com.cristian.timesorteio.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cristian.timesorteio.domain.model.Sport
import com.cristian.timesorteio.presentation.components.PrimaryButton
import com.cristian.timesorteio.presentation.components.SportCard

@Composable
fun SportSelectionScreen(
    onNext: () -> Unit,
    selectedSport: Sport?,
    onSportSelected: (Sport) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Escolha o esporte",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(Sport.entries) { sport ->
                SportCard(
                    sport = sport,
                    isSelected = selectedSport == sport,
                    onClick = { onSportSelected(sport) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        PrimaryButton(
            text = "Continuar",
            onClick = onNext,
            enabled = selectedSport != null,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}
