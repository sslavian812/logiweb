package ru.tsystems.shalamov.ws;

import java.io.Serializable;

/**
 * Created by viacheslav on 11.08.2015.
 */
public class FaultBean implements Serializable {
    private String message;

    public FaultBean() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}