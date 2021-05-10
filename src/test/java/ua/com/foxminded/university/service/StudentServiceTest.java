package ua.com.foxminded.university.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.StudentService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class StudentServiceTest {

    @Mock
    private StudentDAO studentDAO;

    private StudentService studentService;

    public StudentServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.studentService = new StudentService(studentDAO);
    }

    @Test
    @DisplayName("Call to method SAVE in Dao and return Student")
    void saveShouldCallToMethodSaveInDaoAndReturnStudent() {
        Student expected = new Student(1, 0, "Robert", "Kohl");

        given(studentDAO.save(expected)).willReturn(expected);

        Student result = studentService.save(expected);

        assertEquals(expected, result);
        verify(studentDAO).save(expected);
    }

    @Test
    @DisplayName("Call to method SAVE in Dao and return Exeption when pass Null")
    void saveShouldReturnExeptionWhenPassNull() {
        given(studentDAO.save(null)).willThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> studentService.save(null));
        verify(studentDAO).save(null);
    }

    @Test
    void findById() {
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