package com.cristian.timesorteio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.cristian.timesorteio.presentation.navigation.TimeSorteioNavHost
import com.cristian.timesorteio.ui.theme.TimeSorteioTheme
import com.cristian.timesorteio.presentation.viewmodel.MatchViewModel
import com.cristian.timesorteio.presentation.viewmodel.MatchUiState
import com.cristian.timesorteio.util.SoundManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SoundManager.init(this)
        setContent {
            TimeSorteioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val viewModel: MatchViewModel = viewModel()
                    val uiState: MatchUiState by viewModel.uiState.collectAsState()

                    TimeSorteioNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        uiState = uiState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.release()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimeSorteioTheme {
        SportPreview()
    }
}

@Composable
private fun SportPreview() {
    com.cristian.timesorteio.presentation.components.SportCard(
        sport = com.cristian.timesorteio.domain.model.Sport.FOOTBALL,
        isSelected = true,
        onClick = {},
        modifier = Modifier.padding(16.dp)
    )
}
