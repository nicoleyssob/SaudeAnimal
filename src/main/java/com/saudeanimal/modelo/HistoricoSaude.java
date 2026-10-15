package com.saudeanimal.modelo;

import java.time.LocalDate;

public class HistoricoSaude {

    private int idHistorico;
    private int idAnimal;
    private LocalDate dataConsulta;
    private String diagnostico;
    private String tratamento;

    public HistoricoSaude() {
    }

    public HistoricoSaude(int idAnimal, LocalDate dataConsulta, String diagnostico, String tratamento) {
        this.idAnimal = idAnimal;
        this.dataConsulta = dataConsulta;
        this.diagnostico = diagnostico;
        this.tratamento = tratamento;
    }

    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    @Override
    public String toString() {
        return "HistoricoSaude{id=" + idHistorico + ", idAnimal=" + idAnimal + ", dataConsulta=" + dataConsulta
                + ", diagnostico=" + diagnostico + ", tratamento=" + tratamento + "}";
    }
}
