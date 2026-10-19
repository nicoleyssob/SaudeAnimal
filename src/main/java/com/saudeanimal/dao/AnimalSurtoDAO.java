package com.saudeanimal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalSurtoDAO {

    private static final String VINCULAR = "INSERT INTO AnimalSurto (IdAnimal, IdSurto) VALUES (?, ?)";
    private static final String DESVINCULAR = "DELETE FROM AnimalSurto WHERE IdAnimal = ? AND IdSurto = ?";
    private static final String EXISTE = "SELECT 1 FROM AnimalSurto WHERE IdAnimal = ? AND IdSurto = ?";

    public void vincular(int idAnimal, int idSurto) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(VINCULAR)) {
            ps.setInt(1, idAnimal);
            ps.setInt(2, idSurto);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao vincular animal ao surto", e);
        }
    }

    public boolean desvincular(int idAnimal, int idSurto) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(DESVINCULAR)) {
            ps.setInt(1, idAnimal);
            ps.setInt(2, idSurto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Erro ao desvincular animal do surto", e);
        }
    }

    public boolean existe(int idAnimal, int idSurto) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(EXISTE)) {
            ps.setInt(1, idAnimal);
            ps.setInt(2, idSurto);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao verificar vínculo entre animal e surto", e);
        }
    }
}
