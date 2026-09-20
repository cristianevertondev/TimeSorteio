package com.cristian.timesorteio.presentation.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cristian.timesorteio.R
import com.cristian.timesorteio.presentation.components.PrimaryButton
import com.cristian.timesorteio.presentation.components.SecondaryButton
import com.cristian.timesorteio.presentation.viewmodel.MatchUiState

@Composable
fun TotalPlayersScreen(
    uiState: MatchUiState,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onTotalPlayersChanged: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.total_players_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
        )
        com.cristian.timesorteio.presentation.components.NumberInput(
            value = uiState.totalPlayers,
            onValueChange = onTotalPlayersChanged,
            label = stringResource(R.string.total_players_label),
            min = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        SecondaryButton(
            text = stringResource(R.string.back_button),
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        PrimaryButton(
            text = stringResource(R.string.continue_button),
            onClick = onNext,
            enabled = uiState.totalPlayers >= 1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
