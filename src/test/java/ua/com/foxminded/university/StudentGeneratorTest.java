package ua.com.foxminded.university;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentGeneratorTest {

    StudentGenerator objectForTest = new StudentGenerator();

    @Test
    @DisplayName("5 Students")
    void getStudentsShouldReturnListWith5StudentsWhenEnter5Students() {
        int expected = 5;

        int result = objectForTest.getStudents(5).size();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Empty List")
    void getStudentsGroupShouldReturnEmptyListWhenEnter5Students() {
        int expected = 0;

        int result = objectForTest.getStudents(0).size();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Validation of students")
    void getGeneratedGroupShouldReturnListWithCorrectGroups() {
        String RegexForNamesAndLastNames = "^[a-zA-Z]+$";
        boolean expected = true;
        boolean result = true;

        List<Student> studentList = objectForTest.getStudents(5);
        for (Student student : studentList) {
            if (student.getStudentId() != 0 || student.getGroupId() != null ||
                    !student.getFirstName().matches(RegexForNamesAndLastNames) ||
                    !student.getLastName().matches(RegexForNamesAndLastNames)) {
                result = false;
            }
        }

        assertEquals(expected, result);
    }
}