package ua.com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class StudentCourseDAO {
    private final Connection connection;
    private static final String SAVE = "INSERT into students_courses (student_id, course_id) values (?, ?)";

    public StudentCourseDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(int student, int course) {
        try {
            PreparedStatement statement = connection.prepareStatement(SAVE);
            statement.setInt(1, student);
            statement.setInt(2, course);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
