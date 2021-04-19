package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO implements CrudOperations<Course, Integer> {

    private final Connection connection;
    private static final String SAVE = "INSERT INTO courses (course_name, course_description) VALUES (?,?)";
    private static final String FIND_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
    private static final String EXISTS_BY_ID = "SELECT * FROM courses WHERE EXISTS (SELECT * FROM courses WHERE course_id = ?)";
    private static final String FIND_ALL = "SELECT * FROM courses";
    private static final String COUNT = "SELECT COUNT(course_id) FROM courses";
    private static final String DELETE_COURSE = "DELETE FROM courses WHERE course_id = ?";
    private static final String DELETE_ALL = "DELETE FROM courses";


    public CourseDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Course save(Course course) {
        try {
            PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            course.setCourseId(resultSet.getInt("course_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course;
    }

    @Override
    public Course findById(Integer idCourse) {
        Course foundCourse = new Course(null, null);

        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, idCourse);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                foundCourse.setCourseId(resultSet.getInt("course_id"));
                foundCourse.setCourseName(resultSet.getString("course_name"));
                foundCourse.setCourseDescription(resultSet.getString("course_description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return foundCourse;
    }

    @Override
    public boolean existsById(Integer idCourse) {
        boolean existsCourse = false;
        try {
            PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID);
            statement.setInt(1, idCourse);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                existsCourse = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existsCourse;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                courses.add(new Course(resultSet.getInt("course_id"),
                        resultSet.getString("course_name"),
                        resultSet.getString("course_description")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public long count() {
        long countCourse = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT);
            while (resultSet.next()) {
                countCourse = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countCourse;
    }

    @Override
    public void deleteById(Integer idCourse) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_COURSE);
            statement.setInt(1, idCourse);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Course course) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_COURSE);
            statement.setInt(1, course.getCourseId());
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
