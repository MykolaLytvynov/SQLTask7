package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO implements CrudOperations<Group, Integer>{

    private final Connection connection;
    private static final String SAVE_GROUP = "INSERT INTO studentgroup (group_name) VALUES (?)";
    private static final String FIND_BY_ID = "SELECT * FROM studentgroup WHERE group_id = ?";
    private static final String EXISTS_BY_ID = "SELECT * FROM studentgroup WHERE EXISTS (Select * FROM studentgroup WHERE group_id =?)";
    private static final String FIND_ALL = "SELECT * FROM studentgroup";
    private static final String COUNT = "SELECT COUNT(group_id) FROM studentgroup";
    private static final String DELETE_GROUP = "DELETE FROM studentgroup WHERE group_id = ?";
    private static final String DELETE_ALL = "DELETE FROM studentgroup";


    public GroupDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Group save(Group group) {
        try {
            PreparedStatement statement = connection.prepareStatement(SAVE_GROUP, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, group.getGroup());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                group.setId(resultSet.getInt("group_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return group;
    }

    @Override
    public Group findById(Integer idGroup) {
        Group foundGroup = new Group(null);
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, idGroup);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                foundGroup.setId(resultSet.getInt("group_id"));
                foundGroup.setGroup(resultSet.getString("group_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return foundGroup;
    }

    @Override
    public boolean existsById(Integer idGroup) {
        boolean existsGroup = false;
        try {
            PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID);
            statement.setInt(1, idGroup);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                existsGroup = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existsGroup;
    }

    @Override
    public List<Group> findAll() {
        List<Group> groups = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                groups.add(new Group(resultSet.getInt("group_id"), resultSet.getString("group_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groups;
    }

    @Override
    public long count() {
        long countGroup = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT);
            while (resultSet.next()) {
                countGroup = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countGroup;
    }

    @Override
    public void deleteById(Integer idGroup) {
        try { PreparedStatement statement = connection.prepareStatement(DELETE_GROUP);
            statement.setInt(1, idGroup);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Group group) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_GROUP);
            statement.setInt(1, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_ALL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
