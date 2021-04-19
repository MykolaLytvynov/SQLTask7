package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements CrudOperations<Student, Integer> {

    private final Connection connection;
    private static final String ADD_STUDENT = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
    private static final String FIND_ALL = "Select * FROM students";
    private static final String FIND_BY_ID = "Select * FROM students WHERE student_id = ?";
    private static final String EXISTS_BY_ID = "Select * FROM students WHERE EXISTS (Select * FROM students WHERE student_id = ?)";
    private static final String COUNT = "SELECT COUNT(student_id) FROM students";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE student_id = ?";
    private static final String DELETE_ALL = "DELETE FROM students";

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
    public Student findById(Integer idStudent) {
        Student foundStudent = new Student(null, null);
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, idStudent);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                foundStudent.setStudentId(resultSet.getInt("student_id"));
                foundStudent.setGroup_id(resultSet.getInt("group_id"));
                foundStudent.setFirstName(resultSet.getString("first_name"));
                foundStudent.setLastName(resultSet.getString("last_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return foundStudent;
    }


    @Override
    public boolean existsById(Integer idStudent) {
        boolean existsStudent = false;
        try (PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID)) {
            statement.setInt(1, idStudent);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                existsStudent = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existsStudent;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {


                students.add(new Student(resultSet.getInt("student_id"),
                        resultSet.getInt("group_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")));

// Или так, если не использовать два конструктура в поджо классе
//                Student student = new Student(null, null);
//                student.setStudentId(resultSet.getInt("student_id"));
//                student.setGroup_id(resultSet.getInt("group_id"));
//                student.setFirstName(resultSet.getString("first_name"));
//                student.setLastName(resultSet.getString("last_name"));
//                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public long count() {
        long count = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public void deleteById(Integer idStudent) {
        try { PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT);
                statement.setInt(1, idStudent);
                statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Student student) {
        try { PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT);
            statement.setInt(1, student.getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try {Statement statement = connection.createStatement();
             statement.executeUpdate(DELETE_ALL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

