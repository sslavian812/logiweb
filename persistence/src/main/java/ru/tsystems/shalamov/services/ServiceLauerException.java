package ru.tsystems.shalamov.services;

/**
 * Exception, which is thrown on services layer to indicate some failures.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class ServiceLauerException extends Exception {
    ServiceLauerException() {
    }

    public ServiceLauerException(String message) {
        super(message);
    }

    public ServiceLauerException(Throwable cause) {
        super(cause);
    }

    public ServiceLauerException(String message, Throwable cause) {
        super(message, cause);
    }
}
