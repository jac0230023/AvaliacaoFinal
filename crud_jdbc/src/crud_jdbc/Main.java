package crud_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/seubanco";
        String user = "usuario";
        String password = "senha";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Conexão bem-sucedida!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
