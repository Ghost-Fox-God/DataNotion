package com.datanotion.backend.mockDB;

import com.datanotion.backend.models.Project;
import com.datanotion.backend.models.ProjectUser;
import com.datanotion.backend.models.TextAnnotationProject;
import com.datanotion.backend.models.User;
import com.datanotion.backend.repositories.IProjectRepository;
import com.datanotion.backend.requests.ProjectRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProjectDbMock implements IProjectRepository {

    private final ArrayList<TextAnnotationProject> projects = new ArrayList<>(
            List.of(new TextAnnotationProject(1, "test project"), new TextAnnotationProject(2, "testing app")));

    private final ArrayList<ProjectUser> projectUsers = new ArrayList<>(
            List.of(new ProjectUser(1, 2), new ProjectUser(1, 3)));
    @Override
    public List<TextAnnotationProject> getProjectsOfUser(User user) {
        List<ProjectUser> users = projectUsers.stream().filter(projectUser -> projectUser.getUserId() == user.getId()).collect(
                Collectors.toList());
        List<TextAnnotationProject> projectList = new ArrayList<>();
        for(TextAnnotationProject project : projects){
            for(ProjectUser projectUser : users){
                if(project.getId() == projectUser.getUserId()){
                    projectList.add(project);
                }
            }
        }
        return projectList;
    }

    @Override
    public TextAnnotationProject getProjectById(int id) {
        return projects.stream().filter(project -> project.getId() == id).findAny().orElse(null);
    }

    @Override
    public TextAnnotationProject createProject(ProjectRequest projectRequest) {
        projects.add(new TextAnnotationProject(3, projectRequest.getProjectName()));
        return projects.stream().filter(project -> project.getName().equals(projectRequest.getProjectName())).findAny()
                .orElse(null);
    }

    @Override
    public int updateProject(Project project) {
        TextAnnotationProject project2 = projects.stream()
                .filter(project1 -> project1.getId() == ((TextAnnotationProject) project).getId()).findAny()
                .orElse(null);
        if (project2 == null) {
            return 0;
        } else {
            project2.setName(project.getName());
            TextAnnotationProject project3 = projects.stream()
                    .filter(project1 -> project1.getId() == ((TextAnnotationProject) project).getId()).findAny()
                    .orElse(null);
            if (project3 == null) {
                return 0;
            } else {
                if (project3.getName().equals(project.getName())) {

                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    @Override
    public int deleteProjectById(int id) {

        boolean isProjectRemoved = projects.removeIf(project1 -> project1.getId() == id);
        TextAnnotationProject project = projects.stream().filter(project1 -> project1.getId() == id).findAny()
                .orElse(null);
        if (isProjectRemoved && project == null) {
            return 1;
        } else {
            return 0;
        }
    }
}
