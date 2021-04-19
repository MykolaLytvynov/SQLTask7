package ua.com.foxminded.university;

import ua.com.foxminded.university.dao.CourseDAO;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

import java.sql.Connection;
import java.util.List;

public class App {

    public static void main(String[] args) {

        Connection connection = DatabaseConnector.getConnection();

        StudentDAO studentDAO = new StudentDAO(connection);
        StudentService studentService = new StudentService(studentDAO);

//        Student student = new Student("Kostya", "Troffim");
//        Student student1 = new Student("Valya", "Troffim");
//        Student student2 = new Student("Nika", "Kohl");
//
//        studentService.save(student);
//        studentService.save(student1);
//        studentService.save(student2);
//
//        System.out.println(student);
//        System.out.println(student1);
//        System.out.println(student2);

//        Student student = studentService.findById(5);
//        Student student1 = studentService.findById(22);

//        boolean student = studentService.existsById(12);
//        boolean student1 = studentService.existsById(3);

//        List<Student> students = studentService.findAll();
//
//                System.out.println(students);
//
//
//        long count = studentService.count();
//        System.out.println(count);

        //       studentService.deleteById(8);

//        Student example = new Student(11, 0, "Nika", "Kohl");
//        studentService.delete(example);

//studentService.deleteAll();

        GroupDAO groupDAO = new GroupDAO(connection);
        GroupService groupService = new GroupService(groupDAO);

//        Group group1 = new Group("Gr1");
//        Group group2 = new Group("Gr2");
//
//        groupService.save(group1);
//        groupService.save(group2);

//        Group group = groupService.findById(2);
//        System.out.println(group);


//        List<Group> groups = groupService.findAll();
//        for (Group group : groups) {
//            System.out.println(group.getId() + " " + group.getGroup());
//        }

//        long count = groupService.count();
//        System.out.println(count);

//        groupService.deleteById(4);


//        Group gr1 = new Group(3, "Gr1");
//        groupService.delete(gr1);

//        groupService.deleteAll();


        CourseDAO courseDAO = new CourseDAO(connection);
        CourseService courseService = new CourseService(courseDAO);

//        Course course1 = new Course("Chemie", "uber Chemie");
//        Course course2 = new Course("Sprache", "uber Sprache");
//
//        courseService.save(course1);
//        courseService.save(course2);


//        Course foundCourse = courseService.findById(2);
//        System.out.println(foundCourse);

//        System.out.println(courseService.existsById(2));
//        System.out.println(courseService.existsById(22));


//        List<Course> courses = courseService.findAll();
//
//        for (Course course : courses) {
//            System.out.println(course.getCourseId() + " " + course.getCourseName() + " " + course.getCourseDescription());
//        }

//        long foundCourses = courseService.count();
//        System.out.println(foundCourses);

//            courseService.deleteById(2);

//        Course courseForExample = new Course(3, "Chemie", "uber Chemie");
//        courseService.delete(courseForExample);

//        courseService.deleteAll();

    }
}
