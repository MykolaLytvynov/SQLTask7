package ua.com.foxminded.university;

final class Constants {
    private Constants() {
    }

    public static final String NUMBERS = "0123456789";
    public static final String PROPERTY_SOURCE = "src/main/resources/application.properties";
    public static final String SYMBOLS = "abcdefghijklmnopqrstuvwxyz";
    public static final String PATH_SQL_FILE_CREATE_TABLES = "src/main/resources/createTables.sql";
    public static final String MENU = "Please make a selection\n" +
            "a. Find all groups with less or equals student count\n" +
            "b. Find all students related to course with given name\n" +
            "c. Add new student\n" +
            "d. Delete student by STUDENT_ID\n" +
            "e. Add a student to the course (from a list)\n" +
            "f. Remove the student from one of his or her courses\n" +
            "g. Exit";
}
