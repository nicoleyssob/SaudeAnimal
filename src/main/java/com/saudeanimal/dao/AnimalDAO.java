package com.saudeanimal.dao;

import com.saudeanimal.modelo.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalDAO implements DAO<Animal> {

    private static final String INSERIR =
            "INSERT INTO Animal (Nome, Raca, Genero, Nascimento, PesoKg, AlturaCm) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ATUALIZAR =
            "UPDATE Animal SET Nome = ?, Raca = ?, Genero = ?, Nascimento = ?, PesoKg = ?, AlturaCm = ? WHERE IdAnimal = ?";
    private static final String EXCLUIR = "DELETE FROM Animal WHERE IdAnimal = ?";
    private static final String BUSCAR_POR_ID = "SELECT * FROM Animal WHERE IdAnimal = ?";
    private static final String LISTAR_TODOS = "SELECT * FROM Animal ORDER BY Nome";
    private static final String BUSCAR_POR_NOME = "SELECT * FROM Animal WHERE Nome LIKE ? ORDER BY Nome";

    @Override
    public void inserir(Animal animal) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS)) {
            preencher(ps, animal);
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    animal.setIdAnimal(chaves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao inserir animal", e);
        }
    }

    @Override
    public void atualizar(Animal animal) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(ATUALIZAR)) {
            preencher(ps, animal);
            ps.setInt(7, animal.getIdAnimal());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao atualizar animal", e);
        }
    }

    @Override
    public void excluir(int idAnimal) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(EXCLUIR)) {
            ps.setInt(1, idAnimal);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Erro ao excluir animal", e);
        }
    }

    @Override
    public Optional<Animal> buscarPorId(int idAnimal) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(BUSCAR_POR_ID)) {
            ps.setInt(1, idAnimal);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar animal por id", e);
        }
    }

    @Override
    public List<Animal> listarTodos() {
        List<Animal> animais = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(LISTAR_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                animais.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao listar animais", e);
        }
        return animais;
    }

    public List<Animal> buscarPorNome(String parteDoNome) {
        List<Animal> animais = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(BUSCAR_POR_NOME)) {
            ps.setString(1, "%" + parteDoNome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    animais.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar animais por nome", e);
        }
        return animais;
    }

    private void preencher(PreparedStatement ps, Animal animal) throws SQLException {
        ps.setString(1, animal.getNome());
        ps.setString(2, animal.getRaca());
        ps.setString(3, animal.getGenero());
        ps.setObject(4, animal.getNascimento());
        ps.setBigDecimal(5, animal.getPesoKg());
        ps.setBigDecimal(6, animal.getAlturaCm());
    }

    private Animal mapear(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setIdAnimal(rs.getInt("IdAnimal"));
        animal.setNome(rs.getString("Nome"));
        animal.setRaca(rs.getString("Raca"));
        animal.setGenero(rs.getString("Genero"));
        animal.setNascimento(rs.getObject("Nascimento", LocalDate.class));
        animal.setPesoKg(rs.getBigDecimal("PesoKg"));
        animal.setAlturaCm(rs.getBigDecimal("AlturaCm"));
        return animal;
    }
}
