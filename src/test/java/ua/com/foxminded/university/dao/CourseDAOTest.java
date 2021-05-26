package ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Course;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {

    private final String urlDbForTest = "jdbc:h2:mem:testdb";
    private final String loginDbForTest = "sa";
    private final String password = "";

    private final Connection connection = DriverManager.getConnection(urlDbForTest, loginDbForTest, password);
    private CourseDAO courseDAO = new CourseDAO(connection);


    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS courses";

    private static final String CREATE_TABLE_COURSES = "CREATE TABLE courses (course_id SERIAL PRIMARY KEY,\n" +
            " course_name character(25) NOT NULL, course_description character(100) NOT NULL)";


    CourseDAOTest() throws SQLException {
    }

    @BeforeEach
    void addTables () throws SQLException{
        Statement statement = connection.createStatement();
        statement.execute(DELETE_TABLE);
        statement.execute(CREATE_TABLE_COURSES);
    }


    @Test
    @DisplayName("Save course in Bd")
    void saveShouldReturnSaveCourseInBd() throws SQLException{
        courseDAO.save(new Course("Physics", "What is physics"));

        Course expected = new Course(1, "Physics", "What is physics");

        Course result = null;

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE course_id = 1");
        while (resultSet.next()) {
            result = new Course(resultSet.getInt("course_id"),
                    resultSet.getString("course_name"),
                    resultSet.getString("course_description"));
        }

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Save Null instead of course in Bd")
    void saveShouldReturnExceptionWhenEnterNull() {

        assertThrows(RuntimeException.class, () -> courseDAO.save(null));
    }

    @Test
    @DisplayName("Find course by existing id")
    void findByIdShouldReturnCourseWhenEnterExistingId() throws SQLException{

        Course expected = new Course(3, "Physics", "What is physics");

        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO courses (course_id, course_name, course_description) VALUES (3, 'Physics','What is physics')");

        Course result = courseDAO.findById(3).orElse(null);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Find course by non-existing id")
    void findByIdShouldReturnNullWhenEnterNonExistingId() throws SQLException {

        Course expected = null;

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE course_id = 244");
        while (resultSet.next()) {
            expected = new Course(resultSet.getInt("course_id"),
                    resultSet.getString("course_name"),
                    resultSet.getString("course_description"));
        }

        Course result = courseDAO.findById(244).orElse(null);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Exists By Id")
    void existsByIdShouldReturnTrueWhenExistsById() throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO courses (course_id, course_name, course_description) VALUES (3, 'Physics','What is physics')");

        boolean expected = false;

        ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE EXISTS (Select * FROM courses WHERE course_id = 3)");
        if (resultSet.next()) {
            expected = true;
        }

        boolean result = courseDAO.existsById(3);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Does not exist by id")
    void existsByIdShouldReturnFalseWhenNotExistsById() throws SQLException {

        boolean expected = false;

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE EXISTS (Select * FROM courses WHERE course_id = 244)");
        if (resultSet.next()) {
            expected = true;
        }

        boolean result = courseDAO.existsById(244);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Find all courses")
    void findAllShouldReturnAllCourses() throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Physics','What is physics')");
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Economy','What is economy')");
        statement.executeUpdate("INSERT INTO courses (course_id, course_name, course_description) VALUES (3, 'Astrology','What is astrology')");

        List<Course> expected = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM courses");
        while (resultSet.next()) {
            expected.add(new Course(resultSet.getInt("course_id"),
                    resultSet.getString("course_name"),
                    resultSet.getString("course_description")));
        }

        List<Course> result = courseDAO.findAll();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Count courses")
    void countShouldReturnCountCourses() throws SQLException{

        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Physics','What is physics')");
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Economy','What is economy')");
        statement.executeUpdate("INSERT INTO courses (course_id, course_name, course_description) VALUES (3, 'Astrology','What is astrology')");

        long expected = 0;

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(course_id) FROM courses");
        while (resultSet.next()) {
            expected = resultSet.getInt(1);
        }

        long result = courseDAO.count();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one course by id")
    void deleteByIdShouldDeleteOneCourseById() throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO courses (course_id, course_name, course_description) VALUES (3, 'Astrology','What is astrology')");

        courseDAO.deleteById(3);

        Course expected = null;

        Course result = null;

        ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE course_id = 3");
        while (resultSet.next()) {
            result = new Course(resultSet.getInt("course_id"),
                    resultSet.getString("course_name"),
                    resultSet.getString("course_description"));
        }

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete one course when specifying a course")
    void deleteShouldDeleteOneCourseWhenSpecifyingCourse() throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO courses (course_id, course_name, course_description) VALUES (3, 'Astrology','What is astrology')");

        courseDAO.delete(new Course(3, "Astrology","What is astrology"));

        Course expected = null;

        Course result = null;

        ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE course_id = 3");
        while (resultSet.next()) {
            result = new Course(resultSet.getInt("course_id"),
                    resultSet.getString("course_name"),
                    resultSet.getString("course_description"));
        }

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete all courses")
    void deleteAllShouldDeleteAllCourses() throws SQLException{

        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Physics','What is physics')");
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Economy','What is economy')");
        statement.executeUpdate("INSERT INTO courses (course_id, course_name, course_description) VALUES (3, 'Astrology','What is astrology')");

        courseDAO.deleteAll();

        long expected = 0;

        long result = 0;

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(course_id) FROM courses");
        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }

        assertEquals(expected, result);
    }
}