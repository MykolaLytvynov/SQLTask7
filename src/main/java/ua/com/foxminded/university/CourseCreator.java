package ua.com.foxminded.university;

import ua.com.foxminded.university.entities.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseCreator {

    Course biology = new Course("Biology", "What is biology");
    Course mathematics = new Course("Mathematics", "What is mathematics");
    Course english = new Course("English", "What is english");
    Course theoreticalMechanics = new Course("Theoretical Mechanics", "What is theoretical Mechanics");
    Course computerScience = new Course("Computer Science", "What is computer Science");
    Course physics = new Course("Physics", "What is physics");
    Course management = new Course("Management", "What is management");
    Course history = new Course("History", "What is history");
    Course astrology = new Course("Astrology", "What is astrology");
    Course economy = new Course("Economy", "What is economy");


    public List<Course> getCourseList () {
        List<Course> courses = new ArrayList<>();
        courses.add(biology);
        courses.add(mathematics);
        courses.add(english);
        courses.add(theoreticalMechanics);
        courses.add(computerScience);
        courses.add(physics);
        courses.add(management);
        courses.add(history);
        courses.add(astrology);
        courses.add(economy);

        return courses;
    }


}
