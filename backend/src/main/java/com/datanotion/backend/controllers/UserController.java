package com.datanotion.backend.controllers;

import com.datanotion.backend.repositories.IUserRepository;
import com.datanotion.backend.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.datanotion.backend.exceptions.BadRequestException;
import com.datanotion.backend.models.User;
import com.datanotion.backend.requests.UserRegistrationRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    IUserRepository userRepository;

    @PostMapping("/users/register")
    public UserResponse registerUser(@RequestBody UserRegistrationRequest registrationRequest)
            throws BadRequestException {
        User user = User.saveUser(userRepository, registrationRequest);
        return new UserResponse(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail(),
                user.getRole());
    }

    @GetMapping("/users/annotators")
    public List<UserResponse> getAllAnnotators() {
        List<User> users = User.getAllAnnotators(userRepository);
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> userResponses.add(
                new UserResponse(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail(),
                        user.getRole())));
        return userResponses;
    }

    @GetMapping("/users/managers")
    public List<UserResponse> getAllManagers() {
        List<User> users = User.getAllManagers(userRepository);
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> userResponses.add(
                new UserResponse(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail(),
                        user.getRole())));
        return userResponses;
    }
}
