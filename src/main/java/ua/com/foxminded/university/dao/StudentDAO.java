package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Student;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class StudentDAO implements CrudOperations<Student, Integer> {

private final Connection connection;
private static final String ADD_STUDENT = "INSERT INTO students (first_name, last_name) values (?, ?)";
private static final String GET_ALL = "Select * FROM students";
private static final String FIND_BY_ID = "Select * FROM students WHERE student_id = '?'";

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Student save(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.executeUpdate();
            ResultSet getKey = statement.getGeneratedKeys();
            getKey.next();
            student.setStudentId(getKey.getInt("student_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public Student findById(Integer integer) {
//        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
//            ResultSet resultSet = statement.getResultSet();
//        resultSet.next();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public boolean existsById(Integer student) {
        return false;
    }

    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer student) {

    }

    @Override
    public void delete(Student student) {

    }

    @Override
    public void deleteAll() {

    }
}

