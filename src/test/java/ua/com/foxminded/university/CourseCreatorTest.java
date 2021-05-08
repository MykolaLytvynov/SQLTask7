package ua.com.foxminded.university;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Course;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseCreatorTest {
    CourseCreator objectForTest = new CourseCreator();

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

    @Test
    @DisplayName("List of courses should be received")

    void getCourseListShouldGetListOfCourses () {
        List<Course> expected = new ArrayList<>();
        expected.add(biology);
        expected.add(mathematics);
        expected.add(english);
        expected.add(theoreticalMechanics);
        expected.add(computerScience);
        expected.add(physics);
        expected.add(management);
        expected.add(history);
        expected.add(astrology);
        expected.add(economy);

        List<Course> result = objectForTest.getCourseList();

        assertEquals(expected, result);
    }
}