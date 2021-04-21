package ua.com.foxminded.university;


import java.util.Random;

import static ua.com.foxminded.university.Constans.*;

public class GroupGenerator {

    public String getGeneratedGroup() {
        String generatedGroup = getRandomSymbols(2) + "-" + getRandomNumbers(2);
        return generatedGroup;
    }

    private String getRandomSymbols(int numberOfSymbols) {
        checkForExceptions(numberOfSymbols);

        Random randomNumber = new Random();
        String randomSymbols = new String();
        for (int i = 0; i < numberOfSymbols; i++) {
            randomSymbols += String.valueOf(SYMBOLS.charAt(randomNumber.nextInt(SYMBOLS.length())));
        }
        return randomSymbols;
    }

    private String getRandomNumbers(int numberOfumbers) {
        checkForExceptions(numberOfumbers);

        Random randomNumber = new Random();
        String randomNumbers = new String();
        for (int i = 0; i < numberOfumbers; i++) {
            randomNumbers += randomNumber.nextInt(10);
        }
        return randomNumbers;
    }

    private void checkForExceptions(int numberForCheck) {
        if (numberForCheck <= 0) {
            throw new IllegalArgumentException("Number of random values must not be less than or equal to zero");
        }
    }
}
