package ua.com.foxminded.university;

import ua.com.foxminded.university.dao.CourseDAO;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class App {

    public static void main(String[] args) {

        Connection connection = DatabaseConnector.getConnection();

//        StudentDAO studentDAO = new StudentDAO(connection);
//        StudentService studentService = new StudentService(studentDAO);
//
//
//        GroupDAO groupDAO = new GroupDAO(connection);
//        GroupService groupService = new GroupService(groupDAO);
//
//
//        CourseDAO courseDAO = new CourseDAO(connection);
//        CourseService courseService = new CourseService(courseDAO);



    }
}
