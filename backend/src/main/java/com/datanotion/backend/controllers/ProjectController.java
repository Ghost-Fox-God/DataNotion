package com.datanotion.backend.controllers;

import com.datanotion.backend.models.TextAnnotationProject;
import com.datanotion.backend.models.ProjectUser;
import com.datanotion.backend.models.User;
import com.datanotion.backend.repositories.ProjectRepository;
import com.datanotion.backend.repositories.ProjectUserManagementRepository;
import com.datanotion.backend.repositories.UserRepository;
import com.datanotion.backend.requests.ProjectRequest;
import com.datanotion.backend.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectUserManagementRepository projectUserManagementRepository;

    @GetMapping("/projects")
    public List<TextAnnotationProject> loadProjects() {
        User user = User.getUserByEmail(userRepository, (String) SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal());
        return TextAnnotationProject.getProjectsOfUser(projectRepository, user);
    }

    @PostMapping("/projects")
    public int createProject(@RequestBody ProjectRequest projectRequest) {
        User user = User.getUserByEmail(userRepository, (String) SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal());

        ProjectUser projectUser = new ProjectUser(
                TextAnnotationProject.createProject(projectRepository, projectRequest).getId(), user.getId());

        return ProjectUser.addUserToProject(projectUserManagementRepository, projectUser);
    }

    @GetMapping("/projects/{id}")
    public TextAnnotationProject getProjectById(@PathVariable("id") int id) {
        return TextAnnotationProject.getProjectById(projectRepository, id);
    }

    @PutMapping("/projects/{id}")
    public int updateProject(@RequestBody TextAnnotationProject project) {
        return project.updateProject(projectRepository);
    }

    @DeleteMapping("/projects/{id}")
    public int deleteProjectById(@PathVariable("id") int id) {
        return TextAnnotationProject.deleteProjectById(projectRepository, id);
    }

    @GetMapping("/projects/{id}/users")
    public List<UserResponse> getUsersByProjectId(@PathVariable("id") int id) {

        return userResponsesConverter(User.getUsersByProjectId(userRepository, id));
    }

    @GetMapping("/projects/{id}/users/managers")
    public List<UserResponse> getManagersByProjectId(@PathVariable("id") int id) {

        return userResponsesConverter(User.getManagersByProjectId(userRepository, id));
    }

    @GetMapping("/projects/{id}/users/annotators")
    public List<UserResponse> getAnnotatorsByProjectId(@PathVariable("id") int id) {
        return userResponsesConverter(User.getAnnotatorsByProjectId(userRepository, id));
    }

    private List<UserResponse> userResponsesConverter(List<User> users){
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> userResponses.add(
                new UserResponse(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail(),
                                 user.getRole()
                )));
        return userResponses;
    }
}
