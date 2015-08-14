package ru.tsystems.shalamov.services;

import java.util.UUID;

/**
 * Created by viacheslav on 05.08.2015.
 */
public class Util {
    public static String generateCargoIdentifier(String orderIdentifeier, String denomination, int weignt) {
        // todo implement some hash like sha256 here
        // UUID
        UUID uuid;
        return orderIdentifeier + denomination.replaceAll("\\s+","") + weignt;
    }

    /**
     * Private string constant sed to display errors.
     */
    public static final String UNEXPECTED = "Unexpected: ";
}
