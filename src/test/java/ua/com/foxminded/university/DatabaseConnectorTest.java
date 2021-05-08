package ua.com.foxminded.university;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectorTest {

    DatabaseConnector objectForTest;

    @Test
    @DisplayName("Correct data")
    void getConnectionShouldReturnConnectionWhenCorrectData() {
        String url = "jdbc:postgresql://localhost:5432/university";
        String login = "postgres";
        String password = "1234";

        objectForTest = new DatabaseConnector(url, login, password);

        assertDoesNotThrow(() -> objectForTest.getConnection());
    }

    @Test
    @DisplayName("Wrong url")
    void getConnectionShouldReturnExceptionWhenWrongUrl () {
        String url = "wrong url";
        String login = "postgres";
        String password = "1234";

        objectForTest = new DatabaseConnector(url, login, password);

        assertThrows(RuntimeException.class, () -> objectForTest.getConnection());
    }

    @Test
    @DisplayName("Wrong login")
    void getConnectionShouldReturnExceptionWhenWrongLogin () {
        String url = "jdbc:postgresql://localhost:5432/university";
        String login = "wrong url";
        String password = "1234";

        objectForTest = new DatabaseConnector(url, login, password);

        assertThrows(RuntimeException.class, () -> objectForTest.getConnection());
    }

    @Test
    @DisplayName("Wrong password")
    void getConnectionShouldReturnExceptionWhenWrongPassword () {
        String url = "jdbc:postgresql://localhost:5432/university";
        String login = "postgres";
        String password = "wrong url";

        objectForTest = new DatabaseConnector(url, login, password);

        assertThrows(RuntimeException.class, () -> objectForTest.getConnection());
    }

    @Test
    @DisplayName("If the connection exists")

    void getConnectionShouldReturnExistingConnectionIfTheConnectionExists () {
        String url = "jdbc:postgresql://localhost:5432/university";
        String login = "postgres";
        String password = "1234";

        objectForTest = new DatabaseConnector(url, login, password);

        Connection firstConnection = objectForTest.getConnection();
        Connection secondConnection = objectForTest.getConnection();

        assertEquals(firstConnection, secondConnection);
    }
}