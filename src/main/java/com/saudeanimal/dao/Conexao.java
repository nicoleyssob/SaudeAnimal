package com.saudeanimal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexao {

    private static final String URL = variavel("DB_URL", "jdbc:mysql://localhost:3306/saudeanimal");
    private static final String USUARIO = variavel("DB_USER", "root");
    private static final String SENHA = variavel("DB_PASSWORD", "");

    private Conexao() {
    }

    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    private static String variavel(String nome, String padrao) {
        String valor = System.getenv(nome);
        return valor == null || valor.isBlank() ? padrao : valor;
    }
}
