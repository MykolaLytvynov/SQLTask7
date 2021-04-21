package ua.com.foxminded.university.service;

import ua.com.foxminded.university.dao.CourseDAO;
import ua.com.foxminded.university.dao.CrudOperations;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public class CourseService {

    private final CourseDAO courseDAO;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public Course save(Course course) {
        return courseDAO.save(course);
    }

    public Course findById(Integer idCourse) {
        return courseDAO.findById(idCourse).orElseThrow(() -> new NotFoundException("Course not found by id=" + idCourse));
    }

    public boolean existsById(Integer idCourse) {
        return courseDAO.existsById(idCourse);
    }

    public List<Course> findAll() {
        return courseDAO.findAll();
    }

    public long count() {
        return courseDAO.count();
    }

    public void deleteById(Integer idCourse) {
        courseDAO.deleteById(idCourse);
    }

    public void delete(Course course) {
        courseDAO.delete(course);
    }

    public void deleteAll() {
        courseDAO.deleteAll();
    }
}

