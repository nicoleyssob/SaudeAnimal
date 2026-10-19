package com.saudeanimal;

import com.saudeanimal.dao.Conexao;
import com.saudeanimal.menu.MenuPrincipal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {
        try (Connection conexao = Conexao.abrir()) {
            System.out.println("Conectado ao banco de dados: " + conexao.getCatalog());
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco: " + e.getMessage());
            System.out.println("Verifique se o servidor MySQL está ligado, se o banco saudeanimal foi criado");
            System.out.println("com o arquivo sql/schema.sql e se o usuário e a senha estão corretos.");
            return;
        }

        try {
            new MenuPrincipal().executar();
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Entrada encerrada.");
        }
    }
}
