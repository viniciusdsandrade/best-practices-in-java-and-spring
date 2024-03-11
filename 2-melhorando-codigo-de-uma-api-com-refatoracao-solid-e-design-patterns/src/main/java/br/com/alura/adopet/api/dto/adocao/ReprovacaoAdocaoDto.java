package br.com.alura.adopet.api.dto.adocao;

import jakarta.validation.constraints.NotNull;

public record ReprovacaoAdocaoDto(

        @NotNull(message = "O id da adoção não pode ser nulo")
        Long idAdocao,

        @NotNull(message = "A justificativa da reprovação não pode ser nula")
        String justificativa
) {
}
