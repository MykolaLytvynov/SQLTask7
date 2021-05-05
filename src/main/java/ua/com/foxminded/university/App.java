package ua.com.foxminded.university;

import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;

import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {

        ContextHolder context = ContextHolder.connectorBuilder(DatabaseConnector.SourceType.POSTGRES);

        DataInitializer dataInitializer = context.getDataInitializer();
        dataInitializer.initDb();

        List<Group> groupList = dataInitializer.generateGroups(context.getNumberOfGroup());
        dataInitializer.saveGroupsInDb(groupList);

        List<Student> studentList = dataInitializer.generateStudents(context.getNumberOfStudents());
        dataInitializer.addStudentsToGroups(groupList, studentList);
        dataInitializer.saveStudentsInDb(studentList);

        List<Course> courseList = dataInitializer.createCourses();
        dataInitializer.saveCoursesInDb(courseList);
        dataInitializer.asignStudentsToCourses(studentList);

        Menu menu = context.getMenu();
        menu.printMenu();
    }
}
