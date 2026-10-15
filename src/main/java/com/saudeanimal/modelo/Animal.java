package com.saudeanimal.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Animal {

    private int idAnimal;
    private String nome;
    private String raca;
    private String genero;
    private LocalDate nascimento;
    private BigDecimal pesoKg;
    private BigDecimal alturaCm;

    public Animal() {
    }

    public Animal(String nome, String raca, String genero, LocalDate nascimento, BigDecimal pesoKg, BigDecimal alturaCm) {
        this.nome = nome;
        this.raca = raca;
        this.genero = genero;
        this.nascimento = nascimento;
        this.pesoKg = pesoKg;
        this.alturaCm = alturaCm;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public BigDecimal getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(BigDecimal pesoKg) {
        this.pesoKg = pesoKg;
    }

    public BigDecimal getAlturaCm() {
        return alturaCm;
    }

    public void setAlturaCm(BigDecimal alturaCm) {
        this.alturaCm = alturaCm;
    }

    @Override
    public String toString() {
        return "Animal{id=" + idAnimal + ", nome=" + nome + ", raca=" + raca + ", genero=" + genero
                + ", nascimento=" + nascimento + ", pesoKg=" + pesoKg + ", alturaCm=" + alturaCm + "}";
    }
}
