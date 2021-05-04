package ua.com.foxminded.university.service;


import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Map;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public Student save(Student student) {
        Student saved = studentDAO.save(student);
        return saved;
    }

    public Student findById(int idStudent) {
        return studentDAO.findById(idStudent).orElseThrow(() -> new NotFoundException("Student not found by id=" + idStudent));
    }

    public boolean existsById(int idStudent) {
        return studentDAO.existsById(idStudent);
    }

    public List<Student> findAll() {
        List<Student> students = studentDAO.findAll();
        return students;
    }

    public long count() {
        return studentDAO.count();
    }

    public void deleteById(int idStudent) {
        studentDAO.deleteById(idStudent);
    }

    public void delete(Student student) {
        studentDAO.delete(student);
    }

    public void deleteAll() {
        studentDAO.deleteAll();
    }

    public void asignStudentOnCourse(int studentId, int courseId) {
        studentDAO.asignStudentOnCourse(studentId, courseId);
    }

    public void removeStudentFromOneOfHisOrHerCourses(int studentId, int courseId) {
        studentDAO.removeStudentFromOneOfHisOrHerCourses(studentId, courseId);
    }

    public List<Integer> getListCourseOfOneStudentById (int studentId) {
        return studentDAO.getListCourseOfOneStudentById(studentId);
    }

    public void removeOneCourseOfOneStudent (int studentId, int courseId) {
        studentDAO.removeOneCourseOfOneStudent(studentId, courseId);
    }

    public Map<Integer, Integer> findGroupsWithLessOrEqualsStudent (int countStudent) {
        return studentDAO.findGroupsWithLessOrEqualsStudent(countStudent);
    }

    public List<Student> findStudentsRelatedToCourse (String nameCourse) {
        return studentDAO.findStudentsRelatedToCourse(nameCourse);
    }
}
