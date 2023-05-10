package com.datanotion.backend.models;

import com.datanotion.backend.repositories.ITaskRepository;
import com.datanotion.backend.responses.GetTaskRequestResponse;

import java.util.List;

public class Task {
    private int id;
    private String content;
    private int classificationId;
    private int projectId;
    private int userId;

    public Task() {

    }

    public Task(int id, String content, int classificationId, int projectId, int userId) {
        this.id = id;
        this.content = content;
        this.classificationId = classificationId;
        this.projectId = projectId;
        this.userId = userId;
    }

    public Task(int id, String content, int classificationId) {
        this.id = id;
        this.content = content;
        this.classificationId = classificationId;
    }

    public static List<GetTaskRequestResponse> fetchAllTaskByUser(ITaskRepository taskRepository, User user,
            int projectId) {
        return taskRepository.getAllTaskByUser(user, projectId);
    }

    public static List<GetTaskRequestResponse> fetchTaskOfProject(ITaskRepository taskRepository, int taskId,
            int projectId) {
        return taskRepository.getTaskOfProject(taskId, projectId);
    }

    public static int deleteTaskOfProject(ITaskRepository taskRepository, int taskId, int projectId) {
        return taskRepository.deleteTask(taskId, projectId);
    }

    public int getProjectId() {
        return projectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getClassificationId() {
        return classificationId;
    }
}
