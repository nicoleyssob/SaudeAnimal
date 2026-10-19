package com.saudeanimal.menu;

import com.saudeanimal.dao.VacinaDAO;
import com.saudeanimal.modelo.Animal;
import com.saudeanimal.modelo.Vacina;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MenuVacina extends MenuCrud<Vacina> {

    private final VacinaDAO vacinaDAO;
    private final SelecaoAnimal selecaoAnimal;

    public MenuVacina(Entrada entrada, VacinaDAO vacinaDAO, SelecaoAnimal selecaoAnimal) {
        super(entrada, vacinaDAO);
        this.vacinaDAO = vacinaDAO;
        this.selecaoAnimal = selecaoAnimal;
    }

    @Override
    protected String titulo() {
        return "VACINAS";
    }

    @Override
    protected int id(Vacina vacina) {
        return vacina.getIdVacina();
    }

    @Override
    protected void exibir(Vacina vacina) {
        System.out.println("[" + vacina.getIdVacina() + "] " + vacina.getNomeVacina()
                + " - " + selecaoAnimal.descricao(vacina.getIdAnimal()));
        System.out.println("    Aplicada em: " + Formato.data(vacina.getDataAplicacao()));
    }

    @Override
    protected Vacina novo() {
        Optional<Animal> animal = selecaoAnimal.escolher();
        if (animal.isEmpty()) {
            return null;
        }
        String nome = entrada.lerTexto("Nome da vacina", 50, true);
        LocalDate data = entrada.lerData("Data de aplicação", true);
        return new Vacina(animal.get().getIdAnimal(), nome, data);
    }

    @Override
    protected void editar(Vacina vacina) {
        vacina.setNomeVacina(entrada.lerTextoOuManter("Nome da vacina", vacina.getNomeVacina(), 50, true));
        vacina.setDataAplicacao(entrada.lerDataOuManter("Data de aplicação", vacina.getDataAplicacao(), true));
    }

    @Override
    protected List<String> opcoesExtras() {
        return List.of("Listar por animal");
    }

    @Override
    protected void executarExtra(int indice) {
        selecaoAnimal.escolher().ifPresent(animal -> mostrar(vacinaDAO.listarPorAnimal(animal.getIdAnimal())));
    }
}
