package ru.tsystems.shalamov.servicesAPI;

/**
 * Exception, which is thrown on services layer to indicate some failures.
 *
 * Created by viacheslav on 28.06.2015.
 */
public class LogiwebManagerException extends Exception {
    public LogiwebManagerException() {
    }

    public LogiwebManagerException(String message) {
        super(message);
    }

    public LogiwebManagerException(Throwable cause) {
        super(cause);
    }

    public LogiwebManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
