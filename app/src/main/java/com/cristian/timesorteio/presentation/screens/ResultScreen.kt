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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cristian.timesorteio.R
import com.cristian.timesorteio.domain.model.DrawResult
import com.cristian.timesorteio.presentation.components.PrimaryButton
import com.cristian.timesorteio.presentation.components.SecondaryButton
import com.cristian.timesorteio.presentation.components.TeamCard

@Composable
fun ResultScreen(
    result: DrawResult,
    sport: com.cristian.timesorteio.domain.model.Sport,
    hasOutsideTeam: Boolean,
    onShare: () -> Unit,
    onSavePdf: () -> Unit,
    onRedraw: () -> Unit,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "${sport.emoji} ${stringResource(R.string.result_title)}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
            Text(
                text = "${result.teams.size} ${if (result.teams.size != 1) "times" else "time"} • ${result.teams.sumOf { it.players.size }} ${if (result.teams.sumOf { it.players.size } != 1) "pessoas" else "pessoa"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        items(result.teams) { team ->
            TeamCard(team = team, modifier = Modifier.fillMaxWidth())
        }
        if (hasOutsideTeam && result.reserves.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.result_outside_team, result.reserves.size),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
            items(result.reserves) { player ->
                Text(
                    text = "• ${player.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 8.dp)
                        .padding(12.dp)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(
                text = stringResource(R.string.share_button),
                onClick = onShare,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            SecondaryButton(
                text = stringResource(R.string.save_pdf_button),
                onClick = onSavePdf,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(
                text = stringResource(R.string.redraw_button),
                onClick = onRedraw,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            SecondaryButton(
                text = stringResource(R.string.back_to_start_button),
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
