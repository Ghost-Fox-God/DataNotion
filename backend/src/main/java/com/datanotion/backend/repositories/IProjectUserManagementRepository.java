package com.datanotion.backend.repositories;

import com.datanotion.backend.models.ProjectUser;

public interface IProjectUserManagementRepository {
    int addUserTOProject(ProjectUser projectUser);

    int deleteUserFromProject(ProjectUser projectUser);
}
