package com.datanotion.backend.responses;

public class GetTaskRequestResponse {
    public int taskId;
    public String taskContent;
    public int classificationId;

    public int projectId;
    public int userId;
    public String classificationTag;

    public GetTaskRequestResponse(int taskId, String taskContent, int classificationId, int projectId, int userId,
            String classificationTag) {
        this.taskId = taskId;
        this.taskContent = taskContent;
        this.classificationId = classificationId;
        this.projectId = projectId;
        this.userId = userId;
        this.classificationTag = classificationTag;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public int getClassificationId() {
        return classificationId;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getUserId() {
        return userId;
    }

    public String getClassificationTag() {
        return classificationTag;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setClassificationTag(String classificationTag) {
        this.classificationTag = classificationTag;
    }
}
