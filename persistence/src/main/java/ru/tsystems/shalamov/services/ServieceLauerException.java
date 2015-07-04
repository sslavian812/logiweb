package ru.tsystems.shalamov.services;

/**
 * Exception, which is thrown on services layer to indicate some failures.
 * <p>
 * Created by viacheslav on 28.06.2015.
 */
public class ServieceLauerException extends Exception {
    ServieceLauerException() {
    }

    public ServieceLauerException(String message) {
        super(message);
    }

    ServieceLauerException(Throwable cause) {
        super(cause);
    }

    ServieceLauerException(String message, Throwable cause) {
        super(message, cause);
    }
}
