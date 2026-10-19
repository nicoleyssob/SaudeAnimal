package com.saudeanimal;

import com.saudeanimal.dao.AnimalDAO;
import com.saudeanimal.dao.AnimalSurtoDAO;
import com.saudeanimal.dao.DaoException;
import com.saudeanimal.dao.GeneticaDAO;
import com.saudeanimal.dao.HistoricoSaudeDAO;
import com.saudeanimal.dao.SurtoDoencaDAO;
import com.saudeanimal.dao.VacinaDAO;
import com.saudeanimal.modelo.Animal;
import com.saudeanimal.modelo.Genetica;
import com.saudeanimal.modelo.HistoricoSaude;
import com.saudeanimal.modelo.SurtoDoenca;
import com.saudeanimal.modelo.Vacina;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Demonstracao {

    public static void main(String[] args) {
        AnimalDAO animais = new AnimalDAO();
        HistoricoSaudeDAO historicos = new HistoricoSaudeDAO();
        VacinaDAO vacinas = new VacinaDAO();
        GeneticaDAO geneticas = new GeneticaDAO();
        SurtoDoencaDAO surtos = new SurtoDoencaDAO();
        AnimalSurtoDAO vinculos = new AnimalSurtoDAO();

        Animal animal = new Animal("Simba", "Leão", "Macho", LocalDate.of(2020, 5, 14),
                new BigDecimal("180.50"), new BigDecimal("110.00"));
        SurtoDoenca surto = new SurtoDoenca("Surto de cinomose", LocalDate.of(2024, 2, 1), null);

        try {
            animais.inserir(animal);
            historicos.inserir(new HistoricoSaude(animal.getIdAnimal(), LocalDate.of(2024, 1, 10),
                    "Infecção de pele", "Pomada antibiótica por 10 dias"));
            vacinas.inserir(new Vacina(animal.getIdAnimal(), "Antirrábica", LocalDate.of(2024, 1, 20)));
            geneticas.inserir(new Genetica(animal.getIdAnimal(), "Cardiomiopatia",
                    "Predisposição observada na linhagem materna"));
            surtos.inserir(surto);
            vinculos.vincular(animal.getIdAnimal(), surto.getIdSurto());

            System.out.println("Animal cadastrado: " + animais.buscarPorId(animal.getIdAnimal()).orElseThrow());
            historicos.listarPorAnimal(animal.getIdAnimal()).forEach(System.out::println);
            vacinas.listarPorAnimal(animal.getIdAnimal()).forEach(System.out::println);
            geneticas.listarPorAnimal(animal.getIdAnimal()).forEach(System.out::println);
            surtos.listarTodos().forEach(System.out::println);
            System.out.println("Animais do surto: " + animais.listarPorSurto(surto.getIdSurto()));
            System.out.println("Surtos do animal: " + surtos.listarPorAnimal(animal.getIdAnimal()));

            animal.setPesoKg(new BigDecimal("185.00"));
            animais.atualizar(animal);
            System.out.println("Peso atualizado: " + animais.buscarPorId(animal.getIdAnimal()).orElseThrow().getPesoKg());

            System.out.println("Busca por 'Sim': " + animais.buscarPorNome("Sim"));
        } catch (DaoException e) {
            System.err.println(e.getMessage() + ": " + e.getCause().getMessage());
        } finally {
            try {
                if (animal.getIdAnimal() > 0) {
                    animais.excluir(animal.getIdAnimal());
                }
                if (surto.getIdSurto() > 0) {
                    surtos.excluir(surto.getIdSurto());
                }
            } catch (DaoException e) {
                System.err.println(e.getMessage() + ": " + e.getCause().getMessage());
            }
        }
    }
}
