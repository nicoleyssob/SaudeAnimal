package com.saudeanimal.menu;

import com.saudeanimal.dao.AnimalDAO;
import com.saudeanimal.modelo.Animal;

import java.util.List;
import java.util.Optional;

public class SelecaoAnimal {

    private final Entrada entrada;
    private final AnimalDAO animais;

    public SelecaoAnimal(Entrada entrada, AnimalDAO animais) {
        this.entrada = entrada;
        this.animais = animais;
    }

    public Optional<Animal> escolher() {
        List<Animal> lista = animais.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum animal cadastrado. Cadastre um animal primeiro.");
            return Optional.empty();
        }
        listar(lista);
        int id = entrada.lerInteiro("ID do animal: ");
        Optional<Animal> animal = animais.buscarPorId(id);
        if (animal.isEmpty()) {
            System.out.println("Animal não encontrado.");
        }
        return animal;
    }

    public void listar(List<Animal> lista) {
        lista.forEach(a -> System.out.println("  " + a.getIdAnimal() + " - " + a.getNome()
                + " (" + Formato.texto(a.getRaca()) + ")"));
    }

    public String descricao(int idAnimal) {
        return animais.buscarPorId(idAnimal)
                .map(a -> a.getNome() + " (#" + idAnimal + ")")
                .orElse("#" + idAnimal);
    }
}
