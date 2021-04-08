package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.DatabaseConnector;

import java.sql.Connection;

public interface CrudOperations <T> {

    static final Connection connection = DatabaseConnector.getConnection();

    // стараюсь еще разобраться с этим
    void add(T entity);
    void removeById (int id);

}
