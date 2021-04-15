CREATE TABLE studentgroup (
    group_id SERIAL PRIMARY KEY,
    group_name character(5) NOT NULL UNIQUE
);
CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    group_id character(5),
    first_name character (50) NOT NULL,
    last_name character (50) NOT NULL
);
CREATE TABLE courses (
    course_id SERIAL PRIMARY KEY,
    course_name character (100) NOT NULL,
    course_description character (250) NOT NULL
);
