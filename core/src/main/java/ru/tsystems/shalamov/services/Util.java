package ru.tsystems.shalamov.services;

import java.util.UUID;

/**
 * Created by viacheslav on 05.08.2015.
 */
public class Util {

    private Util()
    {}

    public static String generateRandomIdWithTime() {
        UUID uuid = UUID.randomUUID();
        return (new UUID(uuid.getMostSignificantBits(), System.currentTimeMillis())).toString();
    }

    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Private string constant sed to display errors.
     */
    public static final String UNEXPECTED = "Unexpected: ";
}
