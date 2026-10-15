package com.saudeanimal.dao;

import com.saudeanimal.modelo.HistoricoSaude;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoricoSaudeDAO implements DAO<HistoricoSaude> {

    private static final String INSERIR =
            "INSERT INTO HistoricoSaude (IdAnimal, DataConsulta, Diagnostico, Tratamento) VALUES (?, ?, ?, ?)";
    private static final String ATUALIZAR =
            "UPDATE HistoricoSaude SET DataConsulta = ?, Diagnostico = ?, Tratamento = ? WHERE IdHistorico = ?";
    private static final String EXCLUIR = "DELETE FROM HistoricoSaude WHERE IdHistorico = ?";
    private static final String BUSCAR_POR_ID = "SELECT * FROM HistoricoSaude WHERE IdHistorico = ?";
    private static final String LISTAR_TODOS = "SELECT * FROM HistoricoSaude ORDER BY DataConsulta DESC";
    private static final String LISTAR_POR_ANIMAL =
            "SELECT * FROM HistoricoSaude WHERE IdAnimal = ? ORDER BY DataConsulta DESC";

    @Override
    public void inserir(HistoricoSaude historico) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, historico.getIdAnimal());
            ps.setObject(2, historico.getDataConsulta());
            ps.setString(3, historico.getDiagnostico());
            ps.setString(4, historico.getTratamento());
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    historico.setIdHistorico(chaves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao inserir histórico de saúde", e);
        }
    }

    @Override
    public void atualizar(HistoricoSaude historico) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(ATUALIZAR)) {
            ps.setObject(1, historico.getDataConsulta());
            ps.setString(2, historico.getDiagnostico());
            ps.setString(3, historico.getTratamento());
            ps.setInt(4, historico.getIdHistorico());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao atualizar histórico de saúde", e);
        }
    }

    @Override
    public void excluir(int idHistorico) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(EXCLUIR)) {
            ps.setInt(1, idHistorico);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao excluir histórico de saúde", e);
        }
    }

    @Override
    public Optional<HistoricoSaude> buscarPorId(int idHistorico) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(BUSCAR_POR_ID)) {
            ps.setInt(1, idHistorico);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar histórico de saúde por id", e);
        }
    }

    @Override
    public List<HistoricoSaude> listarTodos() {
        List<HistoricoSaude> historicos = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(LISTAR_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                historicos.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao listar históricos de saúde", e);
        }
        return historicos;
    }

    public List<HistoricoSaude> listarPorAnimal(int idAnimal) {
        List<HistoricoSaude> historicos = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(LISTAR_POR_ANIMAL)) {
            ps.setInt(1, idAnimal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historicos.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao listar histórico de saúde do animal", e);
        }
        return historicos;
    }

    private HistoricoSaude mapear(ResultSet rs) throws SQLException {
        HistoricoSaude historico = new HistoricoSaude();
        historico.setIdHistorico(rs.getInt("IdHistorico"));
        historico.setIdAnimal(rs.getInt("IdAnimal"));
        historico.setDataConsulta(rs.getObject("DataConsulta", LocalDate.class));
        historico.setDiagnostico(rs.getString("Diagnostico"));
        historico.setTratamento(rs.getString("Tratamento"));
        return historico;
    }
}
