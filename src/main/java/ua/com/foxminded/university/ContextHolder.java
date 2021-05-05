package ua.com.foxminded.university;

import ua.com.foxminded.university.dao.CourseDAO;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.service.CourseService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.StudentService;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;
import java.util.Random;

import static ua.com.foxminded.university.Constants.PROPERTY_SOURCE;

public final class ContextHolder {
    private static final String ERR_MESSAGE = "Couldn't load property '%s', cause: %s";
    private static final Properties properties = new Properties();

    private final Menu menu;
    private final Integer numberOfGroup;
    private final GroupDAO groupsDao;
    private final Integer numberOfStudents;
    private final CourseDAO coursesDao;
    private final StudentDAO studentsDao;
    private final GroupService groupService;
    private final CourseService courseService;
    private final ScriptManager scriptManager;
    private final StudentService studentService;
    private final DataInitializer dataInitializer;
    private final DatabaseConnector databaseConnector;


    public ContextHolder(DatabaseConnector databaseConnector, ScriptManager scriptManager, DataInitializer dataGenerator,
                         Menu menu, StudentDAO studentDao, GroupDAO groupDao, CourseDAO courseDao, StudentService studentService,
                         GroupService groupService, CourseService courseService, Integer numberOfGroup, Integer numberOfStudents) {
        this.menu = menu;
        this.groupsDao = groupDao;
        this.coursesDao = courseDao;
        this.studentsDao = studentDao;
        this.groupService = groupService;
        this.courseService = courseService;
        this.scriptManager = scriptManager;
        this.studentService = studentService;
        this.dataInitializer = dataGenerator;
        this.databaseConnector = databaseConnector;
        this.numberOfGroup = numberOfGroup;
        this.numberOfStudents = numberOfStudents;
    }

    public static ContextHolder connectorBuilder(DatabaseConnector.SourceType sourceType) {
        String prefix = sourceType.toString().toLowerCase();
        String url = loadProperty(prefix + ".url");
        String login = loadProperty(prefix + ".login");
        String password = loadProperty(prefix + ".password");

        DatabaseConnector databaseConnector = new DatabaseConnector(url, login, password);
        Connection connection = databaseConnector.getConnection();

        Random random = new Random(777);
        CourseCreator courseCreator = new CourseCreator();
        StudentGenerator studentGenerator = new StudentGenerator();
        GroupGenerator groupGenerator = new GroupGenerator(random);
        StudentDAO studentDao = new StudentDAO(connection);
        GroupDAO groupDao = new GroupDAO(connection);
        CourseDAO courseDao = new CourseDAO(connection);
        StudentService studentServiceBean = new StudentService(studentDao);
        GroupService groupServiceBean = new GroupService(groupDao);
        CourseService courseServiceBean = new CourseService(courseDao);
        Menu menuBean = new Menu(studentServiceBean, groupServiceBean, courseServiceBean);
        ScriptManager scriptManagerBean = new ScriptManager(connection);
        DataInitializer dataGeneratorBean = new DataInitializer(random, scriptManagerBean, studentServiceBean, studentGenerator, courseServiceBean, courseCreator, groupGenerator, groupServiceBean);

        return new ContextHolder(databaseConnector, scriptManagerBean, dataGeneratorBean, menuBean, studentDao,
                groupDao, courseDao, studentServiceBean, groupServiceBean, courseServiceBean,
                Integer.valueOf(loadProperty("groups-count")), Integer.valueOf(loadProperty("students-count")));
    }

    public Menu getMenu() {
        return menu;
    }

    public GroupDAO getGroupsDao() {
        return groupsDao;
    }

    public CourseDAO getCoursesDao() {
        return coursesDao;
    }

    public StudentDAO getStudentsDao() {
        return studentsDao;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public ScriptManager getScriptManager() {
        return scriptManager;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public DataInitializer getDataInitializer() {
        return dataInitializer;
    }

    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

    public Integer getNumberOfGroup() {
        return numberOfGroup;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    private static String loadProperty(String propertyName) {
        try (FileInputStream fis = new FileInputStream(PROPERTY_SOURCE)) {
            properties.load(fis);
            return properties.getProperty(propertyName);
        } catch (Exception e) {
            throw new RuntimeException(String.format(ERR_MESSAGE, propertyName, e.getLocalizedMessage()));
        }
    }
}
