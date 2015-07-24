package com.kodgemisi.parabot.model.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by sedat on 24.07.2015.
 */

public class ErrorMessageJSON {

    private boolean error;
    private String errorMessage;
    private String errorCode;
    private String userMessage;

    public ErrorMessageJSON(boolean error, String errorMessage, String errorCode, String userMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }

    public ErrorMessageJSON() {

    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
