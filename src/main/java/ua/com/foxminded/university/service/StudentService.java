package ua.com.foxminded.university.service;


import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;

import java.util.List;

public class StudentService{

    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public Student save (Student student) {
        Student saved = studentDAO.save(student);
        return saved;
    }

    public Student findById (int idStudent) {
        return studentDAO.findById(idStudent);
    }

    public boolean existsById (int idStudent) {
        return studentDAO.existsById(idStudent);
    }

    public List<Student> findAll () {
        List <Student> students = studentDAO.findAll();
        return students;
    }

    public long count () {
        return studentDAO.count();
    }

    public void deleteById (int idStudent) {
        studentDAO.deleteById(idStudent);
    }

    public void delete(Student student) {
        studentDAO.delete(student);
    }

    public void deleteAll() {
        studentDAO.deleteAll();
    }


}
