package com.saudeanimal.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    void inserir(T entidade);

    void atualizar(T entidade);

    void excluir(int id);

    Optional<T> buscarPorId(int id);

    List<T> listarTodos();
}
