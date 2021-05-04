package ua.com.foxminded.university;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ua.com.foxminded.university.Constans.*;

public class DatabaseConnector {
    Connection connection;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Database Connection failure. Cause: " + e.getMessage());
        }
        return connection;
    }

}
