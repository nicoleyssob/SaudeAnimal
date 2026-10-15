package com.saudeanimal.dao;

import com.saudeanimal.modelo.Genetica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GeneticaDAO implements DAO<Genetica> {

    private static final String INSERIR =
            "INSERT INTO Genetica (IdAnimal, DoencasGeneticas, InformacoesGeneticas) VALUES (?, ?, ?)";
    private static final String ATUALIZAR =
            "UPDATE Genetica SET DoencasGeneticas = ?, InformacoesGeneticas = ? WHERE IdGenetica = ?";
    private static final String EXCLUIR = "DELETE FROM Genetica WHERE IdGenetica = ?";
    private static final String BUSCAR_POR_ID = "SELECT * FROM Genetica WHERE IdGenetica = ?";
    private static final String LISTAR_TODOS = "SELECT * FROM Genetica ORDER BY IdGenetica";
    private static final String LISTAR_POR_ANIMAL = "SELECT * FROM Genetica WHERE IdAnimal = ? ORDER BY IdGenetica";
    private static final String BUSCAR_POR_DOENCA =
            "SELECT * FROM Genetica WHERE DoencasGeneticas LIKE ? ORDER BY IdGenetica";

    @Override
    public void inserir(Genetica genetica) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, genetica.getIdAnimal());
            ps.setString(2, genetica.getDoencasGeneticas());
            ps.setString(3, genetica.getInformacoesGeneticas());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    genetica.setIdGenetica(chaves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao inserir informações genéticas", e);
        }
    }

    @Override
    public void atualizar(Genetica genetica) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(ATUALIZAR)) {
            ps.setString(1, genetica.getDoencasGeneticas());
            ps.setString(2, genetica.getInformacoesGeneticas());
            ps.setInt(3, genetica.getIdGenetica());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao atualizar informações genéticas", e);
        }
    }

    @Override
    public void excluir(int idGenetica) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(EXCLUIR)) {
            ps.setInt(1, idGenetica);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao excluir informações genéticas", e);
        }
    }

    @Override
    public Optional<Genetica> buscarPorId(int idGenetica) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(BUSCAR_POR_ID)) {
            ps.setInt(1, idGenetica);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar informações genéticas por id", e);
        }
    }

    @Override
    public List<Genetica> listarTodos() {
        List<Genetica> lista = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(LISTAR_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao listar informações genéticas", e);
        }
        return lista;
    }

    public List<Genetica> listarPorAnimal(int idAnimal) {
        List<Genetica> lista = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(LISTAR_POR_ANIMAL)) {
            ps.setInt(1, idAnimal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao listar informações genéticas do animal", e);
        }
        return lista;
    }

    public List<Genetica> buscarPorDoenca(String parteDoNome) {
        List<Genetica> lista = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(BUSCAR_POR_DOENCA)) {
            ps.setString(1, "%" + parteDoNome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar informações genéticas por doença", e);
        }
        return lista;
    }

    private Genetica mapear(ResultSet rs) throws SQLException {
        Genetica genetica = new Genetica();
        genetica.setIdGenetica(rs.getInt("IdGenetica"));
        genetica.setIdAnimal(rs.getInt("IdAnimal"));
        genetica.setDoencasGeneticas(rs.getString("DoencasGeneticas"));
        genetica.setInformacoesGeneticas(rs.getString("InformacoesGeneticas"));
        return genetica;
    }
}
