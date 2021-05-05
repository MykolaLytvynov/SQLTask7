package ua.com.foxminded.university;


import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

import java.util.Locale;
import java.util.Scanner;


import static ua.com.foxminded.university.Constants.*;

public class Menu {

    private StudentService studentService;
    private GroupService groupService;
    private CourseService courseService;

    public Menu(StudentService studentService, GroupService groupService, CourseService courseService) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.courseService = courseService;
    }

    public void printMenu() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(MENU);
            System.out.println("\nChoose from these choices");
            String input = scanner.nextLine();
            if (input.length() == 1 && input.toLowerCase(Locale.ROOT).contains("g")) {
                break;
            } else selectionCheck(input);
        }

        System.out.println("\n---Program is over---");
    }

    private void selectionCheck(String choice) {
        MenuController menuController = new MenuController(studentService, groupService, courseService);

        switch (choice.toLowerCase(Locale.ROOT)) {
            case "a":
                menuController.findGroupsWithLessOrEqualsStudent();
                break;

            case "b":
                menuController.findStudentsRelatedToCourse();
                break;

            case "c":
                menuController.addNewStudent();
                break;

            case "d":
                menuController.deleteStudentByStudentId();
                break;

            case "e":
                menuController.addStudentToCourse();
                break;

            case "f":
                menuController.removeStudentFromOneOfHisOrHerCourses();
                break;

            default:
                System.out.println("\n---Incorrect choice---\n");
                break;
        }

    }

}
