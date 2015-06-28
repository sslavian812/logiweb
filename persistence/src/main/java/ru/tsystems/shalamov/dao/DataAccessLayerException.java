package ru.tsystems.shalamov.dao;

/**
 * Represents Exceptions thrown by the Data Access Layer.
 * <p/>
 * Created by viacheslav on 28.06.2015.
 */
public class DataAccessLayerException extends RuntimeException {
    public DataAccessLayerException() {
    }

    public DataAccessLayerException(String message) {
        super(message);
    }

    public DataAccessLayerException(Throwable cause) {
        super(cause);
    }

    public DataAccessLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
