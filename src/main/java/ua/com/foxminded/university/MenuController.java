package ua.com.foxminded.university;

import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class MenuController {
    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;
    Scanner scanner = new Scanner(System.in);

    public MenuController(StudentService studentService, GroupService groupService, CourseService courseService) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.courseService = courseService;
    }

    public void findGroupsWithLessOrEqualsStudent() {

        System.out.println("Enter the number of students");
        String numberOfStudents = scanner.nextLine();

        while (true) {
            if (cheakCorrectnessNumber(numberOfStudents)) {
                break;
            }
            System.out.println("Enter the number of students");
            numberOfStudents = scanner.nextLine();
        }

        Map<Integer, Integer> groupsWithLessOrEqualsStudent = studentService.findGroupsWithLessOrEqualsStudent(Integer.valueOf(numberOfStudents));

        for (Entry entry : groupsWithLessOrEqualsStudent.entrySet()) {
            System.out.println(groupService.findById((int) entry.getKey()).getGroup() + " has " + entry.getValue() + " students;");
        }
    }

    public void findStudentsRelatedToCourse() {

        System.out.println("Enter the course name");
        String courseName = scanner.nextLine();

        List<Student> listStudentsRelatedToCourse = studentService.findStudentsRelatedToCourse(courseName);

        while (listStudentsRelatedToCourse.isEmpty()) {

            System.out.println("---This course does not exist:" + "\"" + courseName + "\"");

            System.out.println("---Try these course titles");
            List<Course> courseList = courseService.findAll();
            int i = 1;
            for (Course course : courseList) {
                System.out.println(i + ") " + course.getCourseName() + ";");
                i++;
            }

            System.out.println("\nEnter the course name");
            courseName = scanner.nextLine();
            listStudentsRelatedToCourse = studentService.findStudentsRelatedToCourse(courseName);
        }
        int i = 1;
        for (Student student : listStudentsRelatedToCourse) {
            System.out.println(i + ") " + student.getFirstName() + " " + student.getLastName() + "(id = " + student.getStudentId() + ", id group = " + student.getGroupId() + ");");
            i++;
        }
        System.out.println("\n");
    }

    public void addNewStudent() {

        System.out.println("Enter student name");
        String name = scanner.nextLine();
        while (!checkCorrectnessNameAndLastName(name)) {
            System.out.println("Enter student name");
            name = scanner.nextLine();
        }

        System.out.println("Enter student last name");
        String lastName = scanner.nextLine();
        while (!checkCorrectnessNameAndLastName(lastName)) {
            System.out.println("Enter student last name");
            lastName = scanner.nextLine();
        }

        System.out.println("Add a student to the group by ID:");

        List<Group> groupList = groupService.findAll();
        for (Group group : groupList) {
            System.out.println(group.getId() + ") " + group.getGroup() + ";");
        }

        System.out.println("-----------------");
        System.out.println("Select ID from the existing list\n");

        String groupId = scanner.nextLine();

        while (true) {
            if (cheakCorrectnessNumber(groupId)) {
                if (!groupService.existsById(Integer.valueOf(groupId))) {
                    System.out.println("\n---This ID doesn't exist\n");
                    System.out.println("Select ID from the existing list");
                } else break;
            }
            groupId = scanner.nextLine();
        }

        Student newStudent = new Student(0, Integer.valueOf(groupId), name, lastName);
        studentService.save(newStudent);
        System.out.println("\n---New student added\n");
    }

    public void deleteStudentByStudentId() {
        System.out.println("Enter student ID");
        String studentId = scanner.nextLine();

        while (true) {
            if (cheakCorrectnessNumber(studentId)) {
                if (!studentService.existsById(Integer.valueOf(studentId))) {
                    System.out.println("\n---This ID doesn't exist\n");
                } else break;
            }
            System.out.println("Enter student ID");
            studentId = scanner.nextLine();
        }

        studentService.deleteById(Integer.valueOf(studentId));
        System.out.println("\n---Student was deleted\n");
    }

    public void addStudentToCourse() {
        System.out.println("Enter student ID");
        String studentId = scanner.nextLine();

        while (true) {
            if (cheakCorrectnessNumber(studentId)) {
                if (!studentService.existsById(Integer.valueOf(studentId))) {
                    System.out.println("\n---This ID doesn't exist\n");
                } else break;
            }
            System.out.println("Enter student ID");
            studentId = scanner.nextLine();
        }
        System.out.println("Select course ID from the list:\n");

        for (Course course : courseService.findAll()) {
            System.out.println(course.getCourseId() + ") " + course.getCourseName() + " (" + course.getCourseDescription() + ");");
        }

        System.out.println("Enter course ID");
        String courseId = scanner.nextLine();

        while (true) {
            if (cheakCorrectnessNumber(courseId)) {
                if (!courseService.existsById(Integer.valueOf(courseId))) {
                    System.out.println("---This ID doesn't exist\n");
                } else break;
            }
            System.out.println("Enter course ID");
            courseId = scanner.nextLine();
        }

        try {
            studentService.asignStudentOnCourse(Integer.valueOf(studentId), Integer.valueOf(courseId));
            System.out.println("\n---Student added\n");
        } catch (RuntimeException e) {
            System.out.println("\n---There is such a connection---\n");
        }
    }


    public void removeStudentFromOneOfHisOrHerCourses() {

        System.out.println("Enter student ID");
        String studentId = scanner.nextLine();

        while (true) {
            if (cheakCorrectnessNumber(studentId)) {
                if (!studentService.existsById(Integer.valueOf(studentId))) {
                    System.out.println("\n---This ID doesn't exist\n");
                } else break;
            }
            System.out.println("Enter student ID");
            studentId = scanner.nextLine();
        }

        List<Integer> coursesOfOneStudent = new ArrayList<>();
        coursesOfOneStudent = studentService.getListCourseOfOneStudentById(Integer.valueOf(studentId));

        System.out.println("\nThis student has courses:");
        for (int x : coursesOfOneStudent) {
            Course course = courseService.findById(x);
            System.out.println(course.getCourseId() + ") " + course.getCourseName() + " (" + course.getCourseDescription() + ");");
        }

        System.out.println("\nSelect course ID from the list and enter course ID\n");
        String courseId = scanner.nextLine();

        while (true) {
            if (cheakCorrectnessNumber(courseId)) {
                if (!coursesOfOneStudent.contains(Integer.valueOf(courseId))) {
                    System.out.println("---This ID doesn't exist\n");
                } else break;
            }
            System.out.println("Enter course ID");
            courseId = scanner.nextLine();
        }
        studentService.removeOneCourseOfOneStudent(Integer.valueOf(studentId), Integer.valueOf(courseId));
        System.out.println("\n---Course removed\n");
    }


    private boolean checkCorrectnessNameAndLastName(String value) {
        if (!value.matches("^[a-zA-Z]+$") || value.length() > 50) {
            System.out.println("---Incorrect value - \"" + value + "\"\n");
            return false;
        } else
            return true;
    }

    private boolean cheakCorrectnessNumber(String value) {
        if (!value.matches("^[0-9]+$")) {
            System.out.println("---Invalid value - \"" + value + "\"\n");
            return false;
        }
        return true;
    }
}
