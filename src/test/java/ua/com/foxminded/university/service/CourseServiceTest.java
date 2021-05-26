package ua.com.foxminded.university.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.university.dao.CourseDAO;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CourseServiceTest {

    @Mock
    private CourseDAO courseDAO;

    private CourseService courseService;

    public CourseServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.courseService = new CourseService(courseDAO);
    }

    @Test
    @DisplayName("Call to method SAVE in Dao and return Course")
    void saveShouldCallToMethodSaveInDaoAndReturnCourse() {
        Course expected = new Course(1, "Biology", "What is biology");
        given(courseDAO.save(expected)).willReturn(expected);

        Course result = courseService.save(expected);

        assertEquals(expected, result);
        verify(courseDAO).save(expected);
    }


    @Test
    @DisplayName("Call to method SAVE in Dao and return Exception when pass Null")
    void saveShouldReturnExceptionWhenPassNull() {
        given(courseDAO.save(null)).willThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> courseService.save(null));
        verify(courseDAO).save(null);
    }

    @Test
    @DisplayName("Call to method findById in Dao and return Course")
    void findByIdShouldCallToMethodFindByIdInDaoAndReturnCourse() {
        Course expected = new Course(1, "Biology", "What is biology");
        given(courseDAO.findById(1)).willReturn(Optional.of(expected));

        Course result = courseService.findById(1);

        assertEquals(expected, result);
        verify(courseDAO).findById(1);
    }

    @Test
    @DisplayName("Call to method findById in Dao and return message when pass does not exist id")
    void findByIdShouldReturnMessageWhenPassDoesNotExistId() {
        given(courseDAO.findById(12)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courseService.findById(12));
        verify(courseDAO).findById(12);
    }


    @Test
    @DisplayName("Call to method existsById in Dao and return TRUE when pass exist id")
    void existsByIdShouldReturnTrueWhenPassExistId() {
        boolean expected = true;
        given(courseDAO.existsById(1)).willReturn(true);

        boolean result = courseService.existsById(1);

        assertEquals(expected, result);
        verify(courseDAO).existsById(1);
    }

    @Test
    @DisplayName("Call to method existsById in Dao and return FALSE when pass does not exist id")
    void existsByIdShouldReturnFalseWhenPassDoesNotExistId() {
        boolean expected = false;
        given(courseDAO.existsById(1)).willReturn(false);

        boolean result = courseService.existsById(1);

        assertEquals(expected, result);
        verify(courseDAO).existsById(1);
    }

    @Test
    @DisplayName("Call to method findAll in Dao and return List all courses")
    void findAllShouldReturnListAllCourses() {
        List<Course> expected = new ArrayList<>();
        expected.add(new Course(1, "Biology", "What is biology"));
        given(courseDAO.findAll()).willReturn(expected);

        List<Course> result = courseService.findAll();

        assertEquals(expected, result);
        verify(courseDAO).findAll();
    }

    @Test
    @DisplayName("Call to method count in Dao and return count all courses")
    void countShouldReturnCountAllCourses() {
        long expected = 1;
        given(courseDAO.count()).willReturn(1L);

        long result = courseService.count();

        assertEquals(expected, result);
        verify(courseDAO).count();
    }

    @Test
    @DisplayName("Call to method deleteById in Dao")
    void deleteByIdShouldCallToMethodDeleteByIdInDao() {
        courseService.deleteById(1);

        verify(courseDAO).deleteById(1);
    }

    @Test
    @DisplayName("Call to method delete in Dao")
    void deleteShouldCallToMethodDeleteInDao() {
        Course course = new Course(1, "Biology", "What is biology");

        courseService.delete(course);
        verify(courseDAO).delete(course);
    }

    @Test
    @DisplayName("Call to method deleteAll in Dao")
    void deleteAllShouldCallToMethodDeleteAllInDao() {
        courseService.deleteAll();
        verify(courseDAO).deleteAll();
    }
}