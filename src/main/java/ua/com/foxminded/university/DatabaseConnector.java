package ua.com.foxminded.university;

import java.sql.Connection;
import java.sql.DriverManager;

import static java.util.Optional.ofNullable;

public class DatabaseConnector {
    private static final String ERR_MESSAGE = "Couldn't create connection, cause: %s";

    private final String url;
    private final String login;
    private final String password;
    private Connection connection;

    public enum SourceType {POSTGRES, H2}

    public DatabaseConnector(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public Connection getConnection() {
        return ofNullable(connection).orElseGet(this::createNewConnection);
    }

    private Connection createNewConnection() {
        try {
            connection = DriverManager.getConnection(url, login, password);
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(String.format(ERR_MESSAGE, e.getLocalizedMessage()));
        }
    }
}
