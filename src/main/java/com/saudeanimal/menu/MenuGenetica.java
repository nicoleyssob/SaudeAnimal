package com.saudeanimal.menu;

import com.saudeanimal.dao.GeneticaDAO;
import com.saudeanimal.modelo.Animal;
import com.saudeanimal.modelo.Genetica;

import java.util.List;
import java.util.Optional;

public class MenuGenetica extends MenuCrud<Genetica> {

    private final GeneticaDAO geneticaDAO;
    private final SelecaoAnimal selecaoAnimal;

    public MenuGenetica(Entrada entrada, GeneticaDAO geneticaDAO, SelecaoAnimal selecaoAnimal) {
        super(entrada, geneticaDAO);
        this.geneticaDAO = geneticaDAO;
        this.selecaoAnimal = selecaoAnimal;
    }

    @Override
    protected String titulo() {
        return "GENÉTICA";
    }

    @Override
    protected int id(Genetica genetica) {
        return genetica.getIdGenetica();
    }

    @Override
    protected void exibir(Genetica genetica) {
        System.out.println("[" + genetica.getIdGenetica() + "] " + genetica.getDoencasGeneticas()
                + " - " + selecaoAnimal.descricao(genetica.getIdAnimal()));
        System.out.println("    Informações: " + Formato.texto(genetica.getInformacoesGeneticas()));
    }

    @Override
    protected Genetica novo() {
        Optional<Animal> animal = selecaoAnimal.escolher();
        if (animal.isEmpty()) {
            return null;
        }
        String doencas = entrada.lerTexto("Doenças genéticas", 50, true);
        String informacoes = entrada.lerTexto("Informações genéticas", 500, false);
        return new Genetica(animal.get().getIdAnimal(), doencas, informacoes);
    }

    @Override
    protected void editar(Genetica genetica) {
        genetica.setDoencasGeneticas(entrada.lerTextoOuManter("Doenças genéticas", genetica.getDoencasGeneticas(), 50, true));
        genetica.setInformacoesGeneticas(entrada.lerTextoOuManter("Informações genéticas", genetica.getInformacoesGeneticas(), 500, false));
    }

    @Override
    protected List<String> opcoesExtras() {
        return List.of("Listar por animal", "Buscar por doença");
    }

    @Override
    protected void executarExtra(int indice) {
        if (indice == 0) {
            selecaoAnimal.escolher().ifPresent(animal -> mostrar(geneticaDAO.listarPorAnimal(animal.getIdAnimal())));
        } else {
            String parte = entrada.lerTexto("Parte do nome da doença", 50, true);
            mostrar(geneticaDAO.buscarPorDoenca(parte));
        }
    }
}
