package com.saudeanimal.menu;

import com.saudeanimal.dao.DAO;
import com.saudeanimal.dao.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class MenuCrud<T> {

    protected final Entrada entrada;
    protected final DAO<T> dao;

    protected MenuCrud(Entrada entrada, DAO<T> dao) {
        this.entrada = entrada;
        this.dao = dao;
    }

    protected abstract String titulo();

    protected abstract int id(T item);

    protected abstract void exibir(T item);

    protected abstract T novo();

    protected abstract void editar(T item);

    protected String avisoExclusao() {
        return null;
    }

    protected List<String> opcoesExtras() {
        return List.of();
    }

    protected void executarExtra(int indice) {
    }

    public void executar() {
        while (true) {
            imprimirMenu();
            int opcao = entrada.lerInteiro("Escolha uma opção: ");
            if (opcao == 0) {
                return;
            }
            try {
                tratar(opcao);
            } catch (DaoException e) {
                System.out.println("Erro: " + e.getMessage() + " (" + e.getCause().getMessage() + ")");
            }
        }
    }

    protected void mostrar(List<T> itens) {
        if (itens.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }
        for (T item : itens) {
            exibir(item);
            System.out.println();
        }
        System.out.println(itens.size() + " registro(s).");
    }

    private void imprimirMenu() {
        System.out.println();
        System.out.println("--- " + titulo() + " ---");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar todos");
        System.out.println("3 - Buscar por ID");
        System.out.println("4 - Atualizar");
        System.out.println("5 - Excluir");
        List<String> extras = opcoesExtras();
        for (int i = 0; i < extras.size(); i++) {
            System.out.println((6 + i) + " - " + extras.get(i));
        }
        System.out.println("0 - Voltar");
    }

    private void tratar(int opcao) {
        switch (opcao) {
            case 1 -> cadastrar();
            case 2 -> mostrar(dao.listarTodos());
            case 3 -> buscarPorId();
            case 4 -> atualizar();
            case 5 -> excluir();
            default -> {
                int indice = opcao - 6;
                if (indice >= 0 && indice < opcoesExtras().size()) {
                    executarExtra(indice);
                } else {
                    System.out.println("Opção inválida.");
                }
            }
        }
    }

    private void cadastrar() {
        T item = novo();
        if (item == null) {
            return;
        }
        dao.inserir(item);
        System.out.println("Registro cadastrado (ID " + id(item) + ").");
    }

    private void buscarPorId() {
        int id = entrada.lerInteiro("ID: ");
        Optional<T> item = dao.buscarPorId(id);
        if (item.isPresent()) {
            exibir(item.get());
        } else {
            System.out.println("Registro não encontrado.");
        }
    }

    private void atualizar() {
        Optional<T> item = escolherExistente();
        if (item.isEmpty()) {
            return;
        }
        System.out.println("Enter mantém o valor atual. Digite - para limpar um campo opcional.");
        editar(item.get());
        dao.atualizar(item.get());
        System.out.println("Registro atualizado.");
    }

    private void excluir() {
        Optional<T> item = escolherExistente();
        if (item.isEmpty()) {
            return;
        }
        String aviso = avisoExclusao();
        if (aviso != null) {
            System.out.println(aviso);
        }
        if (!entrada.confirmar("Confirma a exclusão?")) {
            System.out.println("Exclusão cancelada.");
            return;
        }
        dao.excluir(id(item.get()));
        System.out.println("Registro excluído.");
    }

    protected Optional<T> escolherExistente() {
        List<T> itens = dao.listarTodos();
        if (itens.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return Optional.empty();
        }
        for (T item : itens) {
            exibir(item);
            System.out.println();
        }
        int id = entrada.lerInteiro("ID do registro: ");
        Optional<T> escolhido = dao.buscarPorId(id);
        if (escolhido.isEmpty()) {
            System.out.println("Registro não encontrado.");
        }
        return escolhido;
    }
}
