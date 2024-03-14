package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.pet.CadastroPetDto;
import br.com.alura.adopet.api.model.enuns.TipoPet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static br.com.alura.adopet.api.service.ShallowOrDeepCopy.verifyAndCopy;

@NoArgsConstructor
@Entity(name = "Pet")
@Table(name = "tb_pet",
        schema = "db_adopet")
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoPet tipo;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "A raça é obrigatória")
    private String raca;

    @NotNull(message = "A idade é obrigatória")
    private Integer idade;

    @NotBlank(message = "A cor é obrigatória")
    private String cor;

    @NotNull(message = "O peso é obrigatório")
    private Float peso;

    private Boolean adotado;

    @ManyToOne
    @JoinColumn(name = "abrigo_id")
    private Abrigo abrigo;

    @OneToOne(mappedBy = "pet")
    private Adocao adocao;

    public Pet(Pet copy) {
        if (copy == null) throw new IllegalArgumentException("Pet não pode ser nulo");

        this.id = (Long) verifyAndCopy(copy.id);
        this.tipo = (TipoPet) verifyAndCopy(copy.tipo);
        this.nome = (String) verifyAndCopy(copy.nome);
        this.raca = (String) verifyAndCopy(copy.raca);
        this.idade = (Integer) verifyAndCopy(copy.idade);
        this.cor = (String) verifyAndCopy(copy.cor);
        this.peso = (Float) verifyAndCopy(copy.peso);
        this.adotado = (Boolean) verifyAndCopy(copy.adotado);
        this.abrigo = (Abrigo) verifyAndCopy(copy.abrigo);
        this.adocao = (Adocao) verifyAndCopy(copy.adocao);
    }

    public Pet(Abrigo abrigo, CadastroPetDto dto) {

        if (abrigo == null) throw new IllegalArgumentException("Abrigo não pode ser nulo");
        if (dto == null) throw new IllegalArgumentException("PetDto não pode ser nulo");

        this.tipo = dto.tipo();
        this.nome = dto.nome();
        this.raca = dto.raca();
        this.idade = dto.idade();
        this.cor = dto.cor();
        this.peso = dto.peso();
        this.abrigo = abrigo;
        this.adotado = false;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Pet that = (Pet) o;

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
                ", \"tipo\": \"" + this.tipo + "\"" +
                ", \"nome\": \"" + this.nome + "\"" +
                ", \"raca\": \"" + this.raca + "\"" +
                ", \"idade\": " + this.idade +
                ", \"cor\": \"" + this.cor + "\"" +
                ", \"peso\": " + this.peso +
                ", \"adotado\": " + this.adotado +
                "}";
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPet getTipo() {
        return tipo;
    }

    public void setTipo(TipoPet tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Boolean getAdotado() {
        return adotado;
    }

    public void setAdotado(Boolean adotado) {
        this.adotado = adotado;
    }

    public Abrigo getAbrigo() {
        return abrigo;
    }

    public void setAbrigo(Abrigo abrigo) {
        this.abrigo = abrigo;
    }

    public Adocao getAdocao() {
        return adocao;
    }

    public void setAdocao(Adocao adocao) {
        this.adocao = adocao;
    }
}