package com.saudeanimal.modelo;

public class Genetica {

    private int idGenetica;
    private int idAnimal;
    private String doencasGeneticas;
    private String informacoesGeneticas;

    public Genetica() {
    }

    public Genetica(int idAnimal, String doencasGeneticas, String informacoesGeneticas) {
        this.idAnimal = idAnimal;
        this.doencasGeneticas = doencasGeneticas;
        this.informacoesGeneticas = informacoesGeneticas;
    }

    public int getIdGenetica() {
        return idGenetica;
    }

    public void setIdGenetica(int idGenetica) {
        this.idGenetica = idGenetica;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getDoencasGeneticas() {
        return doencasGeneticas;
    }

    public void setDoencasGeneticas(String doencasGeneticas) {
        this.doencasGeneticas = doencasGeneticas;
    }

    public String getInformacoesGeneticas() {
        return informacoesGeneticas;
    }

    public void setInformacoesGeneticas(String informacoesGeneticas) {
        this.informacoesGeneticas = informacoesGeneticas;
    }

    @Override
    public String toString() {
        return "Genetica{id=" + idGenetica + ", idAnimal=" + idAnimal + ", doencasGeneticas=" + doencasGeneticas
                + ", informacoesGeneticas=" + informacoesGeneticas + "}";
    }
}
