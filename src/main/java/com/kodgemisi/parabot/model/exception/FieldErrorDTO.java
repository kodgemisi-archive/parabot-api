package com.kodgemisi.parabot.model.exception;

/**
 * Created by sedat on 05.08.2015.
 */

public class FieldErrorDTO {
    public String field;
    public String message;

    public FieldErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
