package tech.w3ll.egameleague.repositores.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoPostgres {
    private static final Logger LOGGER = Logger.getLogger(ConexaoPostgres.class.getName());

    private static final String URL = "";
    private static final String USUARIO = "";
    private static final String SENHA = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao conectar ao banco de dados.", e);
        }
        return null;
    }
}
