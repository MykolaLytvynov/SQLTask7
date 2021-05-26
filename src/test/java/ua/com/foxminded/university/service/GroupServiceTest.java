package ua.com.foxminded.university.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class GroupServiceTest {

    @Mock
    private GroupDAO groupDAO;

    private GroupService groupService;

    public GroupServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.groupService = new GroupService(groupDAO);
    }


    @Test
    @DisplayName("Call to method SAVE in Dao and return Group")
    void saveShouldCallToMethodSaveInDaoAndReturnGroup() {

        Group expected = new Group(1, "qw-12");

        given(groupDAO.save(expected)).willReturn(expected);

        Group result = groupService.save(expected);

        assertEquals(expected, result);
        verify(groupDAO).save(expected);
    }

    @Test
    @DisplayName("Call to method SAVE in Dao and return Exception when pass Null")
    void saveShouldReturnExceptionWhenPassNull() {

        given(groupDAO.save(null)).willThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> groupService.save(null));
        verify(groupDAO).save(null);
    }

    @Test
    @DisplayName("Call to method findById in Dao and return Course")
    void findByIdShouldCallToMethodFindByIdInDaoAndReturnCourse() {

        Group expected = new Group(1, "qw-12");

        given(groupDAO.findById(1)).willReturn(Optional.of(expected));

        Group result = groupService.findById(1);

        assertEquals(expected, result);
        verify(groupDAO).findById(1);
    }

    @Test
    @DisplayName("Call to method findById in Dao and return message when pass does not exist id")
    void findByIdShouldReturnMessageWhenPassDoesNotExistId() {

        given(groupDAO.findById(12)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> groupService.findById(12));
        verify(groupDAO).findById(12);
    }

    @Test
    @DisplayName("Call to method existsById in Dao and return TRUE when pass exist id")
    void existsByIdShouldReturnTrueWhenPassExistId() {

        boolean expected = true;

        given(groupDAO.existsById(1)).willReturn(true);

        boolean result = groupService.existsById(1);

        assertEquals(expected, result);
        verify(groupDAO).existsById(1);
    }

    @Test
    @DisplayName("Call to method existsById in Dao and return FALSE when pass does not exist id")
    void existsByIdShouldReturnFalseWhenPassDoesNotExistId() {

        boolean expected = false;

        given(groupDAO.existsById(1)).willReturn(false);

        boolean result = groupService.existsById(1);

        assertEquals(expected, result);
        verify(groupDAO).existsById(1);
    }

    @Test
    @DisplayName("Call to method findAll in Dao and return List all groups")
    void findAllShouldReturnListAllGroups() {

        List<Group> expected = new ArrayList<>();
        expected.add(new Group(1, "qw-12"));

        given(groupDAO.findAll()).willReturn(expected);

        List<Group> result = groupService.findAll();

        assertEquals(expected, result);
        verify(groupDAO).findAll();
    }

    @Test
    @DisplayName("Call to method count in Dao and return count all groups")
    void countShouldReturnCountAllGroups() {

        long expected = 1;

        given(groupDAO.count()).willReturn(1L);

        long result = groupService.count();

        assertEquals(expected, result);
        verify(groupDAO).count();
    }

    @Test
    @DisplayName("Call to method deleteById in Dao")
    void deleteByIdShouldCallToMethodDeleteByIdInDao() {

        groupService.deleteById(1);

        verify(groupDAO).deleteById(1);
    }

    @Test
    @DisplayName("Call to method delete in Dao")
    void deleteShouldCallToMethodDeleteInDao() {

        Group group = new Group(1, "qw-12");

        groupService.delete(group);
        verify(groupDAO).delete(group);
    }

    @Test
    @DisplayName("Call to method deleteAll in Dao")
    void deleteAllShouldCallToMethodDeleteAllInDao() {

        groupService.deleteAll();
        verify(groupDAO).deleteAll();
    }
}