package com.saudeanimal.modelo;

import java.time.LocalDate;

public class SurtoDoenca {

    private int idSurto;
    private String nomeSurto;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public SurtoDoenca() {
    }

    public SurtoDoenca(String nomeSurto, LocalDate dataInicio, LocalDate dataFim) {
        this.nomeSurto = nomeSurto;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getIdSurto() {
        return idSurto;
    }

    public void setIdSurto(int idSurto) {
        this.idSurto = idSurto;
    }

    public String getNomeSurto() {
        return nomeSurto;
    }

    public void setNomeSurto(String nomeSurto) {
        this.nomeSurto = nomeSurto;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return "SurtoDoenca{id=" + idSurto + ", nomeSurto=" + nomeSurto + ", dataInicio=" + dataInicio
                + ", dataFim=" + dataFim + "}";
    }
}
