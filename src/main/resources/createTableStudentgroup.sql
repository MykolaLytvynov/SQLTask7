CREATE TABLE studentgroup (
    group_id SERIAL PRIMARY KEY,
    group_name character(5) NOT NULL UNIQUE
)