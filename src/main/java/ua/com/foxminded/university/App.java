package ua.com.foxminded.university;

import ua.com.foxminded.university.dao.CourseDAO;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.StudentCourseDAO;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentCourseServise;
import ua.com.foxminded.university.service.StudentService;


import java.io.IOException;
import java.sql.Connection;
import java.util.*;


public class App {

    public static void main(String[] args) throws IOException {

        int numberOfStudents = 200;
        int numberOfGroup = 10;

        Connection connection = DatabaseConnector.getConnection();


//        есть сомнения по чтению скриптов
//        я сделал такой класс, кот. читает один запрос (нужно мой файл со всеми запросами раздробить
//        на меньшие файлы с единственным запросом), но что-то мне подсказывает, что это не норм идея.
//
//        String pathSqlFileCreateTables = "src/main/resources/createTableStudentgroup.sql";
//        ScriptManager scriptManager =new ScriptManager(connection);
//        scriptManager.executeScript(pathSqlFileCreateTables);

        StudentDAO studentDAO = new StudentDAO(connection);
        StudentService studentService = new StudentService(studentDAO);
        GroupDAO groupDAO = new GroupDAO(connection);
        GroupService groupService = new GroupService(groupDAO);
        CourseDAO courseDAO = new CourseDAO(connection);
        CourseService courseService = new CourseService(courseDAO);

        Random random = new Random();

        GroupGenerator groupGenerator = new GroupGenerator();
        List<Group> groupList = groupGenerator.getGeneratedGroup(numberOfGroup);
        groupList.forEach(group -> groupService.save(group));

        StudentGenerator studentGenerator = new StudentGenerator();
        List<Student> studentList = studentGenerator.getStudents(numberOfStudents);
        studentList.forEach(student -> {
            int randomNumber = random.nextInt(numberOfGroup+2);
            if (randomNumber != numberOfGroup + 1) {
                student.setGroup_id(groupList.get(random.nextInt(numberOfGroup)).getId());
            }
            studentService.save(student);
        });

        CourseCreator courseCreator = new CourseCreator();
        List<Course> courseList = courseCreator.getCourseList();
        courseList.forEach(course -> courseService.save(course));

        StudentCourseDAO studentCourseDAO = new StudentCourseDAO(connection);
        StudentCourseServise studentCourseServise = new StudentCourseServise(studentCourseDAO);

 //        распределить на курсы


        int numberOfCourse = (int) courseService.count();
        int i = 1;
        Set<Integer> s = new HashSet<>();

        while (i <= numberOfStudents ) {
            int courseOneStudent = random.nextInt(3)+1;
            for (int j = 1; j <= courseOneStudent; j++) {
                while (true) {
                    int numberCoursesOneStudent = random.nextInt(numberOfCourse)+1;
                    if (s.contains(numberCoursesOneStudent) == false) {
                        s.add(numberCoursesOneStudent);
                        studentCourseServise.save(i, numberCoursesOneStudent);
                        break;
                    }
                }
            }
            s.clear();
            i++;
        }

    }
}
