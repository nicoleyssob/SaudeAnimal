package com.saudeanimal.menu;

import com.saudeanimal.dao.HistoricoSaudeDAO;
import com.saudeanimal.modelo.Animal;
import com.saudeanimal.modelo.HistoricoSaude;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MenuHistoricoSaude extends MenuCrud<HistoricoSaude> {

    private final HistoricoSaudeDAO historicoDAO;
    private final SelecaoAnimal selecaoAnimal;

    public MenuHistoricoSaude(Entrada entrada, HistoricoSaudeDAO historicoDAO, SelecaoAnimal selecaoAnimal) {
        super(entrada, historicoDAO);
        this.historicoDAO = historicoDAO;
        this.selecaoAnimal = selecaoAnimal;
    }

    @Override
    protected String titulo() {
        return "HISTÓRICO DE SAÚDE";
    }

    @Override
    protected int id(HistoricoSaude historico) {
        return historico.getIdHistorico();
    }

    @Override
    protected void exibir(HistoricoSaude historico) {
        System.out.println("[" + historico.getIdHistorico() + "] Consulta em " + Formato.data(historico.getDataConsulta())
                + " - " + selecaoAnimal.descricao(historico.getIdAnimal()));
        System.out.println("    Diagnóstico: " + Formato.texto(historico.getDiagnostico()));
        System.out.println("    Tratamento: " + Formato.texto(historico.getTratamento()));
    }

    @Override
    protected HistoricoSaude novo() {
        Optional<Animal> animal = selecaoAnimal.escolher();
        if (animal.isEmpty()) {
            return null;
        }
        LocalDate data = entrada.lerData("Data da consulta", true);
        String diagnostico = entrada.lerTexto("Diagnóstico", 300, false);
        String tratamento = entrada.lerTexto("Tratamento", 300, false);
        return new HistoricoSaude(animal.get().getIdAnimal(), data, diagnostico, tratamento);
    }

    @Override
    protected void editar(HistoricoSaude historico) {
        historico.setDataConsulta(entrada.lerDataOuManter("Data da consulta", historico.getDataConsulta(), true));
        historico.setDiagnostico(entrada.lerTextoOuManter("Diagnóstico", historico.getDiagnostico(), 300, false));
        historico.setTratamento(entrada.lerTextoOuManter("Tratamento", historico.getTratamento(), 300, false));
    }

    @Override
    protected List<String> opcoesExtras() {
        return List.of("Listar por animal");
    }

    @Override
    protected void executarExtra(int indice) {
        selecaoAnimal.escolher().ifPresent(animal -> mostrar(historicoDAO.listarPorAnimal(animal.getIdAnimal())));
    }
}
