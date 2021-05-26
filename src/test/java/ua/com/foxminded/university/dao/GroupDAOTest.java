package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupDAOTest {

    private final String urlDbForTest = "jdbc:h2:mem:testdb";
    private final String loginDbForTest = "sa";
    private final String password = "";

    private final Connection connection = DriverManager.getConnection(urlDbForTest, loginDbForTest, password);
    private GroupDAO groupDAO = new GroupDAO(connection);

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS studentgroup";
    private static final String CREATE_TABLE_STUDENT_GROUP = "CREATE TABLE studentgroup (group_id SERIAL PRIMARY KEY,\n" +
            " group_name character(5) NOT NULL UNIQUE)";


    GroupDAOTest() throws SQLException {
    }


    @BeforeEach
    void addTables() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(DELETE_TABLE);
        statement.execute(CREATE_TABLE_STUDENT_GROUP);
    }


    @Test
    @DisplayName("Save group in Bd")
    void saveShouldSaveGroupInBd() throws SQLException {

        groupDAO.save(new Group("ce-22"));

        Group expected = new Group(1, "ce-22");

        Group result = null;

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM studentgroup WHERE group_id = 1");
        while (resultSet.next()) {
            result = new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"));
        }

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Save Null instead of group in Bd")
    void saveShouldReturnExceptionWhenEnterNull() {

        assertThrows(RuntimeException.class, () -> groupDAO.save(null));
    }


    @Test
    @DisplayName("Find group by existing id")
    void findByIdShouldReturnGroupWhenEnterExistingId() throws SQLException {

        Group expected = new Group(1, "ce-22");

        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO studentgroup (group_id, group_name) VALUES (1, 'ce-22')");

        Group result = groupDAO.findById(1).orElse(null);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Find group by non-existing id")
    void findByIdShouldReturnNullWhenEnterNonExistingId() throws SQLException {

        Group expected = null;

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM studentgroup WHERE group_id = 244");
        while (resultSet.next()) {
            expected = new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"));
        }

        Group result = groupDAO.findById(244).orElse(null);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Exists By Id")
    void existsById() throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO studentgroup (group_id, group_name) VALUES (3, 'ce-22')");

        boolean expected = false;

        ResultSet resultSet = statement.executeQuery("SELECT * FROM studentgroup WHERE EXISTS (Select * FROM studentgroup WHERE group_id = 3)");
        if (resultSet.next()) {
            expected = true;
        }

        boolean result = groupDAO.existsById(3);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Does not exist by id")
    void existsByIdShouldReturnFalseWhenNotExistsById() throws SQLException {

        boolean expected = false;

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM studentgroup WHERE EXISTS (Select * FROM studentgroup WHERE group_id = 244)");
        if (resultSet.next()) {
            expected = true;
        }

        boolean result = groupDAO.existsById(244);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Find all groups")
    void findAllShouldReturnAllGroups() throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO studentgroup (group_id, group_name) VALUES (1, 'ce-22')");
        statement.execute("INSERT INTO studentgroup (group_id, group_name) VALUES (2, 'fr-12')");
        statement.executeUpdate("INSERT INTO studentgroup (group_id, group_name) VALUES (3, 'fd-13')");

        List<Group> expected = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM studentgroup");
        while (resultSet.next()) {
            expected.add(new Group(resultSet.getInt("group_id"), resultSet.getString("group_name")));
        }

        List<Group> result = groupDAO.findAll();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Count groups")
    void countShouldReturnCountGroups() throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO studentgroup (group_id, group_name) VALUES (1, 'ce-22')");
        statement.execute("INSERT INTO studentgroup (group_id, group_name) VALUES (2, 'fr-12')");
        statement.executeUpdate("INSERT INTO studentgroup (group_id, group_name) VALUES (3, 'fd-13')");

        long expected = 0;

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(group_id) FROM studentgroup");
        while (resultSet.next()) {
            expected = resultSet.getInt(1);
        }

        long result = groupDAO.count();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one group by id")
    void deleteByIdShouldDeleteOneGroupById() throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO studentgroup (group_id, group_name) VALUES (3, 'fd-13')");

        groupDAO.deleteById(3);

        Group expected = null;

        Group result = null;

        ResultSet resultSet = statement.executeQuery("SELECT * FROM studentgroup WHERE group_id = 3");
        while (resultSet.next()) {
            result = new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"));
        }

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one group when specifying a group")
    void deleteShouldDeleteOneGroupWhenSpecifyingGroup() throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO studentgroup (group_id, group_name) VALUES (3, 'fd-13')");

        groupDAO.delete(new Group(3, "fd-13"));

        Group expected = null;

        Group result = null;

        ResultSet resultSet = statement.executeQuery("SELECT * FROM studentgroup WHERE group_id = 3");
        while (resultSet.next()) {
            result = new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"));
        }

        assertEquals(expected, result);
    }

    @Test
    void deleteAllShouldDeleteAllGroups() throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO studentgroup (group_id, group_name) VALUES (1, 'ce-22')");
        statement.execute("INSERT INTO studentgroup (group_id, group_name) VALUES (2, 'fr-12')");
        statement.executeUpdate("INSERT INTO studentgroup (group_id, group_name) VALUES (3, 'fd-13')");

        groupDAO.deleteAll();

        long expected = 0;

        long result = 0;

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(group_id) FROM studentgroup");
        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }

        assertEquals(expected, result);
    }
}