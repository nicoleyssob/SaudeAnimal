package com.saudeanimal.modelo;

import java.time.LocalDate;

public class Vacina {

    private int idVacina;
    private int idAnimal;
    private String nomeVacina;
    private LocalDate dataAplicacao;

    public Vacina() {
    }

    public Vacina(int idAnimal, String nomeVacina, LocalDate dataAplicacao) {
        this.idAnimal = idAnimal;
        this.nomeVacina = nomeVacina;
        this.dataAplicacao = dataAplicacao;
    }

    public int getIdVacina() {
        return idVacina;
    }

    public void setIdVacina(int idVacina) {
        this.idVacina = idVacina;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    @Override
    public String toString() {
        return "Vacina{id=" + idVacina + ", idAnimal=" + idAnimal + ", nomeVacina=" + nomeVacina
                + ", dataAplicacao=" + dataAplicacao + "}";
    }
}
