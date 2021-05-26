package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Student;

import java.sql.*;
import java.util.*;

public class StudentDAO implements CrudOperations<Student, Integer> {

    private final Connection connection;
    private static final String ADD_STUDENT = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String FIND_ALL = "SELECT * FROM students";
    private static final String FIND_BY_ID = "SELECT * FROM students WHERE student_id = ?";
    private static final String EXISTS_BY_ID = "SELECT * FROM students WHERE EXISTS (Select * FROM students WHERE student_id = ?)";
    private static final String COUNT = "SELECT COUNT(student_id) FROM students";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE student_id = ?";
    private static final String DELETE_ALL = "DELETE FROM students";

    private static final String ASIGN_STUDENT_ON_COURSE = "INSERT into students_courses (student_id, course_id) values (?, ?)";
    private static final String REMOVE_STUDENT_FROM_ONE_OF_HIS_OR_HER_COURSES = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
    private static final String GET_LIST_COURSE_OF_ONE_STUDENT_BY_ID = "SELECT * FROM students_courses WHERE student_id = ?";
    private static final String REMOVE_ONE_COURSE_OF_ONE_STUDENT = "DELETE FROM students_courses WHERE student_id = ? AND  course_id = ?";
    private static final String FIND_GROUPS_WITH_LESS_OR_EQUALS_STUDENT = "SELECT group_id, COUNT(*) FROM students GROUP BY group_id HAVING COUNT(group_id) <= ? AND COUNT(group_id) != 0;";
    private static final String FIND_STUDENTS_RELATED_TO_COURSE = "Select students.* from students\n" +
            "INNER JOIN students_courses ON students.student_id = students_courses.student_id\n" +
            "INNER JOIN courses  ON courses.course_id = students_courses.course_id\n" +
            "where courses.course_name = ?";

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student save(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
            if (student.getGroupId() == null || student.getGroupId() == 0) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, student.getGroupId());
            }
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
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
    public Optional<Student> findById(Integer idStudent) {
        Student foundStudent = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, idStudent);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                foundStudent = new Student(resultSet.getInt("student_id"),
                        resultSet.getInt("group_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(foundStudent);
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
                        resultSet.getString("first_name").trim(),
                        resultSet.getString("last_name").trim()));
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
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT);
            statement.setInt(1, idStudent);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Student student) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT);
            statement.setInt(1, student.getStudentId());
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

    public void asignStudentOnCourse(int studentId, int courseId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ASIGN_STUDENT_ON_COURSE);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeStudentFromOneOfHisOrHerCourses(int studentId, int courseId) {
        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_STUDENT_FROM_ONE_OF_HIS_OR_HER_COURSES);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getListCourseOfOneStudentById(int studentId) {
        List<Integer> coursesOfOneStudent = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_LIST_COURSE_OF_ONE_STUDENT_BY_ID);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coursesOfOneStudent.add(resultSet.getInt("course_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coursesOfOneStudent;
    }

    public void removeOneCourseOfOneStudent(int studentId, int courseId) {
        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_ONE_COURSE_OF_ONE_STUDENT);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, Integer> findGroupsWithLessOrEqualsStudent(int countStudent) {
        Map<Integer, Integer> groupsWithLessOrEqualsStudent = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_GROUPS_WITH_LESS_OR_EQUALS_STUDENT);
            statement.setInt(1, countStudent);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groupsWithLessOrEqualsStudent.put(resultSet.getInt(1), resultSet.getInt(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupsWithLessOrEqualsStudent;
    }

    public List<Student> findStudentsRelatedToCourse (String nameCourse) {
        List<Student> listStudentsRelatedToCourse = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_STUDENTS_RELATED_TO_COURSE);
            statement.setString(1, nameCourse);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listStudentsRelatedToCourse.add(new Student(
                        resultSet.getInt("student_id"),
                        resultSet.getInt("group_id"),
                        resultSet.getString( "first_name").trim(),
                        resultSet.getString("last_name").trim()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listStudentsRelatedToCourse;
    }
}

