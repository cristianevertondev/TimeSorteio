package com.cristian.timesorteio.domain.usecase

import com.cristian.timesorteio.domain.model.MatchConfig

class ValidateMatchConfigUseCase {
    operator fun invoke(config: MatchConfig): ValidationResult {
        if (config.totalPlayers < 1) {
            return ValidationResult.Invalid("É necessário pelo menos 1 jogador.")
        }
        if (config.playersPerTeam < 1) {
            return ValidationResult.Invalid("É necessário pelo menos 1 pessoa por time.")
        }
        if (config.teamCount < 1) {
            return ValidationResult.Invalid("Não é possível formar times com a quantidade informada.")
        }
        return ValidationResult.Valid
    }
}
