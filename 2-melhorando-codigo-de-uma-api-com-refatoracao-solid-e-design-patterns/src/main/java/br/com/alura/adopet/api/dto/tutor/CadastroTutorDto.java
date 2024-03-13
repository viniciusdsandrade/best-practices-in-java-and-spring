package br.com.alura.adopet.api.dto.tutor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CadastroTutorDto(
        
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String telefone,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email é inválido")
        String email
) {
}
