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

import java.io.IOException;
import java.sql.Connection;
import java.util.*;

import static ua.com.foxminded.university.Constans.*;


public class App {

    public static void main(String[] args) throws IOException {

        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        StudentDAO studentDAO = new StudentDAO(connection);
        StudentService studentService = new StudentService(studentDAO);
        GroupDAO groupDAO = new GroupDAO(connection);
        GroupService groupService = new GroupService(groupDAO);
        CourseDAO courseDAO = new CourseDAO(connection);
        CourseService courseService = new CourseService(courseDAO);

        DataInitializer dataInitializer = new DataInitializer(groupService, studentService, courseService, connection);
        dataInitializer.initDb();

        List<Group> groupList = dataInitializer.generateGroups(NUMBER_OF_GROUP);
        dataInitializer.saveGroupsInDb(groupList);

        List<Student> studentList = dataInitializer.generateStudents(NUMBER_OF_STUDENTS);
        dataInitializer.addStudentsToGroups(groupList, studentList);
        dataInitializer.saveStudentsInDb(studentList);

        List<Course> courseList = dataInitializer.createCourses();
        dataInitializer.saveCoursesInDb(courseList);
        dataInitializer.asignStudentsToCourses(studentList);

        Menu menu = new Menu(studentService, groupService, courseService);
        menu.printMenu();
    }
}
