package ua.com.foxminded.university;

import java.sql.Connection;

public class Menu {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.printMenu();
    }

    private void printMenu () {
        String menu = "Please make a selection\n" +
                "a. Find all groups with less or equals student count\n" +
                "b. Find all students related to course with given name\n" +
                "c. Add new student\n" +
                "d. Delete student by STUDENT_ID\n" +
                "e. Add a student to the course (from a list)\n" +
                "f. Remove the student from one of his or her courses";
        System.out.println(menu);



    }





}
