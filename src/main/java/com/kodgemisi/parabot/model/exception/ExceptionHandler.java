package com.kodgemisi.parabot.model.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sedat on 24.07.2015.
 */

@ControllerAdvice
public class ExceptionHandler {

    @Autowired
    private MessageHelper messageHelper;

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
    public ErrorMessageJSON handleNullPointerException(NullPointerException e) {
        return new ErrorMessageJSON(true, e.toString(), messageHelper.getCode(e), messageHelper.getMessage(e));
    }

}
