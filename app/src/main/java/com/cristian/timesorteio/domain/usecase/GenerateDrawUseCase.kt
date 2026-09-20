package com.cristian.timesorteio.domain.usecase

import com.cristian.timesorteio.domain.model.DrawResult
import com.cristian.timesorteio.domain.model.MatchConfig
import com.cristian.timesorteio.domain.model.Player
import com.cristian.timesorteio.domain.model.Team
import kotlin.random.Random

class GenerateDrawUseCase {
    operator fun invoke(config: MatchConfig, players: List<Player>): DrawResult {
        val shuffled = players.shuffled(Random.Default)
        val teams = mutableListOf<Team>()
        val outsideTeam = mutableListOf<Player>()

        val fullTeamsCount = config.totalPlayers / config.playersPerTeam
        val remainder = config.totalPlayers % config.playersPerTeam

        if (config.hasOutsideTeam && remainder > 0) {
            val outsideTeamSize = if (config.outsideTeamSameQuantity) config.playersPerTeam else remainder
            val teamsWithFullSize = fullTeamsCount

            var index = 0
            for (i in 0 until teamsWithFullSize) {
                val teamPlayers = shuffled.subList(index, index + config.playersPerTeam)
                teams.add(Team("Time ${i + 1}", teamPlayers.toList()))
                index += config.playersPerTeam
            }
            outsideTeam.addAll(shuffled.subList(index, index + outsideTeamSize))
        } else if (config.hasOutsideTeam && remainder == 0) {
            var index = 0
            for (i in 0 until fullTeamsCount) {
                val teamPlayers = shuffled.subList(index, index + config.playersPerTeam)
                teams.add(Team("Time ${i + 1}", teamPlayers.toList()))
                index += config.playersPerTeam
            }
        } else {
            var index = 0
            for (i in 0 until fullTeamsCount) {
                val teamPlayers = shuffled.subList(index, index + config.playersPerTeam)
                teams.add(Team("Time ${i + 1}", teamPlayers.toList()))
                index += config.playersPerTeam
            }
            if (remainder > 0) {
                val lastTeamPlayers = shuffled.subList(index, shuffled.size)
                teams.add(Team("Time ${fullTeamsCount + 1}", lastTeamPlayers.toList()))
            }
        }

        return DrawResult(teams, outsideTeam)
    }
}
