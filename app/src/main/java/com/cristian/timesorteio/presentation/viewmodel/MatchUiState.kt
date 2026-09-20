package com.cristian.timesorteio.presentation.viewmodel

import com.cristian.timesorteio.domain.model.DrawResult
import com.cristian.timesorteio.domain.model.Sport

data class MatchUiState(
    val currentRoute: String = "sport",
    val selectedSport: Sport? = null,
    val playersPerTeam: Int = 0,
    val totalPlayers: Int = 0,
    val teamCount: Int = 0,
    val hasOutsideTeam: Boolean = false,
    val outsideTeamSameQuantity: Boolean = false,
    val playerNames: List<String> = emptyList(),
    val drawResult: DrawResult? = null,
    val errorMessage: String? = null
)
