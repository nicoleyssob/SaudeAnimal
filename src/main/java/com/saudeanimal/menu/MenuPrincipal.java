package com.saudeanimal.menu;

import com.saudeanimal.dao.AnimalDAO;
import com.saudeanimal.dao.AnimalSurtoDAO;
import com.saudeanimal.dao.GeneticaDAO;
import com.saudeanimal.dao.HistoricoSaudeDAO;
import com.saudeanimal.dao.SurtoDoencaDAO;
import com.saudeanimal.dao.VacinaDAO;

public class MenuPrincipal {

    private final Entrada entrada = new Entrada();
    private final MenuAnimal menuAnimal;
    private final MenuHistoricoSaude menuHistorico;
    private final MenuVacina menuVacina;
    private final MenuGenetica menuGenetica;
    private final MenuSurto menuSurto;

    public MenuPrincipal() {
        AnimalDAO animalDAO = new AnimalDAO();
        SurtoDoencaDAO surtoDAO = new SurtoDoencaDAO();
        SelecaoAnimal selecaoAnimal = new SelecaoAnimal(entrada, animalDAO);
        this.menuAnimal = new MenuAnimal(entrada, animalDAO, surtoDAO);
        this.menuHistorico = new MenuHistoricoSaude(entrada, new HistoricoSaudeDAO(), selecaoAnimal);
        this.menuVacina = new MenuVacina(entrada, new VacinaDAO(), selecaoAnimal);
        this.menuGenetica = new MenuGenetica(entrada, new GeneticaDAO(), selecaoAnimal);
        this.menuSurto = new MenuSurto(entrada, surtoDAO, animalDAO, new AnimalSurtoDAO(), selecaoAnimal);
    }

    public void executar() {
        while (true) {
            System.out.println();
            System.out.println("=== SAÚDE ANIMAL ===");
            System.out.println("1 - Animais");
            System.out.println("2 - Histórico de saúde");
            System.out.println("3 - Vacinas");
            System.out.println("4 - Genética");
            System.out.println("5 - Surtos de doenças");
            System.out.println("0 - Sair");
            int opcao = entrada.lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> menuAnimal.executar();
                case 2 -> menuHistorico.executar();
                case 3 -> menuVacina.executar();
                case 4 -> menuGenetica.executar();
                case 5 -> menuSurto.executar();
                case 0 -> {
                    System.out.println("Até logo!");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
