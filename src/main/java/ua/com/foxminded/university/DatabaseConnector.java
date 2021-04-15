package ua.com.foxminded.university;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ua.com.foxminded.university.Constans.*;

public class DatabaseConnector {
    private static DatabaseConnector databaseConnector = new DatabaseConnector();
    private static Connection connection;

    private DatabaseConnector() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Connection failure");
        }
    }

    public static synchronized Connection getConnection() {
        return connection;
    }

}
