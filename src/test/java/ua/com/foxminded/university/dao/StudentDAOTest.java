package ua.com.foxminded.university.dao;


import org.junit.jupiter.api.*;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.exception.NotFoundException;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


class StudentDAOTest {

    private StudentDAO studentDAO;

    private final String urlDbForTest = "jdbc:h2:mem:testdb";
    private final String loginDbForTest = "sa";
    private final String password = "";

    private final Connection connection;

    private static final String DELETE_TABLES = "DROP TABLE IF EXISTS students, courses, students_courses";
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE students (student_id SERIAL PRIMARY KEY,\n" +
            " group_id int, first_name character (50) NOT NULL, last_name character (50) NOT NULL)";
    private static final String CREATE_TABLE_COURSES = "CREATE TABLE courses (course_id SERIAL PRIMARY KEY,\n" +
            " course_name character(25) NOT NULL, course_description character(100) NOT NULL)";
    private static final String CREATE_TABLE_STUDENTS_COURSES = "CREATE TABLE students_courses  (student_id int NOT NULL,\n" +
            " course_id int NOT NULL, PRIMARY KEY(student_id, course_id), FOREIGN KEY(student_id) REFERENCES students (student_id)\n" +
            " ON DELETE CASCADE, FOREIGN KEY(course_id) REFERENCES courses (course_id) ON DELETE CASCADE )";

    StudentDAOTest() throws SQLException {

        connection = DriverManager.getConnection(urlDbForTest, loginDbForTest, password);
        studentDAO = new StudentDAO(connection);
    }


    @BeforeEach
    void createTablesForTest() throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute(CREATE_TABLE_STUDENTS);
        statement.execute(CREATE_TABLE_COURSES);
        statement.executeUpdate(CREATE_TABLE_STUDENTS_COURSES);
    }

    @AfterEach
    void deleteTablesForTest() throws SQLException {
        Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_TABLES);
    }

    @Test
    @DisplayName("Save student in Bd")
    void saveShouldReturnSavedStudentWhenEnterStudent() {
        Student expected = new Student(1, null, "Robert", "Kohl");

        Student result = studentDAO.save(new Student("Robert", "Kohl"));

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Save Null instead of student in Bd")
    void saveShouldReturnExeptionWhenEnterNull() {

        assertThrows(RuntimeException.class, () -> studentDAO.save(null));
    }

    @Test
    @DisplayName("Find student by existing id")
    void findByIdShouldReturnStudentWhenEnterExistingId() {
        Student expected = new Student(1, 0, "Nick", "Kohl");

        studentDAO.save(new Student("Nick", "Kohl"));

        Student result = studentDAO.findById(1).orElseThrow(() -> new NotFoundException("Student not found by id=2"));

        assertEquals(expected, result);

    }

    @Test
    @DisplayName("Find student by non-existing id")
    void findByIdShouldReturnNullWhenEnterNonExistingId() {

        Student expected = null;

        Student result = studentDAO.findById(244).orElse(null);

        assertEquals(expected, result);

    }

    @Test
    void existsById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void count() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void asignStudentOnCourse() {
    }

    @Test
    void removeStudentFromOneOfHisOrHerCourses() {
    }

    @Test
    void getListCourseOfOneStudentById() {
    }

    @Test
    void removeOneCourseOfOneStudent() {
    }

    @Test
    void findGroupsWithLessOrEqualsStudent() {
    }

    @Test
    void findStudentsRelatedToCourse() {
    }
}