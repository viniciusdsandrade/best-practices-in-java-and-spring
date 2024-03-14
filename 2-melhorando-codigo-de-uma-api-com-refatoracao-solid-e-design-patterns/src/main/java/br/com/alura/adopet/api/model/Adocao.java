package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.model.enuns.StatusAdocao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

import static br.com.alura.adopet.api.service.ShallowOrDeepCopy.verifyAndCopy;

@Setter
@NoArgsConstructor
@Entity(name = "Adocao")
@Table(name = "tb_adocao",
        schema = "db_adopet")
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tutor tutor;

    @OneToOne(fetch = FetchType.LAZY)
    private Pet pet;

    private String motivo;
    
    @Enumerated(EnumType.STRING)
    private StatusAdocao status;

    private String justificativaStatus;

    public Adocao(Tutor tutor, Pet pet, String motivo) {
        this.tutor = tutor;
        this.pet = pet;
        this.motivo = motivo;
        this.status = StatusAdocao.AGUARDANDO_AVALIACAO;
        this.data = LocalDateTime.now();
    }

    public Adocao(Adocao adocao) {
        if (adocao == null) throw new IllegalArgumentException("Adoção não pode ser nula");

        this.id = (Long) verifyAndCopy(adocao.id);
        this.data = (LocalDateTime) verifyAndCopy(adocao.data);
        this.tutor = (Tutor) verifyAndCopy(adocao.tutor);
        this.pet = (Pet) verifyAndCopy(adocao.pet);
        this.motivo = (String) verifyAndCopy(adocao.motivo);
        this.status = (StatusAdocao) verifyAndCopy(adocao.status);
        this.justificativaStatus = (String) verifyAndCopy(adocao.justificativaStatus);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Adocao that = (Adocao) o;

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
                ", \"data\": \"" + this.data + "\"" +
                ", \"tutor\": \"" + this.tutor + "\"" +
                ", \"pet\": \"" + this.pet + "\"" +
                ", \"motivo\": \"" + this.motivo + "\"" +
                ", \"status\": " + this.status +
                ", \"justificativaStatus\": \"" + this.justificativaStatus + "\"" +
                "}";
    }

    public void aprovar() {
        this.status = StatusAdocao.APROVADO;
    }

    public void reprovar(String justificativa) {
        this.status = StatusAdocao.REPROVADO;
        this.justificativaStatus = justificativa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public StatusAdocao getStatus() {
        return status;
    }

    public void setStatus(StatusAdocao status) {
        this.status = status;
    }

    public String getJustificativaStatus() {
        return justificativaStatus;
    }

    public void setJustificativaStatus(String justificativaStatus) {
        this.justificativaStatus = justificativaStatus;
    }
}
