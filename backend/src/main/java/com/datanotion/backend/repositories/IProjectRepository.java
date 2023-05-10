package com.datanotion.backend.repositories;

import java.util.List;

import com.datanotion.backend.models.Project;
import com.datanotion.backend.models.User;
import com.datanotion.backend.requests.ProjectRequest;

public interface IProjectRepository {
    List<? extends Project> getProjectsOfUser(User user);

    Project getProjectById(int id);

    Project createProject(ProjectRequest projectRequest);

    int updateProject(Project project);

    int deleteProjectById(int id);
}
