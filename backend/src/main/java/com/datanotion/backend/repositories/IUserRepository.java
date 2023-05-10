package com.datanotion.backend.repositories;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.datanotion.backend.exceptions.UserAlreadyExistsException;
import com.datanotion.backend.models.User;

public interface IUserRepository {
    User getUserById(int id) throws UsernameNotFoundException;

    User getUserByEmail(String email) throws UsernameNotFoundException;

    List<User> getUsersByProjectId(int projectId);

    List<User> getAnnotatorsByProjectId(int projectId);

    List<User> getManagersByProjectId(int projectId);

    User saveUser(User user) throws UserAlreadyExistsException;

    List<User> getAllAnnotators();

    List<User> getAllManagers();
}
