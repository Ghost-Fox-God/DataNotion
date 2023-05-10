package com.datanotion.backend.repositories;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.models.ProjectUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ProjectUserManagementRepository implements IProjectUserManagementRepository {

    @Autowired
    private DBconnect dbConnect;

    @Override
    public int addUserTOProject(ProjectUser projectUser) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("INSERT INTO Project_has_User (project_id, user_id) VALUES (?, ?);")) {
            statement.setInt(1, projectUser.getProjectId());
            statement.setInt(2, projectUser.getUserId());
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteUserFromProject(ProjectUser projectUser) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("DELETE FROM Project_has_User WHERE project_id = ? AND user_id = ?;")) {
            statement.setInt(1, projectUser.getProjectId());
            statement.setInt(2, projectUser.getUserId());
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
