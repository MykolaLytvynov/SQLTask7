package ua.com.foxminded.university;

import ua.com.foxminded.university.entities.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentGenerator {

    private String name1 = "Viktor", name2 = "Nika", name3 = "Robert", name4 = "Nick", name5 = "Robert",
            name6 = "Lena", name7 = "Nina", name8 = "Vova", name9 = "Boris", name10 = "Igor",
            name11 = "Kesha", name12 = "Kolya", name13 = "Stanislav", name14 = "Yury", name15 = "Alla",
            name16 = "Varvara", name17 = "Katya", name18 = "Yakov", name19 = "Roman", name20 = "Stepan";
    private String lastName1 = "Adamson", lastName2 = "Austin", lastName3 = "Backer", lastName4 = "Benson", lastName5 = "Birch",
            lastName6 = "Ford", lastName7 = "Hodges", lastName8 = "Gustman", lastName9 = "Goldman", lastName10 = "Taylor",
            lastName11 = "Stanley", lastName12 = "Foster", lastName13 = "Wood", lastName14 = "Francis", lastName15 = "Samuels",
            lastName16 = "Owen", lastName17 = "Hardman", lastName18 = "Young", lastName19 = "Stevenson", lastName20 = "Peacock";

    public List<Student> getStudents(int numberOfStudents) {
        List<Student> studentList = new ArrayList<>();
        Random randomNumber = new Random();

        for (int i = 0; i < numberOfStudents; i++) {
            studentList.add(new Student(getNamesList().get(randomNumber.nextInt(getNamesList().size())),
                    getLastNamesList().get(randomNumber.nextInt(getLastNamesList().size()))));
        }
        return studentList;
    }


    private List<String> getNamesList() {

        List<String> namesList = new ArrayList<>();

        namesList.add(name1);
        namesList.add(name2);
        namesList.add(name3);
        namesList.add(name4);
        namesList.add(name5);
        namesList.add(name6);
        namesList.add(name7);
        namesList.add(name8);
        namesList.add(name9);
        namesList.add(name10);
        namesList.add(name11);
        namesList.add(name12);
        namesList.add(name13);
        namesList.add(name14);
        namesList.add(name15);
        namesList.add(name16);
        namesList.add(name17);
        namesList.add(name18);
        namesList.add(name19);
        namesList.add(name20);

        return namesList;
    }

    private List<String> getLastNamesList() {

        List<String> lastNamesList = new ArrayList<>();

        lastNamesList.add(lastName1);
        lastNamesList.add(lastName2);
        lastNamesList.add(lastName3);
        lastNamesList.add(lastName4);
        lastNamesList.add(lastName5);
        lastNamesList.add(lastName6);
        lastNamesList.add(lastName7);
        lastNamesList.add(lastName8);
        lastNamesList.add(lastName9);
        lastNamesList.add(lastName10);
        lastNamesList.add(lastName11);
        lastNamesList.add(lastName12);
        lastNamesList.add(lastName13);
        lastNamesList.add(lastName14);
        lastNamesList.add(lastName15);
        lastNamesList.add(lastName16);
        lastNamesList.add(lastName17);
        lastNamesList.add(lastName18);
        lastNamesList.add(lastName19);
        lastNamesList.add(lastName20);

        return lastNamesList;
    }

    private void checkForExceptions(int numberForCheck) {
        if (numberForCheck <= 0) {
            throw new IllegalArgumentException("Number of students must not be less than or equal to zero");
        }
    }

}
