package ua.com.foxminded.university.dao;


import org.junit.jupiter.api.*;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.exception.NotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class StudentDAOTest {

    private final String urlDbForTest = "jdbc:h2:mem:testdb";
    private final String loginDbForTest = "sa";
    private final String password = "";

    private final Connection connection = DriverManager.getConnection(urlDbForTest, loginDbForTest, password);
    private StudentDAO studentDAO = new StudentDAO(connection);


    private static final String DELETE_TABLES = "DROP TABLE IF EXISTS students, courses, students_courses";
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE students (student_id SERIAL PRIMARY KEY,\n" +
            " group_id int, first_name character (50) NOT NULL, last_name character (50) NOT NULL)";
    private static final String CREATE_TABLE_COURSES = "CREATE TABLE courses (course_id SERIAL PRIMARY KEY,\n" +
            " course_name character(25) NOT NULL, course_description character(100) NOT NULL)";
    private static final String CREATE_TABLE_STUDENTS_COURSES = "CREATE TABLE students_courses  (student_id int NOT NULL,\n" +
            " course_id int NOT NULL, PRIMARY KEY(student_id, course_id), FOREIGN KEY(student_id) REFERENCES students (student_id)\n" +
            " ON DELETE CASCADE, FOREIGN KEY(course_id) REFERENCES courses (course_id) ON DELETE CASCADE )";


    StudentDAOTest() throws SQLException {
    }


    @BeforeEach
    void addTables() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(DELETE_TABLES);
        statement.execute(CREATE_TABLE_STUDENTS);
        statement.execute(CREATE_TABLE_COURSES);
        statement.executeUpdate(CREATE_TABLE_STUDENTS_COURSES);
    }


    @Test
    @DisplayName("Save student in Bd")
    void saveShouldSaveStudentInBd() throws SQLException {
        Student expected = new Student(1, null, "Robert", "Kohl");

        studentDAO.save(new Student("Robert", "Kohl"));

        Student result = null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE student_id = 1");
        while (resultSet.next()) {
            result = new Student(resultSet.getInt("student_id"),
                    resultSet.getInt("group_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
            if (result.getGroupId() == 0) {
                result.setGroupId(null);
            }
        }
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Save Null instead of student in Bd")
    void saveShouldReturnExceptionWhenEnterNull() {
        assertThrows(RuntimeException.class, () -> studentDAO.save(null));
    }

    @Test
    @DisplayName("Find student by existing id")
    void findByIdShouldReturnStudentWhenEnterExistingId() throws SQLException {
        Student expected = new Student(3, 2, "Viktor", "Drop");
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 2, 'Viktor', 'Drop')");

        Student result = studentDAO.findById(3).orElse(null);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Find student by non-existing id")
    void findByIdShouldReturnNullWhenEnterNonExistingId() throws SQLException {
        Student expected = null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE student_id = 244");
        while (resultSet.next()) {
            expected = new Student(resultSet.getInt("student_id"),
                    resultSet.getInt("group_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
        }

        Student result = studentDAO.findById(244).orElse(null);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Exists By Id")
    void existsByIdShouldReturnTrueWhenExistsById() throws SQLException {
        boolean expected = false;
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 2, 'Viktor', 'Drop')");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE EXISTS (Select * FROM students WHERE student_id = 3)");
        if (resultSet.next()) {
            expected = true;
        }

        boolean result = studentDAO.existsById(3);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Does not exist by id")
    void existsByIdShouldReturnFalseWhenNotExistsById() throws SQLException {
        boolean expected = false;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE EXISTS (Select * FROM students WHERE student_id = 244)");
        if (resultSet.next()) {
            expected = true;
        }

        boolean result = studentDAO.existsById(244);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Find all students")
    void findAllShouldReturnAllStudents() throws SQLException {
        List<Student> expected = new ArrayList<>();
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 2, 'Viktor', 'Drop')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (2, 4, 'Katya', 'Vanil')");
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 21, 'Karla', 'Aderedes')");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
        while (resultSet.next()) {
            expected.add(new Student(resultSet.getInt("student_id"),
                    resultSet.getInt("group_id"),
                    resultSet.getString("first_name").trim(),
                    resultSet.getString("last_name").trim()));
        }

        List<Student> result = studentDAO.findAll();

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Count students")
    void countShouldReturnCountStudents() throws SQLException {
        long expectedResult  = 3;
        long realResult = 0;
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 2, 'Viktor', 'Drop')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (2, 4, 'Katya', 'Vanil')");
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 21, 'Karla', 'Aderedes')");
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(student_id) FROM students");
        while (resultSet.next()) {
            realResult = resultSet.getInt(1);
        }

        long actualResult = studentDAO.count();

        assertEquals(expectedResult, actualResult);
        assertEquals(realResult, actualResult);
    }


    @Test
    @DisplayName("Delete one student by id")
    void deleteByIdShouldDeleteOneStudentById() throws SQLException {
        Student expected = null;
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 21, 'Karla', 'Aderedes')");

        studentDAO.deleteById(3);

        Student result = null;
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE student_id = 3");
        while (resultSet.next()) {
            result = new Student(resultSet.getInt("student_id"),
                    resultSet.getInt("group_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
        }
        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Delete one student when specifying a student")
    void deleteShouldDeleteOneStudentWhenSpecifyingStudent() throws SQLException {
        Student expected = null;
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 21, 'Karla', 'Aderedes')");

        studentDAO.delete(new Student(3, 21, "Karla", "Aderedes"));

        Student result = null;
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE student_id = 3");
        while (resultSet.next()) {
            result = new Student(resultSet.getInt("student_id"),
                    resultSet.getInt("group_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
        }
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Delete all students")
    void deleteAllShouldDeleteAllStudent() throws SQLException {
        long expected = 0;
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 2, 'Viktor', 'Drop')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (2, 4, 'Katya', 'Vanil')");
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 21, 'Karla', 'Aderedes')");

        studentDAO.deleteAll();

        long result = 0;
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(student_id) FROM students");
        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Asign student on course")
    void asignStudentOnCourseShouldAsignStudentOnCourse() throws SQLException {
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(3, 1);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Biology','Biology')");
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 21, 'Karla', 'Aderedes')");

        studentDAO.asignStudentOnCourse(3, 1);

        Map<Integer, Integer> result = new HashMap<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students_courses");
        while (resultSet.next()) {
            result.put(resultSet.getInt("student_id"), resultSet.getInt("course_id"));
        }
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Asign student on course when course does not exist")
    void asignStudentOnCourseShouldReturnExceptionWhenCourseDoesNotExist() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 21, 'Karla', 'Aderedes')");

        assertThrows(RuntimeException.class, () -> studentDAO.asignStudentOnCourse(3, 1));
    }

    @Test
    @DisplayName("Asign student on course when student does not exist")
    void asignStudentOnCourseShouldReturnExceptionWhenStudentDoesNotExist() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Biology','Biology')");

        assertThrows(RuntimeException.class, () -> studentDAO.asignStudentOnCourse(3, 1));
    }


    @Test
    @DisplayName("Remove Student From One Course")
    void removeStudentFromOneOfHisOrHerCourses() throws SQLException {
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(2, 2);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Biology','Biology')");
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Physics','Physics')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 1, 'Nina', 'Vahl')");
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (2, 2, 'Karla', 'Aderedes')");
        statement.execute("INSERT into students_courses (student_id, course_id) values (1, 1)");
        statement.executeUpdate("INSERT into students_courses (student_id, course_id) values (2, 2)");

        studentDAO.removeStudentFromOneOfHisOrHerCourses(1, 1);

        Map<Integer, Integer> result = new HashMap<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students_courses");
        while (resultSet.next()) {
            result.put(resultSet.getInt("student_id"), resultSet.getInt("course_id"));
        }
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Get list courses of one student by id")
    void getListCourseOfOneStudentByIdShouldReturnListCoursesOfOneStudent() throws SQLException {
        List<Integer> expected = new ArrayList<>();
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Biology','Biology')");
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Physics','Physics')");
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 2, 'Karla', 'Aderedes')");
        statement.execute("INSERT into students_courses (student_id, course_id) values (1, 1)");
        statement.executeUpdate("INSERT into students_courses (student_id, course_id) values (1, 2)");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students_courses WHERE student_id = 1");
        while (resultSet.next()) {
            expected.add(resultSet.getInt("course_id"));
        }

        List<Integer> result = studentDAO.getListCourseOfOneStudentById(1);

        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Get list courses of one student by id does not exist")
    void getListCourseOfOneStudentByIdNotShouldReturnExceptionWhenIdDoesNotExist() throws SQLException {
        assertDoesNotThrow(() -> studentDAO.getListCourseOfOneStudentById(12));
    }


    @Test
    @DisplayName("Remove one course of one student")
    void removeOneCourseOfOneStudentShouldReturnListWithoutOneCousre() throws SQLException {
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Biology','Biology')");
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Physics','Physics')");
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 2, 'Karla', 'Aderedes')");
        statement.execute("INSERT into students_courses (student_id, course_id) values (1, 1)");
        statement.executeUpdate("INSERT into students_courses (student_id, course_id) values (1, 2)");

        studentDAO.removeOneCourseOfOneStudent(1, 1);

        List<Integer> result = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students_courses WHERE student_id = 1");
        while (resultSet.next()) {
            result.add(resultSet.getInt("course_id"));
        }
        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Find groups with less or equals student")
    void findGroupsWithLessOrEqualsStudentShouldReturnMapGroupsWithLessOrEqualsStudent() throws SQLException {
        Map<Integer, Integer> expected = new HashMap<>();
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 1, 'Viktor', 'Drop')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (2, 2, 'Katya', 'Vanil')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 2, 'Vika', 'Less')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (4, 2, 'Sveta', 'Nilopo')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (5, 1, 'Gergie', 'Billig')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (6, 3, 'Vlad', 'Taulll')");
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (7, null, 'Karla', 'Aderedes')");
        ResultSet resultSet = statement.executeQuery("SELECT group_id, COUNT(*) FROM students GROUP BY group_id HAVING COUNT(group_id) <= 2 AND COUNT(group_id) != 0");
        while (resultSet.next()) {
            expected.put(resultSet.getInt("group_id"), resultSet.getInt(2));
        }

        Map<Integer, Integer> result = studentDAO.findGroupsWithLessOrEqualsStudent(2);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Find students related to course")
    void findStudentsRelatedToCourse() throws SQLException {
        List<Student> expected = new ArrayList<>();
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Biology','Biology')");
        statement.execute("INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'History','History')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 1, 'Viktor', 'Drop')");
        statement.execute("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (2, 2, 'Katya', 'Vanil')");
        statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (3, 2, 'Vika', 'Less')");

        statement.execute("INSERT into students_courses (student_id, course_id) values (1, 1)");
        statement.execute("INSERT into students_courses (student_id, course_id) values (2, 1)");
        statement.executeUpdate("INSERT into students_courses (student_id, course_id) values (3, 2)");
        ResultSet resultSet = statement.executeQuery("Select students.* from students\n" +
                "INNER JOIN students_courses ON students.student_id = students_courses.student_id\n" +
                "INNER JOIN courses  ON courses.course_id = students_courses.course_id\n" +
                "where courses.course_name = 'Biology'");
        while (resultSet.next()) {
            expected.add(new Student(resultSet.getInt("student_id"), resultSet.getInt("group_id"),
                    resultSet.getString("first_name"), resultSet.getString("last_name")));
        }

        List<Student> result = studentDAO.findStudentsRelatedToCourse("Biology");

        assertEquals(expected, result);
    }
}