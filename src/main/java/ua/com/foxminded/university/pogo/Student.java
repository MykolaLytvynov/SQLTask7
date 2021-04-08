package ua.com.foxminded.university.pogo;

public class Student {
    private int studentId;
    private int groupId;
    private String firstName;
    private String lastName;

    public Student(int studentId, int groupId, String firstName, String lastName) {
        this.studentId = studentId;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
