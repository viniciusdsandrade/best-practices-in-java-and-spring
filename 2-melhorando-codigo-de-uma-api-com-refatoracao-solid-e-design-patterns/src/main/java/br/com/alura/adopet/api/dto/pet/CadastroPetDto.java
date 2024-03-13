package br.com.alura.adopet.api.dto.pet;

import br.com.alura.adopet.api.model.enuns.TipoPet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroPetDto(
        
        @NotNull(message = "O id do abrigo não pode ser nulo")
        TipoPet tipo,

        @NotBlank(message = "O nome do pet não pode ser nulo")
        String nome,

        @NotBlank(message = "A raça do pet não pode ser nula")
        String raca,

        @NotNull(message = "A idade do pet não pode ser nula")
        Integer idade,

        @NotBlank(message = "A cor do pet não pode ser nula")
        String cor,

        @NotNull(message = "O peso do pet não pode ser nulo")
        Float peso
) {
}
