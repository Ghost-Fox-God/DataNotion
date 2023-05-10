package com.datanotion.backend.controllers;

import com.datanotion.backend.models.ProjectUser;
import com.datanotion.backend.repositories.ProjectUserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{id}")
public class ProjectUserManagementController {

    @Autowired
    ProjectUserManagementRepository projectUserManagementRepository;

    @PostMapping("/users")
    public int addUserToProject(@RequestBody ProjectUser projectUser) {
        return (ProjectUser.addUserToProject(projectUserManagementRepository, projectUser));
    }

    @DeleteMapping("/users")
    public int deleteUserFromProject(@RequestBody ProjectUser projectUser) {
        return (ProjectUser.removeUserFromProject(projectUserManagementRepository, projectUser));
    }
}
