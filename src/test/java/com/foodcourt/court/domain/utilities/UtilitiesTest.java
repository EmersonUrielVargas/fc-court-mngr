package com.foodcourt.court.domain.utilities;

import com.foodcourt.court.domain.constants.Constants;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {

    @Test
    void privateConstructor_shouldThrowIllegalStateException() throws Exception {
        Constructor<Utilities> constructor = Utilities.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException thrownException = assertThrows(InvocationTargetException.class,
                constructor::newInstance
        );

        Throwable cause = thrownException.getCause();
        assertNotNull(cause);
        assertInstanceOf(IllegalStateException.class, cause);
        assertEquals(Constants.ERROR_INSTANCE_UTILITY_CLASS, cause.getMessage());
    }

    @Test
    void generateRandomPin_shouldReturnPinWithCorrectLength() {
        String pin = Utilities.generateRandomPin();

        assertNotNull(pin);
        assertEquals(Constants.DIGIT_NUMBER_CLIENT_PIN, pin.length());
    }
}