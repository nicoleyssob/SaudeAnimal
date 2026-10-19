package com.saudeanimal.menu;

import com.saudeanimal.dao.AnimalDAO;
import com.saudeanimal.dao.AnimalSurtoDAO;
import com.saudeanimal.dao.SurtoDoencaDAO;
import com.saudeanimal.modelo.Animal;
import com.saudeanimal.modelo.SurtoDoenca;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MenuSurto extends MenuCrud<SurtoDoenca> {

    private final AnimalDAO animalDAO;
    private final AnimalSurtoDAO animalSurtoDAO;
    private final SelecaoAnimal selecaoAnimal;

    public MenuSurto(Entrada entrada, SurtoDoencaDAO surtoDAO, AnimalDAO animalDAO,
                     AnimalSurtoDAO animalSurtoDAO, SelecaoAnimal selecaoAnimal) {
        super(entrada, surtoDAO);
        this.animalDAO = animalDAO;
        this.animalSurtoDAO = animalSurtoDAO;
        this.selecaoAnimal = selecaoAnimal;
    }

    @Override
    protected String titulo() {
        return "SURTOS DE DOENÇAS";
    }

    @Override
    protected int id(SurtoDoenca surto) {
        return surto.getIdSurto();
    }

    @Override
    protected void exibir(SurtoDoenca surto) {
        System.out.println("[" + surto.getIdSurto() + "] " + surto.getNomeSurto());
        System.out.println("    Período: " + Formato.periodo(surto.getDataInicio(), surto.getDataFim()));
        System.out.println("    Animais afetados: " + animalDAO.listarPorSurto(surto.getIdSurto()).size());
    }

    @Override
    protected SurtoDoenca novo() {
        String nome = entrada.lerTexto("Nome do surto", 100, true);
        LocalDate inicio = entrada.lerData("Data de início", true);
        LocalDate fim = lerFim(inicio, null, false);
        return new SurtoDoenca(nome, inicio, fim);
    }

    @Override
    protected void editar(SurtoDoenca surto) {
        surto.setNomeSurto(entrada.lerTextoOuManter("Nome do surto", surto.getNomeSurto(), 100, true));
        surto.setDataInicio(entrada.lerDataOuManter("Data de início", surto.getDataInicio(), true));
        surto.setDataFim(lerFim(surto.getDataInicio(), surto.getDataFim(), true));
    }

    @Override
    protected String avisoExclusao() {
        return "Atenção: os vínculos deste surto com animais também serão removidos (os animais não são excluídos).";
    }

    @Override
    protected List<String> opcoesExtras() {
        return List.of("Vincular animal ao surto", "Desvincular animal do surto", "Listar animais do surto");
    }

    @Override
    protected void executarExtra(int indice) {
        switch (indice) {
            case 0 -> vincular();
            case 1 -> desvincular();
            default -> listarAnimais();
        }
    }

    private void vincular() {
        Optional<SurtoDoenca> surto = escolherExistente();
        if (surto.isEmpty()) {
            return;
        }
        Optional<Animal> animal = selecaoAnimal.escolher();
        if (animal.isEmpty()) {
            return;
        }
        int idAnimal = animal.get().getIdAnimal();
        int idSurto = surto.get().getIdSurto();
        if (animalSurtoDAO.existe(idAnimal, idSurto)) {
            System.out.println("Este animal já está vinculado a este surto.");
            return;
        }
        animalSurtoDAO.vincular(idAnimal, idSurto);
        System.out.println(animal.get().getNome() + " foi vinculado ao surto " + surto.get().getNomeSurto() + ".");
    }

    private void desvincular() {
        Optional<SurtoDoenca> surto = escolherExistente();
        if (surto.isEmpty()) {
            return;
        }
        int idSurto = surto.get().getIdSurto();
        List<Animal> vinculados = animalDAO.listarPorSurto(idSurto);
        if (vinculados.isEmpty()) {
            System.out.println("Nenhum animal está vinculado a este surto.");
            return;
        }
        System.out.println("Animais vinculados:");
        selecaoAnimal.listar(vinculados);
        int idAnimal = entrada.lerInteiro("ID do animal: ");
        if (animalSurtoDAO.desvincular(idAnimal, idSurto)) {
            System.out.println("Vínculo removido.");
        } else {
            System.out.println("Esse animal não está vinculado a este surto.");
        }
    }

    private void listarAnimais() {
        Optional<SurtoDoenca> surto = escolherExistente();
        if (surto.isEmpty()) {
            return;
        }
        List<Animal> vinculados = animalDAO.listarPorSurto(surto.get().getIdSurto());
        if (vinculados.isEmpty()) {
            System.out.println("Nenhum animal está vinculado a este surto.");
            return;
        }
        System.out.println("Animais do surto " + surto.get().getNomeSurto() + ":");
        selecaoAnimal.listar(vinculados);
    }

    private LocalDate lerFim(LocalDate inicio, LocalDate atual, boolean manter) {
        while (true) {
            LocalDate fim = manter
                    ? entrada.lerDataOuManter("Data de fim", atual, false)
                    : entrada.lerData("Data de fim", false);
            if (fim == null || !fim.isBefore(inicio)) {
                return fim;
            }
            System.out.println("A data de fim não pode ser anterior à data de início.");
        }
    }
}
