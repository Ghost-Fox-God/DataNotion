package com.datanotion.backend.models;

import com.datanotion.backend.exceptions.BadRequestException;
import com.datanotion.backend.exceptions.UserAlreadyExistsException;
import com.datanotion.backend.repositories.IUserRepository;
import com.datanotion.backend.requests.UserRegistrationRequest;
import java.util.List;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    public User(int id, String firstName, String lastName, String email, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static User saveUser(IUserRepository userRepository, UserRegistrationRequest registrationRequest)
            throws BadRequestException {
        try {
            return userRepository.saveUser(
                    new User(0, registrationRequest.getFirstName(), registrationRequest.getLastName(),
                            registrationRequest.getEmail(), registrationRequest.getPassword(),
                            registrationRequest.getRole()));
        } catch (UserAlreadyExistsException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public static List<User> getAllAnnotators(IUserRepository userRepository) {
        return userRepository.getAllAnnotators();
    }

    public static List<User> getAllManagers(IUserRepository userRepository) {
        return userRepository.getAllManagers();
    }

    public static User getUserById(IUserRepository userRepository, int id) {
        return userRepository.getUserById(id);
    }

    public static User getUserByEmail(IUserRepository userRepository, String email) {
        return userRepository.getUserByEmail(email);
    }

    public static List<User> getUsersByProjectId(IUserRepository userRepository, int projectId) {
        return userRepository.getUsersByProjectId(projectId);
    }

    public static List<User> getAnnotatorsByProjectId(IUserRepository userRepository, int projectId) {
        return userRepository.getAnnotatorsByProjectId(projectId);
    }

    public static List<User> getManagersByProjectId(IUserRepository userRepository, int projectId) {
        return userRepository.getManagersByProjectId(projectId);
    }
}
