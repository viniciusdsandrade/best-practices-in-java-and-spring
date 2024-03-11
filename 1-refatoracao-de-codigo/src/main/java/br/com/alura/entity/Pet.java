package br.com.alura.entity;


import java.util.Objects;

public class Pet {

    private Long id;
    private String nome;
    private String raca;
    private String cor;
    private Float peso;
    private int idade;
    private String tipo;

    public Pet(Long id,
                String tipo,
               String nome,
               String raca,
               String cor,
               Float peso,
               int idade) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.raca = raca;
        this.cor = cor;
        this.peso = peso;
        this.idade = idade;
    }

    public Pet() {
    }

    public Pet(String tipo,
               String nome,
               String raca,
               int idade,
               String cor,
               Float peso) {
        this.tipo = tipo;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.cor = cor;
        this.peso = peso;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Pet that = (Pet) o;

        return Objects.equals(this.nome, that.nome) &&
                Objects.equals(this.raca, that.raca) &&
                Objects.equals(this.cor, that.cor) &&
                Objects.equals(this.peso, that.peso) &&
                Objects.equals(this.idade, that.idade) &&
                Objects.equals(this.tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + (this.nome == null ? 0 : this.nome.hashCode());
        hash *= prime + (this.raca == null ? 0 : this.raca.hashCode());
        hash *= prime + (this.cor == null ? 0 : this.cor.hashCode());
        hash *= prime + (this.peso == null ? 0 : this.peso.hashCode());
        hash *= prime + (this.idade == 0 ? 0 : this.idade);
        hash *= prime + (this.tipo == null ? 0 : this.tipo.hashCode());

        if (hash < 0) hash *= -1;

        return hash;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"nome\": \"" + this.nome + "\",\n" +
                "  \"raca\": \"" + this.raca + "\",\n" +
                "  \"cor\": \"" + this.cor + "\",\n" +
                "  \"peso\": " + this.peso + ",\n" +
                "  \"idade\": " + this.idade + ",\n" +
                "  \"tipo\": \"" + this.tipo + "\"\n" +
                "}";
    }
}
