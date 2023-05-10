package com.datanotion.backend.models;

import com.datanotion.backend.repositories.IProjectRepository;
import com.datanotion.backend.requests.ProjectRequest;

import java.util.List;

public class TextAnnotationProject extends Project {
    private int id;

    public TextAnnotationProject(int id, String name) {
        super(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int updateProject(IProjectRepository projectRepository) {
        return projectRepository.updateProject(this);
    }

    public static int deleteProjectById(IProjectRepository projectRepository, int id) {
        return projectRepository.deleteProjectById(id);
    }

    public static List<TextAnnotationProject> getProjectsOfUser(IProjectRepository projectRepository, User user) {
        return (List<TextAnnotationProject>) projectRepository.getProjectsOfUser(user);
    }

    public static TextAnnotationProject createProject(IProjectRepository projectRepository,
            ProjectRequest projectRequest) {
        return (TextAnnotationProject) projectRepository.createProject(projectRequest);
    }

    public static TextAnnotationProject getProjectById(IProjectRepository projectRepository, int id) {
        return (TextAnnotationProject) projectRepository.getProjectById(id);
    }
}
