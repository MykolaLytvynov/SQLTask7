package ua.com.foxminded.university;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;


public class ScriptManager {
    private final Connection connection;

    public ScriptManager(Connection connection) {
        this.connection = connection;
    }

    public void executeScript(String pathSqlFile) throws IOException {

        List<String> sqlFile = Files.lines(Paths.get(pathSqlFile)).collect(Collectors.toList());
        String query = "";

        for (String string : sqlFile) {
            query += string + " ";
        }

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
