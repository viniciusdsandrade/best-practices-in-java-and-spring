package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
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

@NoArgsConstructor
@Entity(name = "Abrigo")
@Table(name = "tb_abrigo",
        schema = "db_adopet")
public class Abrigo {

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

    @OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<Pet> pets = new ArrayList<>();



    public Abrigo(CadastroAbrigoDto dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.email = dto.email();
    }

    private void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public Abrigo(Abrigo abrigo) {
        if (abrigo == null) throw new IllegalArgumentException("Abrigo não pode ser nulo");

        this.id = (Long) verifyAndCopy(abrigo.id);
        this.nome = (String) verifyAndCopy(abrigo.nome);
        this.telefone = (String) verifyAndCopy(abrigo.telefone);
        this.email = (String) verifyAndCopy(abrigo.email);

        if (abrigo.pets != null) {
            this.pets = new ArrayList<>();
            for (Pet pet : abrigo.pets) {
                this.pets.add(new Pet(pet));
            }
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Abrigo that = (Abrigo) o;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + (this.id == null ? 0 : this.id.hashCode());

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
                ", \"pets\": " + this.pets +
                "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
