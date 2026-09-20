package com.cristian.timesorteio.presentation.navigation

sealed class TimeSorteioRoute(val route: String) {
    data object SportSelection : TimeSorteioRoute("sport")
    data object PlayersPerTeam : TimeSorteioRoute("players_per_team")
    data object TotalPlayers : TimeSorteioRoute("total_players")
    data object OutsideTeamSelection : TimeSorteioRoute("outside_team_selection")
    data object SameQuantitySelection : TimeSorteioRoute("same_quantity_selection")
    data object PlayerNames : TimeSorteioRoute("player_names")
    data object Review : TimeSorteioRoute("review")
    data object Result : TimeSorteioRoute("result")
}
