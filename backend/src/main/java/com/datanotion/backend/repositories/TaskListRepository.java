package com.datanotion.backend.repositories;

import com.datanotion.backend.databaseConnection.DBconnect;
import com.datanotion.backend.models.Task;
import com.datanotion.backend.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.datanotion.backend.responses.GetTaskRequestResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskListRepository implements ITaskRepository {
    @Autowired
    private DBconnect dbConnect;

    @Override
    public List<GetTaskRequestResponse> getAllTaskByUser(User user, int projectId) {
        List<GetTaskRequestResponse> taskListByProject = new ArrayList<>();
        {
            try {
                PreparedStatement statement = null;
                if (user.getRole().equals("manager")) {
                    statement = dbConnect.getDBConnection().prepareStatement(
                            "SELECT DataTask.id, DataTask.content, DataTask.classification_id, DataTask.project_id, DataTask.user_id, ClassificationTag.tag_name From DataTask left outer Join ClassificationTag ON DataTask.classification_id = ClassificationTag.id where DataTask.project_id = ?;");
                    statement.setInt(1, projectId);
                } else {
                    statement = dbConnect.getDBConnection().prepareStatement(
                            "SELECT DataTask.id, DataTask.content, DataTask.classification_id, DataTask.project_id, DataTask.user_id, ClassificationTag.tag_name From DataTask left outer Join ClassificationTag ON DataTask.classification_id = ClassificationTag.id where DataTask.user_id = ? AND DataTask.project_id = ?;");
                    statement.setInt(1, user.getId());
                    statement.setInt(2, projectId);
                }
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    taskListByProject.add(new GetTaskRequestResponse(
                            rs.getInt("id"),
                            rs.getString("content"),
                            rs.getInt("classification_id"),
                            rs.getInt("project_id"),
                            rs.getInt("user_id"),
                            rs.getString("tag_name")));
                }
                return taskListByProject;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<GetTaskRequestResponse> getTaskOfProject(int taskId, int projectId) {
        List<GetTaskRequestResponse> taskListByProject = new ArrayList<>();
        {
            try {
                PreparedStatement statement = dbConnect.getDBConnection()
                        .prepareStatement(
                                "SELECT DataTask.id, DataTask.content, DataTask.classification_id, DataTask.project_id, DataTask.user_id, ClassificationTag.tag_name From DataTask Inner Join ClassificationTag ON DataTask.classification_id = ClassificationTag.id AND DataTask.id = ?;");
                statement.setLong(1, taskId);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    taskListByProject.add(new GetTaskRequestResponse(
                            rs.getInt("id"),
                            rs.getString("content"),
                            rs.getInt("classification_id"),
                            rs.getInt("project_id"),
                            rs.getInt("user_id"),
                            rs.getString("tag_name")));
                }
                return taskListByProject;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int deleteTask(int taskId, int projectId) {
        try (PreparedStatement statement = dbConnect.getDBConnection()
                .prepareStatement("DELETE FROM DataTask WHERE project_id = ? AND id = ?")) {
            statement.setLong(1, projectId);
            statement.setLong(2, taskId);
            return (statement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task createTask(Task task) {
        try (PreparedStatement statement = dbConnect.getDBConnection().prepareStatement(
                "INSERT INTO DataTask (content,project_id,user_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getContent());
            statement.setInt(2, task.getProjectId());
            statement.setInt(3, task.getUserId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                task.setId(rs.getInt(1));
            }
            return task;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
