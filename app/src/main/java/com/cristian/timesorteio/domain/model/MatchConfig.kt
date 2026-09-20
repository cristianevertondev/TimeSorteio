package com.cristian.timesorteio.domain.model

data class MatchConfig(
    val sport: Sport,
    val totalPlayers: Int,
    val playersPerTeam: Int,
    val teamCount: Int,
    val hasOutsideTeam: Boolean,
    val outsideTeamSameQuantity: Boolean
)
