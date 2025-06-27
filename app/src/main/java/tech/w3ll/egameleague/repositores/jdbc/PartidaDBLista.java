package tech.w3ll.egameleague.repositores.jdbc;

import tech.w3ll.egameleague.modelos.Partida;
import tech.w3ll.egameleague.modelos.Time;
import tech.w3ll.egameleague.repositores.IPartidaRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDBLista implements IPartidaRepo {

    @Override
    public void adicionar(Partida partida) {
        String sql = "INSERT INTO partida (time1_id, time2_id, vencedor_id) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, partida.getTime1().getId());
            stmt.setInt(2, partida.getTime2() != null ? partida.getTime2().getId() : Types.NULL);
            stmt.setObject(3, partida.getVencedor() != null ? partida.getVencedor().getId() : null);

            stmt.executeUpdate();
            System.out.println("Partida salva com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar partida: " + e.getMessage());
        }
    }

    @Override
    public void remover(Partida partida) {
        String sql = "DELETE FROM partida WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, partida.getId());
            stmt.executeUpdate();
            System.out.println("Partida removida com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao remover partida: " + e.getMessage());
        }
    }

    @Override
    public List<Partida> listarTodas() {
        List<Partida> partidas = new ArrayList<>();
        String sql = "SELECT id, time1_id, time2_id, vencedor_id FROM partida";

        try (Connection conn = ConexaoPostgres.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            TimeDBLista timeDBLista = new TimeDBLista(); // instancie o repositório de times

            while (rs.next()) {
                int id = rs.getInt("id");
                int time1Id = rs.getInt("time1_id");
                int time2Id = rs.getObject("time2_id", Integer.class);
                int vencedorId = rs.getObject("vencedor_id", Integer.class);

                Time time1 = timeDBLista.buscarPorId(time1Id);
                Time time2 = time2Id != 0 ? timeDBLista.buscarPorId(time2Id) : null;
                Time vencedor = vencedorId != 0 ? timeDBLista.buscarPorId(vencedorId) : null;

                Partida partida = new Partida(time1, time2);
                partida.setId(id);

                if (vencedor != null) {
                    partida.definirVencedor(vencedor);
                }

                partidas.add(partida);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar partidas: " + e.getMessage());
        }

        return partidas;
    }


    @Override
    public void limpar() {
        String sql = "DELETE FROM partida";

        try (Connection conn = ConexaoPostgres.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Todas as partidas foram apagadas.");

        } catch (SQLException e) {
            System.out.println("Erro ao limpar partidas: " + e.getMessage());
        }
    }
}
