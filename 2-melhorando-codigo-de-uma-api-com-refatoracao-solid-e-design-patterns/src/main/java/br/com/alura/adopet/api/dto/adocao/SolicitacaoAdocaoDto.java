package br.com.alura.adopet.api.dto.adocao;

import jakarta.validation.constraints.NotNull;

public record SolicitacaoAdocaoDto(
        
        @NotNull(message = "O id do pet não pode ser nulo")
        Long idPet,
        
        @NotNull(message = "O id do tutor não pode ser nulo")
        Long idTutor,
        
        @NotNull(message = "O motivo da adoção não pode ser nulo")
        String motivo
) {
}
