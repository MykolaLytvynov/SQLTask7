package ua.com.foxminded.university;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.entities.Group;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GroupGeneratorTest {
    Random random = new Random();
    GroupGenerator objectForTest = new GroupGenerator(random);

    @Test
    @DisplayName("5 Groups")
    void getGeneratedGroupShouldReturnListWith5GroupsWhenEnter5Groups() {
        int expected = 5;

        int result = objectForTest.getGeneratedGroup(5).size();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Empty List")
    void getGeneratedGroupShouldReturnEmptyListWhenEnter5Groups() {
        int expected = 0;

        int result = objectForTest.getGeneratedGroup(0).size();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Validation of groups")
    void getGeneratedGroupShouldReturnListWithCorrectGroups() {
        String RegexForGroups = "^[a-z]{2}[-][0-9]{2}$";
        boolean expected = true;
        boolean result = true;

        List<Group> groupList = objectForTest.getGeneratedGroup(5);
        for (Group group : groupList) {
            if (!group.getGroup().matches(RegexForGroups) || group.getId() != 0) {
                result = false;
            }
        }

        assertEquals(expected, result);
    }
}