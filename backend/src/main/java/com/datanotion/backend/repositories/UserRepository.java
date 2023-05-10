package com.datanotion.backend.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.exceptions.UserAlreadyExistsException;
import com.datanotion.backend.models.User;

@Repository
public class UserRepository implements IUserRepository {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DBconnect dbConnect;

    @Override
    public User getUserById(int id) throws UsernameNotFoundException {
        try {
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE id=?");
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            res.next();
            return new User(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
                    res.getString("email"), res.getString("password"), res.getString("role"));

        } catch (SQLException e) {
            throw new UsernameNotFoundException("User not found with id " + id);
        }
    }

    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        try {
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE email=?");
            statement.setString(1, email);
            ResultSet res = statement.executeQuery();
            res.next();
            return new User(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
                    res.getString("email"), res.getString("password"), res.getString("role"));

        } catch (SQLException e) {
            throw new UsernameNotFoundException("User not found with email " + email);
        }
    }

    @Override
    public List<User> getUsersByProjectId(int projectId) {
        try {
            List<User> users = new ArrayList<User>();
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User where id IN (select user_id from Project_has_User where project_id=?);");
            statement.setInt(1, projectId);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                users.add(
                        new User(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
                                res.getString("email"), res.getString("password"), res.getString("role")));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAnnotatorsByProjectId(int projectId) {
        try {
            List<User> users = new ArrayList<User>();
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User where role = 'annotator' AND id IN (select user_id from Project_has_User where project_id=?);");
            statement.setInt(1, projectId);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                users.add(
                        new User(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
                                res.getString("email"), res.getString("password"), res.getString("role")));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getManagersByProjectId(int projectId) {
        try {
            List<User> users = new ArrayList<User>();
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User where role = 'manager' AND id IN (select user_id from Project_has_User where project_id=?);");
            statement.setInt(1, projectId);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                users.add(
                        new User(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
                                res.getString("email"), res.getString("password"), res.getString("role")));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE email=?");
            statement.setString(1, user.getEmail());
            ResultSet res = statement.executeQuery();
            if (res.isBeforeFirst()) {
                throw new UserAlreadyExistsException(user);
            }

            statement = connection.prepareStatement(
                    "INSERT INTO User (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.executeUpdate();

            res = statement.getGeneratedKeys();
            res.next();
            user.setId(res.getInt(1));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllAnnotators() {
        try {
            List<User> users = new ArrayList<User>();
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User where role = 'annotator'");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                users.add(
                        new User(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
                                res.getString("email"), res.getString("password"), res.getString("role")));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllManagers() {
        try {
            List<User> users = new ArrayList<User>();
            Connection connection = dbConnect.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User where role = 'manager';");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                users.add(
                        new User(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
                                res.getString("email"), res.getString("password"), res.getString("role")));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
