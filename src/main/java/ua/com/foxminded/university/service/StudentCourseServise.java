package ua.com.foxminded.university.service;

import ua.com.foxminded.university.dao.StudentCourseDAO;

public class StudentCourseServise {
    private final StudentCourseDAO studentCourseDAO;

    public StudentCourseServise(StudentCourseDAO studentCourseDAO) {
        this.studentCourseDAO = studentCourseDAO;
    }

    public void save (int student, int course) {
        studentCourseDAO.save(student, course);
    }
}
