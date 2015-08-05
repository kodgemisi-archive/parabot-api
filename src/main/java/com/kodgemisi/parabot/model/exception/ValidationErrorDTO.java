package com.kodgemisi.parabot.model.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sedat on 05.08.2015.
 */

public class ValidationErrorDTO {
    public List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public void addFieldError(String field, String message) {
        FieldErrorDTO error = new FieldErrorDTO(field, message);
        fieldErrors.add(error);
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }
}
