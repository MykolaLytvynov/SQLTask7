package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.DatabaseConnector;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface CrudOperations <T, ID> {


    T save(T entity);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    List<T> findAll();

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAll();

}
