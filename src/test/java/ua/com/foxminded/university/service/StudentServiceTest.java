package ua.com.foxminded.university.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.service.StudentService;

import java.sql.Connection;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Call to method SAVE in Dao and return Exception when pass Null")
    void saveShouldReturnExceptionWhenPassNull() {

        given(studentDAO.save(null)).willThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> studentService.save(null));
        verify(studentDAO).save(null);
    }

    @Test
    @DisplayName("Call to method findById in Dao and return Student")
    void findByIdShouldCallToMethodFindByIdInDaoAndReturnStudent() {

        Student expected = new Student(1, 0, "Robert", "Kohl");

        given(studentDAO.findById(1)).willReturn(Optional.of(expected));

        Student result = studentService.findById(1);

        assertEquals(expected, result);
        verify(studentDAO).findById(1);
    }


    @Test
    @DisplayName("Call to method findById in Dao and return message when pass does not exist id")
    void findByIdShouldReturnMessageWhenPassDoesNotExistId() {

        given(studentDAO.findById(12)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> studentService.findById(12));
        verify(studentDAO).findById(12);
    }


    @Test
    @DisplayName("Call to method existsById in Dao and return TRUE when pass exist id")
    void existsByIdShouldReturnTrueWhenPassExistId() {

        boolean expected = true;

        given(studentDAO.existsById(1)).willReturn(true);

        boolean result = studentService.existsById(1);

        assertEquals(expected, result);
        verify(studentDAO).existsById(1);
    }

    @Test
    @DisplayName("Call to method existsById in Dao and return FALSE when pass does not exist id")
    void existsByIdShouldReturnFalseWhenPassDoesNotExistId() {

        boolean expected = false;

        given(studentDAO.existsById(1)).willReturn(false);

        boolean result = studentService.existsById(1);

        assertEquals(expected, result);
        verify(studentDAO).existsById(1);
    }


    @Test
    @DisplayName("Call to method findAll in Dao and return List all students")
    void findAllShouldReturnListAllStudents() {

        List<Student> expected = new ArrayList<>();
        expected.add(new Student(1, 0, "Karla", "Full"));

        given(studentDAO.findAll()).willReturn(expected);

        List<Student> result = studentService.findAll();

        assertEquals(expected, result);
        verify(studentDAO).findAll();
    }

    @Test
    @DisplayName("Call to method count in Dao and return count all students")
    void countShouldReturnCountAllStudents() {

        long expected = 1;

        given(studentDAO.count()).willReturn(1L);

        long result = studentService.count();

        assertEquals(expected, result);
        verify(studentDAO).count();
    }

    @Test
    @DisplayName("Call to method deleteById in Dao")
    void deleteByIdShouldCallToMethodDeleteByIdInDao() {

        studentService.deleteById(1);

        verify(studentDAO).deleteById(1);
    }

    @Test
    @DisplayName("Call to method delete in Dao")
    void deleteShouldCallToMethodDeleteInDao() {

        Student student = new Student(1, 0, "Karla", "Li");

        studentService.delete(student);
        verify(studentDAO).delete(student);
    }

    @Test
    @DisplayName("Call to method deleteAll in Dao")
    void deleteAllShouldCallToMethodDeleteAllInDao() {

        studentService.deleteAll();
        verify(studentDAO).deleteAll();
    }

    @Test
    @DisplayName("Call to method asignStudentOnCourse in Dao")
    void asignStudentOnCourseShouldCallToMethodAsignStudentOnCourseInDao() {

        studentService.asignStudentOnCourse(1, 1);
        verify(studentDAO).asignStudentOnCourse(1, 1);
    }

    @Test
    @DisplayName("Call to method removeStudentFromOneOfHisOrHerCourses in Dao")
    void removeStudentFromOneOfHisOrHerCoursesShouldCallToMethodInDao() {

        studentService.removeStudentFromOneOfHisOrHerCourses(1, 1);
        verify(studentDAO).removeStudentFromOneOfHisOrHerCourses(1, 1);
    }

    @Test
    @DisplayName("Call to method getListCourseOfOneStudentById in Dao and return List courses")
    void getListCourseOfOneStudentByIdShouldCallToMethodInDaoAndReturnListIdCourses() {

        List<Integer> expected = new ArrayList<>();
        expected.add(1);

        given(studentDAO.getListCourseOfOneStudentById(1)).willReturn(expected);

        List<Integer> result = studentService.getListCourseOfOneStudentById(1);
        verify(studentDAO).getListCourseOfOneStudentById(1);
    }

    @Test
    @DisplayName("Call to method removeOneCourseOfOneStudent in Dao")
    void removeOneCourseOfOneStudentShouldCallToMethodInDao() {

        studentService.removeOneCourseOfOneStudent(1, 1);
        verify(studentDAO).removeOneCourseOfOneStudent(1, 1);
    }

    @Test
    @DisplayName("Call to method findGroupsWithLessOrEqualsStudent in Dao and return map with id groups")
    void findGroupsWithLessOrEqualsStudentShouldCallToMethodInDaoAndReturnMapWithIdGroups() {

        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(1, 1);

        given(studentDAO.findGroupsWithLessOrEqualsStudent(1)).willReturn(expected);

        Map<Integer, Integer> result = studentService.findGroupsWithLessOrEqualsStudent(1);
        verify(studentDAO).findGroupsWithLessOrEqualsStudent(1);
    }

    @Test
    @DisplayName("Call to method findStudentsRelatedToCourse in Dao and return list with students")
    void findStudentsRelatedToCourseShouldCallToMethodInDaoAndReturnListWithStudents() {

        List<Student> expected = new ArrayList<>();
        expected.add(new Student(1, 1, "Karl", "Kohl"));

        given(studentDAO.findStudentsRelatedToCourse("Economy")).willReturn(expected);

        List<Student> result = studentService.findStudentsRelatedToCourse("Economy");
        verify(studentDAO).findStudentsRelatedToCourse("Economy");
    }
}