package com.kodgemisi.parabot.model.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

/**
 * Created by sedat on 24.07.2015.
 */

@ControllerAdvice
public class ExceptionHandler {

    @Autowired
    private MessageHelper messageHelper;

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler({
        NullPointerException.class, ConstraintViolationException.class,
        NoSuchElementException.class, IllegalArgumentException.class
    })
    public ErrorMessageJSON handleExceptions(Exception e) {
        e.printStackTrace();
        return new ErrorMessageJSON(true, e.toString(), messageHelper.getCode(e), messageHelper.getMessage(e));
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorDTO handleFieldErrors(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError error: bindingResult.getFieldErrors()) {
            dto.addFieldError(error.getField(), error.getDefaultMessage());
        }

        return dto;
    }
}
