package com.datanotion.backend.responses;

public class BadRequestResponse {
    private String message;

    public BadRequestResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
