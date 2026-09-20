package com.cristian.timesorteio.domain.usecase

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.cristian.timesorteio.domain.model.DrawResult
import com.cristian.timesorteio.domain.model.Sport

class GeneratePdfUseCase {
    fun generatePdf(
        result: DrawResult,
        sport: Sport,
        totalPlayers: Int,
        playersPerTeam: Int,
        hasOutsideTeam: Boolean
    ): PdfDocument {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        val titlePaint = Paint().apply {
            textSize = 24f
            isFakeBoldText = true
        }
        val headerPaint = Paint().apply {
            textSize = 18f
            isFakeBoldText = true
        }
        val teamPaint = Paint().apply {
            textSize = 16f
            isFakeBoldText = true
        }
        val playerPaint = Paint().apply {
            textSize = 14f
        }
        val infoPaint = Paint().apply {
            textSize = 12f
        }

        var y = 50f
        val margin = 40f
        val lineHeight = 20f

        titlePaint.let {
            canvas.drawText("TimeSorteio", margin, y, it)
            y += 35f
        }

        infoPaint.let {
            canvas.drawText("${sport.emoji} ${sport.displayName}", margin, y, it)
            y += lineHeight
            canvas.drawText("Total de pessoas: $totalPlayers", margin, y, it)
            y += lineHeight
            canvas.drawText("Pessoas por time: $playersPerTeam", margin, y, it)
            y += 30f
        }

        headerPaint.let {
            canvas.drawText("Times Sorteados", margin, y, it)
            y += 25f
        }

        result.teams.forEach { team ->
            teamPaint.let {
                canvas.drawText("${sport.emoji} ${team.name} (${team.players.size} pessoas)", margin, y, it)
                y += lineHeight + 5
            }
            team.players.forEachIndexed { index, player ->
                playerPaint.let {
                    canvas.drawText("${index + 1}. ${player.name}", margin + 20f, y, it)
                    y += lineHeight
                }
            }
            y += 10f
        }

        if (hasOutsideTeam && result.reserves.isNotEmpty()) {
            teamPaint.let {
                canvas.drawText("🔄 Time de fora (${result.reserves.size} pessoas)", margin, y, it)
                y += lineHeight + 5
            }
            result.reserves.forEachIndexed { index, player ->
                playerPaint.let {
                    canvas.drawText("${index + 1}. ${player.name}", margin + 20f, y, it)
                    y += lineHeight
                }
            }
        }

        document.finishPage(page)
        return document
    }
}
