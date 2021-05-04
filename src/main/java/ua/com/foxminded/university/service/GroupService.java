package ua.com.foxminded.university.service;

import ua.com.foxminded.university.dao.CrudOperations;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;


public class GroupService {

    private final GroupDAO groupDAO;

    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public Group save(Group group) {
        return groupDAO.save(group);
    }

    public Group findById(Integer idGroup) {
        return groupDAO.findById(idGroup).orElseThrow(() -> new NotFoundException("Group not found by id=" + idGroup));
    }

    public boolean existsById (Integer idGroup) {
        return groupDAO.existsById(idGroup);
    }

    public List<Group> findAll() {
        return groupDAO.findAll();
    }

    public long count() {
        return groupDAO.count();
    }

    public void deleteById(Integer idGroup) {
        groupDAO.deleteById(idGroup);
    }

    public void delete(Group group) {
        groupDAO.delete(group);
    }

    public void deleteAll() {
        groupDAO.deleteAll();
    }
}

