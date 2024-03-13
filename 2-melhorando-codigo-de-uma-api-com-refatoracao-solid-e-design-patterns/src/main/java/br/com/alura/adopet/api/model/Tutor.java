package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.tutor.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastroTutorDto;
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
    private String nome;
    private String telefone;
    private String email;

    @OneToMany(mappedBy = "tutor")
    @Setter(AccessLevel.NONE)
    private List<Adocao> adocoes = new ArrayList<>();

    public Tutor(CadastroTutorDto cadastroTutorDto) {
        this.nome = cadastroTutorDto.nome();
        this.telefone = cadastroTutorDto.telefone();
        this.email = cadastroTutorDto.email();
    }
    
    public void atualizarDados(AtualizacaoTutorDto tutorDto) {
        this.nome = tutorDto.nome();
        this.telefone = tutorDto.telefone();
        this.email = tutorDto.email();
    }

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

        Tutor that = (Tutor) o;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((this.id == null) ? 0 : this.id.hashCode());

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
