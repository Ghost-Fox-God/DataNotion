package com.datanotion.backend.mockDB;

import com.datanotion.backend.exceptions.UserAlreadyExistsException;
import com.datanotion.backend.models.ProjectUser;
import com.datanotion.backend.models.User;
import com.datanotion.backend.repositories.IUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class UserDbMock implements IUserRepository {
    private final ArrayList<User> users = new ArrayList<>(
            List.of(new User(1, "test1", "user1", "testmanager@gmail.com", "test1", "manager"),
                    new User(2, "test2", "user2", "testannotator@gmail.com", "test2", "annotator"),
                    new User(3, "test3", "user3", "test3@gmail.com", "test3", "annotator")));

    private final ArrayList<ProjectUser> projectUsers = new ArrayList<>(
            List.of(new ProjectUser(1, 2), new ProjectUser(2, 1), new ProjectUser(1, 3)));

    @Override
    public User getUserById(int id) throws UsernameNotFoundException {
        return users.stream().filter(user -> user.getId() == id).findAny()
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findAny()
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public List<User> getUsersByProjectId(int projectId) {
        List<ProjectUser> projectUserList = projectUsers.stream().filter(projectUser -> projectUser.getProjectId() == projectId).collect(
                Collectors.toList());
        List<User> responseUser = new ArrayList<>();
        for (User user : users) {
            for (ProjectUser projectUser : projectUserList) {
                if (projectUser.getUserId() == user.getId()) {
                    responseUser.add(user);
                }
            }
        }
        return responseUser;
    }

    @Override
    public List<User> getAnnotatorsByProjectId(int projectId) {
        List<ProjectUser> projectUserList = projectUsers.stream().filter(projectUser -> projectUser.getProjectId() == projectId).collect(
                Collectors.toList());
        List<User> responseUser = new ArrayList<>();
        for (User user : users) {
            for (ProjectUser projectUser : projectUserList) {
                if (projectUser.getUserId() == user.getId() && user.getRole().equals("annotator")) {
                    responseUser.add(user);
                }
            }
        }
        return responseUser;
    }

    @Override
    public List<User> getManagersByProjectId(int projectId) {
        List<ProjectUser> projectUserList = projectUsers.stream().filter(projectUser -> projectUser.getProjectId() == projectId).collect(
                Collectors.toList());
        List<User> responseUser = new ArrayList<>();
        for (User user : users) {
            for (ProjectUser projectUser : projectUserList) {
                if (projectUser.getUserId() == user.getId() && user.getRole().equals("managers")) {
                    responseUser.add(user);
                }
            }
        }
        return responseUser;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        User user1 = users.stream().filter(user2 -> Objects.equals(user2.getEmail(),
                user.getEmail())).findAny().orElse(null);
        if (user1 == null) {
            users.add(user);
            return users.stream().filter(user2 -> user2.getEmail().equals(user.getEmail())).findAny().orElse(null);
        } else {
            throw new UserAlreadyExistsException(user);
        }
    }

    @Override
    public List<User> getAllAnnotators() {
        return users.stream().filter(user -> user.getRole().equals("annotator")).collect(Collectors.toList());
    }

    @Override
    public List<User> getAllManagers() {
        return users.stream().filter(user -> user.getRole().equals("manager")).collect(Collectors.toList());
    }
}
