package ua.com.foxminded.university;

import ua.com.foxminded.university.entities.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseCreator {

    private Course biology = new Course("Biology", "What is biology");
    private Course mathematics = new Course("Mathematics", "What is mathematics");
    private Course english = new Course("English", "What is english");
    private Course theoreticalMechanics = new Course("Theoretical Mechanics", "What is theoretical Mechanics");
    private Course computerScience = new Course("Computer Science", "What is computer Science");
    private Course physics = new Course("Physics", "What is physics");
    private Course management = new Course("Management", "What is management");
    private Course history = new Course("History", "What is history");
    private Course astrology = new Course("Astrology", "What is astrology");
    private Course economy = new Course("Economy", "What is economy");


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
