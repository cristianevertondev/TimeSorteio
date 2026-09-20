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

@Composable
fun SameQuantitySelectionScreen(
    uiState: com.cristian.timesorteio.presentation.viewmodel.MatchUiState,
    onSameQuantityChanged: (Boolean) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.same_quantity_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
        )
        if (uiState.outsideTeamSameQuantity) {
            Text(
                text = stringResource(R.string.same_quantity_yes_description),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        } else {
            Text(
                text = stringResource(R.string.same_quantity_no_description),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        SecondaryButton(
            text = stringResource(R.string.no),
            onClick = {
                onSameQuantityChanged(false)
                onNext()
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        PrimaryButton(
            text = stringResource(R.string.yes),
            onClick = {
                onSameQuantityChanged(true)
                onNext()
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        SecondaryButton(
            text = stringResource(R.string.back_button),
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
