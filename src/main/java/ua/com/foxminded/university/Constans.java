package ua.com.foxminded.university;

public class Constans {
    public static final String USER = "postgres";
    public static final String PASSWORD = "1234";
    public static final String URL = "jdbc:postgresql://localhost:5432/university";

    public static final String SYMBOLS = "abcdefghijklmnopqrstuvwxyz";
    public static final int NUMBER_OF_STUDENTS = 200;
    public static final int NUMBER_OF_GROUP = 10;
    public static final String PATH_SQL_FILE_CREATE_TABLES = "src/main/resources/createTables.sql";
    public static final String MENU = "Please make a selection\n" +
            "a. Find all groups with less or equals student count\n" +
            "b. Find all students related to course with given name\n" +
            "c. Add new student\n" +
            "d. Delete student by STUDENT_ID\n" +
            "e. Add a student to the course (from a list)\n" +
            "f. Remove the student from one of his or her courses\n" +
            "g. Exit";
    public static final String NUMBERS = "0123456789";
}
