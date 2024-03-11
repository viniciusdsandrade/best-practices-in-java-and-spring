package br.com.alura.entity;

import java.util.Objects;

import static br.com.alura.Service.ShallowOrDeepCopy.verifyAndCopy;

public class Abrigo implements Cloneable {

    private Long id;
    private String nome;
    private String telefone;
    private String email;

    public Abrigo(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Abrigo(Long id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Abrigo() {
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

    public Abrigo(Abrigo copia) {
        if (copia == null) throw new IllegalArgumentException("Cópia não pode ser nula");

        this.id = (Long) verifyAndCopy(copia.id);
        this.nome = (String) verifyAndCopy(copia.nome);
        this.telefone = (String) verifyAndCopy(copia.telefone);
        this.email = (String) verifyAndCopy(copia.email);
    }

    @Override
    public Object clone() {
        Abrigo clone = null;

        try {
            clone = new Abrigo(this);
        } catch (Exception ignored) {
        }
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Abrigo that = (Abrigo) obj;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.nome, that.nome) &&
                Objects.equals(this.telefone, that.telefone) &&
                Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + ((this.email == null) ? 0 : this.email.hashCode());
        hash *= prime + ((this.id == null) ? 0 : this.id.hashCode());
        hash *= prime + ((this.nome == null) ? 0 : this.nome.hashCode());
        hash *= prime + ((this.telefone == null) ? 0 : this.telefone.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"id\": " + this.id + ",\n" +
                "  \"nome\": \"" + this.nome + "\",\n" +
                "  \"telefone\": \"" + this.telefone + "\",\n" +
                "  \"email\": \"" + this.email + "\"\n" +
                "}";
    }
}