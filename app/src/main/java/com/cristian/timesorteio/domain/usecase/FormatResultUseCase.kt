package com.cristian.timesorteio.domain.usecase

import com.cristian.timesorteio.domain.model.DrawResult
import com.cristian.timesorteio.domain.model.Sport

class FormatResultUseCase {
    fun formatForShare(result: DrawResult, sport: Sport, hasOutsideTeam: Boolean): String {
        val sb = StringBuilder()
        sb.appendLine("🏆 TIMES SORTEADOS")
        sb.appendLine()
        sb.appendLine("${sport.emoji} ${sport.displayName}")
        sb.appendLine()

        result.teams.forEachIndexed { index, team ->
            sb.appendLine("${sport.emoji} ${team.name}")
            team.players.forEachIndexed { playerIndex, player ->
                sb.appendLine("${playerIndex + 1}. ${player.name}")
            }
            sb.appendLine()
        }

        if (hasOutsideTeam && result.reserves.isNotEmpty()) {
            sb.appendLine("🔄 Time de fora")
            result.reserves.forEachIndexed { index, player ->
                sb.appendLine("${index + 1}. ${player.name}")
            }
        }

        return sb.toString()
    }
}
