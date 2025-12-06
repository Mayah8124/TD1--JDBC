package config;

import java.sql.*;

public class DatabaseConnection {
    private final String URL = "jdbc:postgresql://localhost:5432/product_management_db";
    private final String USER = "postgres";
    private final String PASSWORD = "nekoko";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
