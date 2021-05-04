package ua.com.foxminded.university;

import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static ua.com.foxminded.university.Constans.*;


public class DataInitializer {
    private final GroupService groupService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final Connection connection;
    Random random = new Random();

    public DataInitializer(GroupService groupService, StudentService studentService, CourseService courseService, Connection connection) {
        this.groupService = groupService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.connection = connection;
    }

    public void initDb() throws IOException {
        ScriptManager scriptManager = new ScriptManager(connection);

        scriptManager.executeScript(PATH_SQL_FILE_CREATE_TABLES);
    }

    public List<Group> generateGroups(int numberOfGroups) {
        GroupGenerator groupGenerator = new GroupGenerator();
        List<Group> groupList = groupGenerator.getGeneratedGroup(numberOfGroups);
        return groupList;
    }

    public List<Student> generateStudents(int numberOfStudents) {
        StudentGenerator studentGenerator = new StudentGenerator();
        List<Student> studentList = studentGenerator.getStudents(numberOfStudents);
        return studentList;
    }

    public List<Course> createCourses() {
        CourseCreator courseCreator = new CourseCreator();
        return courseCreator.getCourseList();
    }

    public void addStudentsToGroups(List<Group> groupList, List<Student> studentList) {
        studentList.forEach(student -> {
            int randomNumber = random.nextInt(groupList.size() + 2);
            if (randomNumber != groupList.size() + 1) {
                student.setGroupId(groupList.get(random.nextInt(groupList.size())).getId());
            }
        });
    }


    public void saveGroupsInDb(List<Group> groupList) {
        groupList.forEach(group -> groupService.save(group));
        groupList = groupService.findAll();
    }

    public void saveStudentsInDb(List<Student> studentList) {
        studentList.forEach(student -> studentService.save(student));
    }

    public void saveCoursesInDb(List<Course> courseList) {
        courseList.forEach(course -> courseService.save(course));
    }

    public void asignStudentsToCourses(List<Student> studentList) {
        int numberOfCourse = (int) courseService.count();
        int i = 1;
        Set<Integer> s = new HashSet<>();

        while (i <= studentList.size()) {
            int courseOneStudent = random.nextInt(3) + 1;
            for (int j = 1; j <= courseOneStudent; j++) {
                while (true) {
                    int numberCoursesOneStudent = random.nextInt(numberOfCourse) + 1;
                    if (s.contains(numberCoursesOneStudent) == false) {
                        s.add(numberCoursesOneStudent);
                        studentService.asignStudentOnCourse(i, numberCoursesOneStudent);
                        break;
                    }
                }
            }
            s.clear();
            i++;
        }
    }
}
