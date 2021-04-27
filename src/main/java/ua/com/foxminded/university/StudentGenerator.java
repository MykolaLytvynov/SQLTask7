package ua.com.foxminded.university;

import ua.com.foxminded.university.entities.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentGenerator {

    private String[] names = new String[]{"Viktor", "Nika", "Robert", "Nick", "Kleo",
            "Lena", "Nina", "Vova", "Boris", "Igor",
            "Kesha", "Kolya", "Stanislav", "Yury", "Alla",
            "Varvara", "Katya", "Yakov", "Roman", "Stepan"};

    private String[] lastNames = new String[]{"Adamson", "Austin", "Backer", "Benson", "Birch",
            "Ford", "Stevenson", "Hodges", "Gustman", "Goldman",
            "Taylor", "Peacock", "Stanley", "Foster", "Wood",
            "Kohl", "Samuels", "Owen", "Hardman", "Young"};


    public List<Student> getStudents(int numberOfStudents) {
        List<Student> studentList = new ArrayList<>();
        Random randomNumber = new Random();

        for (int i = 0; i < numberOfStudents; i++) {
            studentList.add(new Student(names[randomNumber.nextInt(names.length)],
                    lastNames[randomNumber.nextInt(lastNames.length)]));
        }
        return studentList;
    }
}
