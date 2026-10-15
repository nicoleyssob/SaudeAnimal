package com.saudeanimal.dao;

import com.saudeanimal.modelo.Vacina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VacinaDAO implements DAO<Vacina> {

    private static final String INSERIR =
            "INSERT INTO Vacinas (IdAnimal, NomeVacina, DataAplicacao) VALUES (?, ?, ?)";
    private static final String ATUALIZAR =
            "UPDATE Vacinas SET NomeVacina = ?, DataAplicacao = ? WHERE IdVacinas = ?";
    private static final String EXCLUIR = "DELETE FROM Vacinas WHERE IdVacinas = ?";
    private static final String BUSCAR_POR_ID = "SELECT * FROM Vacinas WHERE IdVacinas = ?";
    private static final String LISTAR_TODOS = "SELECT * FROM Vacinas ORDER BY DataAplicacao DESC";
    private static final String LISTAR_POR_ANIMAL =
            "SELECT * FROM Vacinas WHERE IdAnimal = ? ORDER BY DataAplicacao DESC";

    @Override
    public void inserir(Vacina vacina) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, vacina.getIdAnimal());
            ps.setString(2, vacina.getNomeVacina());
            ps.setObject(3, vacina.getDataAplicacao());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    vacina.setIdVacina(chaves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao inserir vacina", e);
        }
    }

    @Override
    public void atualizar(Vacina vacina) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(ATUALIZAR)) {
            ps.setString(1, vacina.getNomeVacina());
            ps.setObject(2, vacina.getDataAplicacao());
            ps.setInt(3, vacina.getIdVacina());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao atualizar vacina", e);
        }
    }

    @Override
    public void excluir(int idVacina) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(EXCLUIR)) {
            ps.setInt(1, idVacina);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao excluir vacina", e);
        }
    }

    @Override
    public Optional<Vacina> buscarPorId(int idVacina) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(BUSCAR_POR_ID)) {
            ps.setInt(1, idVacina);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar vacina por id", e);
        }
    }

    @Override
    public List<Vacina> listarTodos() {
        List<Vacina> vacinas = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(LISTAR_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                vacinas.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao listar vacinas", e);
        }
        return vacinas;
    }

    public List<Vacina> listarPorAnimal(int idAnimal) {
        List<Vacina> vacinas = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(LISTAR_POR_ANIMAL)) {
            ps.setInt(1, idAnimal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vacinas.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao listar vacinas do animal", e);
        }
        return vacinas;
    }

    private Vacina mapear(ResultSet rs) throws SQLException {
        Vacina vacina = new Vacina();
        vacina.setIdVacina(rs.getInt("IdVacinas"));
        vacina.setIdAnimal(rs.getInt("IdAnimal"));
        vacina.setNomeVacina(rs.getString("NomeVacina"));
        vacina.setDataAplicacao(rs.getObject("DataAplicacao", LocalDate.class));
        return vacina;
    }
}
