package br.com.alura.adopet.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.alura.adopet.api.service.ShallowOrDeepCopy.verifyAndCopy;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Tutor")
@Table(name = "tb_tutor",
        schema = "db_adopet")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
    private String telefone;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email é inválido")
    private String email;

    @OneToMany(mappedBy = "tutor")
    @Setter(AccessLevel.NONE)
    private List<Adocao> adocoes = new ArrayList<>();

    private void addAdocao(Adocao adocao) {
        this.adocoes.add(adocao);
    }

    public Tutor(Tutor tutor) {
        if (tutor == null) throw new IllegalArgumentException("Tutor não pode ser nulo");

        this.id = (Long) verifyAndCopy(tutor.id);
        this.nome = (String) verifyAndCopy(tutor.nome);
        this.telefone = (String) verifyAndCopy(tutor.telefone);
        this.email = (String) verifyAndCopy(tutor.email);

        if (tutor.adocoes != null) {
            this.adocoes = new ArrayList<>();
            for (Adocao adocao : tutor.adocoes) {
                this.adocoes.add(new Adocao(adocao));
            }
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Tutor tutor = (Tutor) o;

        return Objects.equals(id, tutor.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((id == null) ? 0 : id.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + this.id +
                ", \"nome\": \"" + this.nome + "\"" +
                ", \"telefone\": \"" + this.telefone + "\"" +
                ", \"email\": \"" + this.email + "\"" +
                "}";
    }
}
