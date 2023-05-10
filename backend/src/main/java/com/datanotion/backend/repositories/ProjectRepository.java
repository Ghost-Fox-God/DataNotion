package com.datanotion.backend.repositories;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.factories.DatanotionTextAnnotationFactory;
import com.datanotion.backend.models.Project;
import com.datanotion.backend.models.TextAnnotationProject;
import com.datanotion.backend.models.User;

import com.datanotion.backend.requests.ProjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository implements IProjectRepository {

    @Autowired
    private DatanotionTextAnnotationFactory datanotionTextAnnotationFactory;

    @Autowired
    private DBconnect dbConnect;

    @Override
    public List<TextAnnotationProject> getProjectsOfUser(User user) {
        try (PreparedStatement statement = dbConnect.getDBConnection().prepareStatement(
                "SELECT * FROM Project where id IN (select project_id from Project_has_User where user_id=?);")) {
            List<TextAnnotationProject> projects = new ArrayList<TextAnnotationProject>();
            statement.setInt(1, user.getId());
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                TextAnnotationProject project = (TextAnnotationProject) datanotionTextAnnotationFactory
                        .createProject(res.getString("name"));
                project.setId(res.getInt("id"));
                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Project getProjectById(int id) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("SELECT * FROM Project WHERE id=?")) {
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            res.next();
            TextAnnotationProject project = (TextAnnotationProject) datanotionTextAnnotationFactory
                    .createProject(res.getString("name"));
            project.setId(res.getInt("id"));
            return project;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Project createProject(ProjectRequest projectRequest) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("INSERT INTO Project (name) VALUES (?);", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, projectRequest.getProjectName());
            statement.executeUpdate();
            ResultSet res = statement.getGeneratedKeys();
            res.next();
            TextAnnotationProject project = (TextAnnotationProject) datanotionTextAnnotationFactory
                    .createProject(projectRequest.getProjectName());
            project.setId(res.getInt(1));
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateProject(Project project) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("UPDATE Project SET name = ? WHERE id = ?;")) {
            statement.setString(1, project.getName());
            statement.setInt(2, ((TextAnnotationProject) project).getId());
            return (statement.executeUpdate());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteProjectById(int id) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("DELETE FROM Project WHERE id = ?;")) {
            statement.setInt(1, id);
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
