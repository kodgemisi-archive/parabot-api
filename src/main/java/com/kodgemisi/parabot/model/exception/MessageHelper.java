package com.kodgemisi.parabot.model.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by sedat on 24.07.2015.
 */

@Component
public class MessageHelper {
    @Autowired
    private Environment environment;

    public String getCode(Exception exception) {
        String exceptionClassName = exception.getClass().getSimpleName();
        return environment.getProperty(exceptionClassName);
    }

    public String getMessage(Exception exception) {
        return environment.getProperty(getCode(exception));
    }
}
