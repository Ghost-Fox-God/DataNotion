package com.datanotion.backend.models;

import com.datanotion.backend.repositories.IProjectUserManagementRepository;

public class ProjectUser {
    private int projectId;
    private int userId;

    public ProjectUser(int projectId, int userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static int addUserToProject(IProjectUserManagementRepository projectUserManagementRepository, ProjectUser projectUser) {
        return projectUserManagementRepository.addUserTOProject(projectUser);
    }

    public static int removeUserFromProject(IProjectUserManagementRepository projectUserManagementRepository, ProjectUser projectUser) {
        return projectUserManagementRepository.deleteUserFromProject(projectUser);
    }
}
