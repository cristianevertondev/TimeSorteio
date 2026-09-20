package com.cristian.timesorteio.presentation.navigation

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cristian.timesorteio.presentation.screens.OutsideTeamSelectionScreen
import com.cristian.timesorteio.presentation.screens.SameQuantitySelectionScreen
import com.cristian.timesorteio.presentation.screens.PlayerNamesScreen
import com.cristian.timesorteio.presentation.screens.PlayersPerTeamScreen
import com.cristian.timesorteio.presentation.screens.ResultScreen
import com.cristian.timesorteio.presentation.screens.ReviewScreen
import com.cristian.timesorteio.presentation.screens.SportSelectionScreen
import com.cristian.timesorteio.presentation.screens.TotalPlayersScreen
import com.cristian.timesorteio.presentation.viewmodel.MatchViewModel
import com.cristian.timesorteio.presentation.viewmodel.MatchUiState

@Composable
fun TimeSorteioNavHost(
    navController: NavHostController,
    viewModel: MatchViewModel,
    uiState: MatchUiState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TimeSorteioRoute.SportSelection.route,
        modifier = modifier
    ) {
        composable(TimeSorteioRoute.SportSelection.route) {
            SportSelectionScreen(
                selectedSport = uiState.selectedSport,
                onSportSelected = { viewModel.selectSport(it) },
                onNext = {
                    viewModel.navigate(TimeSorteioRoute.PlayersPerTeam.route)
                    navController.navigate(TimeSorteioRoute.PlayersPerTeam.route)
                }
            )
        }
        composable(TimeSorteioRoute.PlayersPerTeam.route) {
            PlayersPerTeamScreen(
                uiState = uiState,
                onPlayersPerTeamChanged = { viewModel.setPlayersPerTeam(it) },
                onNext = {
                    viewModel.navigate(TimeSorteioRoute.TotalPlayers.route)
                    navController.navigate(TimeSorteioRoute.TotalPlayers.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                errorMessage = uiState.errorMessage
            )
        }
        composable(TimeSorteioRoute.TotalPlayers.route) {
            TotalPlayersScreen(
                uiState = uiState,
                onTotalPlayersChanged = { viewModel.setTotalPlayers(it) },
                onNext = {
                    viewModel.navigate(TimeSorteioRoute.OutsideTeamSelection.route)
                    navController.navigate(TimeSorteioRoute.OutsideTeamSelection.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(TimeSorteioRoute.OutsideTeamSelection.route) {
            OutsideTeamSelectionScreen(
                uiState = uiState,
                onOutsideTeamChanged = { viewModel.setHasOutsideTeam(it) },
                onNext = {
                    if (uiState.hasOutsideTeam) {
                        viewModel.navigate(TimeSorteioRoute.SameQuantitySelection.route)
                        navController.navigate(TimeSorteioRoute.SameQuantitySelection.route)
                    } else {
                        if (uiState.playerNames.size != uiState.totalPlayers) {
                            val names = List(uiState.totalPlayers) { "" }
                            viewModel.setPlayerNames(names)
                        }
                        viewModel.navigate(TimeSorteioRoute.PlayerNames.route)
                        navController.navigate(TimeSorteioRoute.PlayerNames.route)
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(TimeSorteioRoute.SameQuantitySelection.route) {
            SameQuantitySelectionScreen(
                uiState = uiState,
                onSameQuantityChanged = { viewModel.setOutsideTeamSameQuantity(it) },
                onNext = {
                    if (uiState.playerNames.size != uiState.totalPlayers) {
                        val names = List(uiState.totalPlayers) { "" }
                        viewModel.setPlayerNames(names)
                    }
                    viewModel.navigate(TimeSorteioRoute.PlayerNames.route)
                    navController.navigate(TimeSorteioRoute.PlayerNames.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(TimeSorteioRoute.PlayerNames.route) {
            PlayerNamesScreen(
                uiState = uiState,
                onNameChanged = { index, name -> viewModel.updatePlayerName(index, name) },
                onNext = {
                    viewModel.navigate(TimeSorteioRoute.Review.route)
                    navController.navigate(TimeSorteioRoute.Review.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                errorMessage = uiState.errorMessage
            )
        }
        composable(TimeSorteioRoute.Review.route) {
            ReviewScreen(
                uiState = uiState,
                onDraw = {
                    viewModel.validateAndDraw()
                    navController.navigate(TimeSorteioRoute.Result.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(TimeSorteioRoute.Result.route) {
            val context = LocalContext.current
            val sport = uiState.selectedSport ?: return@composable
            uiState.drawResult?.let { result ->
                val pdfDocument = viewModel.getPdfDocument()

                val shareLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) { }

                val createDocumentLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.CreateDocument("application/pdf")
                ) { uri ->
                    uri?.let {
                        context.contentResolver.openOutputStream(it)?.use { outputStream ->
                            pdfDocument?.writeTo(outputStream)
                        }
                        pdfDocument?.close()
                    }
                }

                ResultScreen(
                    result = result,
                    sport = sport,
                    hasOutsideTeam = uiState.hasOutsideTeam,
                    onShare = {
                        val shareIntent = viewModel.getShareIntent(context)
                        shareLauncher.launch(shareIntent)
                    },
                    onSavePdf = {
                        val fileName = viewModel.getPdfFileName()
                        createDocumentLauncher.launch(fileName)
                    },
                    onRedraw = { viewModel.redraw() },
                    onBack = {
                        viewModel.navigate(TimeSorteioRoute.SportSelection.route)
                        navController.navigate(TimeSorteioRoute.SportSelection.route) {
                            popUpTo(TimeSorteioRoute.SportSelection.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
