package com.zuzu.sg.review.exception;

import java.util.List;

public class JsonlValidationException extends RuntimeException {
    private final List<String> validationErrors;

    public JsonlValidationException(String message, List<String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
