package ua.com.foxminded.university.service;


import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;

public class StudentService{

    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public Student save (Student student) {
        Student saved = studentDAO.save(student);
        return saved;
    }


    // Будут реализованы и остальные методы
}
