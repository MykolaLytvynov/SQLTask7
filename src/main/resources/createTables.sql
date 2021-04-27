DROP TABLE IF EXISTS students, studentgroup, courses, students_courses;

CREATE TABLE studentgroup (
    group_id SERIAL PRIMARY KEY,
    group_name character(5) NOT NULL UNIQUE
);
CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    group_id int,
    first_name character (50) NOT NULL,
    last_name character (50) NOT NULL,
    FOREIGN KEY (group_id) REFERENCES studentgroup (group_id) ON DELETE SET NULL
);
CREATE TABLE courses (
    course_id SERIAL PRIMARY KEY,
    course_name character (25) NOT NULL,
    course_description character (100) NOT NULL
);

CREATE TABLE students_courses  (
student_id int NOT NULL,
course_id int NOT NULL,
PRIMARY KEY (student_id, course_id),
FOREIGN KEY (student_id) REFERENCES students (student_id) ON DELETE CASCADE,
FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE CASCADE
);