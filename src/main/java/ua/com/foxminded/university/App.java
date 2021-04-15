package ua.com.foxminded.university;

import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.StudentService;

import java.sql.Connection;

public class App {

    public static void main(String[] args) {

        Connection connection = DatabaseConnector.getConnection();

        StudentDAO studentDAO = new StudentDAO(connection);
        StudentService studentService = new StudentService(studentDAO);

        Student student = new Student("Kostya", "Troffim");
        Student student1 = new Student("Valya", "Troffim");
        Student student2 = new Student("Nika", "Kohl");

        studentService.save(student);
        studentService.save(student1);
        studentService.save(student2);

        System.out.println(student);
        System.out.println(student1);
        System.out.println(student2);

    }

}
