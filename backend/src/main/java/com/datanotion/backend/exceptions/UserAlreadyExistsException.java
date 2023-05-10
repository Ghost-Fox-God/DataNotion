package com.datanotion.backend.exceptions;

import com.datanotion.backend.models.User;

public class UserAlreadyExistsException extends Exception {
    public User user;

    public UserAlreadyExistsException(User user) {
        super("User already exists with email " + user.getEmail());
        this.user = user;
    }
}
