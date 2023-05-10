package com.datanotion.backend.responses;

public class AuthSuccessResponse {
    private String access_token;

    public AuthSuccessResponse() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public AuthSuccessResponse(String access_token) {
        this.access_token = access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}