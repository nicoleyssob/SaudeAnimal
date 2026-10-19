package com.saudeanimal.dao;

import com.saudeanimal.modelo.SurtoDoenca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SurtoDoencaDAO implements DAO<SurtoDoenca> {

    private static final String INSERIR =
            "INSERT INTO SurtosDoencas (NomeSurto, DataInicio, DataFim) VALUES (?, ?, ?)";
    private static final String ATUALIZAR =
            "UPDATE SurtosDoencas SET NomeSurto = ?, DataInicio = ?, DataFim = ? WHERE IdSurto = ?";
    private static final String EXCLUIR = "DELETE FROM SurtosDoencas WHERE IdSurto = ?";
    private static final String BUSCAR_POR_ID = "SELECT * FROM SurtosDoencas WHERE IdSurto = ?";
    private static final String LISTAR_TODOS = "SELECT * FROM SurtosDoencas ORDER BY DataInicio DESC";
    private static final String LISTAR_POR_ANIMAL =
            "SELECT d.* FROM SurtosDoencas d JOIN AnimalSurto s ON s.IdSurto = d.IdSurto WHERE s.IdAnimal = ? ORDER BY d.DataInicio DESC";

    @Override
    public void inserir(SurtoDoenca surto) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, surto.getNomeSurto());
            ps.setObject(2, surto.getDataInicio());
            ps.setObject(3, surto.getDataFim());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    surto.setIdSurto(chaves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao inserir surto de doença", e);
        }
    }

    @Override
    public void atualizar(SurtoDoenca surto) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(ATUALIZAR)) {
            ps.setString(1, surto.getNomeSurto());
            ps.setObject(2, surto.getDataInicio());
            ps.setObject(3, surto.getDataFim());
            ps.setInt(4, surto.getIdSurto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao atualizar surto de doença", e);
        }
    }

    @Override
    public void excluir(int idSurto) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(EXCLUIR)) {
            ps.setInt(1, idSurto);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao excluir surto de doença", e);
        }
    }

    @Override
    public Optional<SurtoDoenca> buscarPorId(int idSurto) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(BUSCAR_POR_ID)) {
            ps.setInt(1, idSurto);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar surto de doença por id", e);
        }
    }

    @Override
    public List<SurtoDoenca> listarTodos() {
        List<SurtoDoenca> surtos = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(LISTAR_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                surtos.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao listar surtos de doenças", e);
        }
        return surtos;
    }

    public List<SurtoDoenca> listarPorAnimal(int idAnimal) {
        List<SurtoDoenca> surtos = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(LISTAR_POR_ANIMAL)) {
            ps.setInt(1, idAnimal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    surtos.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao listar surtos do animal", e);
        }
        return surtos;
    }

    private SurtoDoenca mapear(ResultSet rs) throws SQLException {
        SurtoDoenca surto = new SurtoDoenca();
        surto.setIdSurto(rs.getInt("IdSurto"));
        surto.setNomeSurto(rs.getString("NomeSurto"));
        surto.setDataInicio(rs.getObject("DataInicio", LocalDate.class));
        surto.setDataFim(rs.getObject("DataFim", LocalDate.class));
        return surto;
    }
}
