package com.cristian.timesorteio.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfDocument
import androidx.lifecycle.ViewModel
import com.cristian.timesorteio.domain.model.DrawResult
import com.cristian.timesorteio.domain.model.MatchConfig
import com.cristian.timesorteio.domain.model.Player
import com.cristian.timesorteio.domain.model.Sport
import com.cristian.timesorteio.domain.usecase.FormatResultUseCase
import com.cristian.timesorteio.domain.usecase.GenerateDrawUseCase
import com.cristian.timesorteio.domain.usecase.GeneratePdfUseCase
import com.cristian.timesorteio.domain.usecase.ValidateMatchConfigUseCase
import com.cristian.timesorteio.domain.usecase.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MatchViewModel(
    private val validateUseCase: ValidateMatchConfigUseCase = ValidateMatchConfigUseCase(),
    private val generateDrawUseCase: GenerateDrawUseCase = GenerateDrawUseCase(),
    private val formatResultUseCase: FormatResultUseCase = FormatResultUseCase(),
    private val generatePdfUseCase: GeneratePdfUseCase = GeneratePdfUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchUiState())
    val uiState: StateFlow<MatchUiState> = _uiState.asStateFlow()

    fun selectSport(sport: Sport) {
        _uiState.value = _uiState.value.copy(selectedSport = sport, errorMessage = null)
    }

    fun setPlayersPerTeam(count: Int) {
        val playersPerTeam = count.coerceAtLeast(1)
        val teamCount = if (playersPerTeam > 0) _uiState.value.totalPlayers / playersPerTeam else 0
        _uiState.value = _uiState.value.copy(playersPerTeam = playersPerTeam, teamCount = teamCount, errorMessage = null)
    }

    fun setTotalPlayers(count: Int) {
        val totalPlayers = count.coerceAtLeast(0)
        val playersPerTeam = _uiState.value.playersPerTeam
        val teamCount = if (playersPerTeam > 0) totalPlayers / playersPerTeam else 0
        _uiState.value = _uiState.value.copy(totalPlayers = totalPlayers, teamCount = teamCount, errorMessage = null)
    }

    fun setHasOutsideTeam(value: Boolean) {
        _uiState.value = _uiState.value.copy(hasOutsideTeam = value, errorMessage = null)
    }

    fun setOutsideTeamSameQuantity(value: Boolean) {
        _uiState.value = _uiState.value.copy(outsideTeamSameQuantity = value, errorMessage = null)
    }

    fun updatePlayerName(index: Int, name: String) {
        val newNames = _uiState.value.playerNames.toMutableList()
        if (index in newNames.indices) {
            newNames[index] = name
            _uiState.value = _uiState.value.copy(playerNames = newNames, errorMessage = null)
        }
    }

    fun setPlayerNames(names: List<String>) {
        _uiState.value = _uiState.value.copy(playerNames = names, errorMessage = null)
    }

    fun navigate(route: String) {
        _uiState.value = _uiState.value.copy(currentRoute = route, errorMessage = null)
    }

    fun canProceedFromSport(): Boolean = _uiState.value.selectedSport != null

    fun canProceedFromPlayersPerTeam(): Boolean = _uiState.value.playersPerTeam >= 1

    fun canProceedFromTotalPlayers(): Boolean = _uiState.value.totalPlayers >= 1

    fun canProceedFromOutsideTeam(): Boolean = true

    fun canProceedFromSameQuantity(): Boolean = true

    fun canProceedFromPlayerNames(): Boolean {
        val names = _uiState.value.playerNames
        if (names.size != _uiState.value.totalPlayers) return false
        if (names.any { it.isBlank() }) return false
        if (names.distinctBy { it.lowercase() }.size != names.size) return false
        return true
    }

    fun validateAndDraw(): Boolean {
        val state = _uiState.value
        val validation = validateUseCase(
            MatchConfig(
                sport = state.selectedSport ?: return false,
                totalPlayers = state.totalPlayers,
                playersPerTeam = state.playersPerTeam,
                teamCount = state.teamCount,
                hasOutsideTeam = state.hasOutsideTeam,
                outsideTeamSameQuantity = state.outsideTeamSameQuantity
            )
        )
        if (validation != ValidationResult.Valid) {
            _uiState.value = state.copy(errorMessage = (validation as ValidationResult.Invalid).message)
            return false
        }

        val players = state.playerNames.mapIndexed { index, name ->
            Player(id = "player_$index", name = name.trim())
        }

        val config = MatchConfig(
            sport = state.selectedSport!!,
            totalPlayers = state.totalPlayers,
            playersPerTeam = state.playersPerTeam,
            teamCount = state.teamCount,
            hasOutsideTeam = state.hasOutsideTeam,
            outsideTeamSameQuantity = state.outsideTeamSameQuantity
        )

        val result = generateDrawUseCase(config, players)
        _uiState.value = state.copy(drawResult = result, currentRoute = "result", errorMessage = null)
        return true
    }

    fun redraw() {
        validateAndDraw()
    }

    fun getShareIntent(context: Context): Intent {
        val state = _uiState.value
        val result = state.drawResult ?: return Intent()
        val sport = state.selectedSport ?: return Intent()

        val text = formatResultUseCase.formatForShare(result, sport, state.hasOutsideTeam)

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        return Intent.createChooser(sendIntent, "Compartilhar resultado")
    }

    fun getPdfDocument(): PdfDocument? {
        val state = _uiState.value
        val result = state.drawResult ?: return null
        val sport = state.selectedSport ?: return null

        return generatePdfUseCase.generatePdf(
            result = result,
            sport = sport,
            totalPlayers = state.totalPlayers,
            playersPerTeam = state.playersPerTeam,
            hasOutsideTeam = state.hasOutsideTeam
        )
    }

    fun getPdfFileName(): String {
        val state = _uiState.value
        val sport = state.selectedSport?.displayName ?: "sorteio"
        return "TimeSorteio_${sport}_${System.currentTimeMillis()}.pdf"
    }
}
