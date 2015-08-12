package ru.tsystems.shalamov.services;

/**
 * Exception, which is thrown on services layer to indicate some failures.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class ServiceLayerException extends Exception {
    ServiceLayerException() {
    }

    public ServiceLayerException(String message) {
        super(message);
    }

    public ServiceLayerException(Throwable cause) {
        super(cause);
    }

    public ServiceLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
