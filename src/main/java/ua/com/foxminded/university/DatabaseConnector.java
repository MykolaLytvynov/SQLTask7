package ua.com.foxminded.university;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static DatabaseConnector databaseConnector = new DatabaseConnector();
    private static Connection connection;
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private static final String URL = "jdbc:postgresql://localhost:5432/university";

    private DatabaseConnector() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Connection failure");
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}
