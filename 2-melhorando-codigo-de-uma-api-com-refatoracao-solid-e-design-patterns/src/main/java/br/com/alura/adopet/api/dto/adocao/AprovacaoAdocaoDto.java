package br.com.alura.adopet.api.dto.adocao;

import jakarta.validation.constraints.NotNull;

public record AprovacaoAdocaoDto(
        
        @NotNull(message = "O id da adoção não pode ser nulo")
        Long idAdocao
) {
}
