package ru.tsystems.shalamov.services;

/**
 * Created by viacheslav on 05.08.2015.
 */
public class Util {
    public static String generateCargoIdentifier(String orderIdentifeier, String denomination, int weignt)
    {
        // todo implement some hash like sha256 here
        return orderIdentifeier+denomination+weignt;
    }

    /**
     * Private string constant sed to display errors.
     */
    public static final String UNEXPECTED = "Unexpected: ";
}
