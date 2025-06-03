package com.foodcourt.court.domain.utilities;

import com.foodcourt.court.domain.constants.Constants;

import java.util.Random;

public class Utilities {

    private Utilities() {
        throw new IllegalStateException(Constants.ERROR_INSTANCE_UTILITY_CLASS);
    }

    private static final Random random =  new Random();

    public static String generateRandomPin() {
        StringBuilder pinGenerated = new StringBuilder();
        for (int i = 0; i < Constants.DIGIT_NUMBER_CLIENT_PIN; i++){
            pinGenerated.append(random.nextInt(Constants.MAX_RANGE_CLIENT_PIN));
        }
        return pinGenerated.toString();
    }

}
