package tech.w3ll.egameleague.repositores.jdbc;

import tech.w3ll.egameleague.modelos.Time;
import tech.w3ll.egameleague.repositores.ITimeRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeDBLista implements ITimeRepo {

    @Override
    public void adicionar(Time time) {
        String sql = "INSERT INTO time (nome) VALUES (?) RETURNING id";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, time.getNome());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idGerado = rs.getInt("id");
                time.setId(idGerado);
                System.out.println("Time adicionado com ID: " + idGerado);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar time: " + e.getMessage());
        }
    }

    @Override
    public void remover(Time time) {
        String sql = "DELETE FROM time WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, time.getId());
            stmt.executeUpdate();
            System.out.println("Time removido com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao remover time: " + e.getMessage());
        }
    }

    @Override
    public List<Time> listarTodos() {
        List<Time> times = new ArrayList<>();
        String sql = "SELECT id, nome FROM time";

        try (Connection conn = ConexaoPostgres.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Time time = new Time(rs.getString("nome"));
                time.setId(rs.getInt("id"));
                times.add(time);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar times: " + e.getMessage());
        }

        return times;
    }

    @Override
    public Time buscarPorNome(String nome) {
        String sql = "SELECT id, nome FROM time WHERE nome = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Time time = new Time(rs.getString("nome"));
                time.setId(rs.getInt("id"));
                return time;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar time por nome: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Time buscarPorId(int id) {
        String sql = "SELECT id, nome FROM time WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Time time = new Time(rs.getString("nome"));
                time.setId(rs.getInt("id"));
                return time;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar time por id: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void limpar() {
        String sql = "DELETE FROM time";

        try (Connection conn = ConexaoPostgres.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Todos os times foram removidos.");

        } catch (SQLException e) {
            System.out.println("Erro ao limpar time: " + e.getMessage());
        }
    }

    @Override
    public void resetarTabela() {
        String sqlDelete = "DELETE FROM time";
        String sqlResetSeq = "ALTER SEQUENCE time_id_seq RESTART WITH 1";

        try (Connection conn = ConexaoPostgres.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sqlDelete);
            stmt.executeUpdate(sqlResetSeq);
            System.out.println("Tabela \"time\" resetada com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao resetar tabela: " + e.getMessage());
        }
    }

}
