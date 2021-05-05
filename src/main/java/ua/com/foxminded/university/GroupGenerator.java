package ua.com.foxminded.university;


import ua.com.foxminded.university.entities.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ua.com.foxminded.university.Constants.SYMBOLS;

public class GroupGenerator {
    private final Random randomNumber;

    public GroupGenerator(Random random) {
        randomNumber = random;
    }

    public List<Group> getGeneratedGroup(int numberOfGroup) {
        List<Group> groupList = new ArrayList<>();

        for (int i = 0; i < numberOfGroup; i++) {
            groupList.add(new Group(getRandomSymbols(2) + "-" + getRandomNumbers(2)));
        }
        return groupList;
    }

    private String getRandomSymbols(int numberOfSymbols) {
        String randomSymbols = new String();
        for (int i = 0; i < numberOfSymbols; i++) {
            randomSymbols += String.valueOf(SYMBOLS.charAt(randomNumber.nextInt(SYMBOLS.length())));
        }
        return randomSymbols;
    }

    private String getRandomNumbers(int numberOfumbers) {
        String randomNumbers = new String();
        for (int i = 0; i < numberOfumbers; i++) {
            randomNumbers += randomNumber.nextInt(10);
        }
        return randomNumbers;
    }
}
