package com.datanotion.backend.mockDB;

import com.datanotion.backend.models.ProjectUser;
import com.datanotion.backend.repositories.IProjectUserManagementRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectUserDbMock implements IProjectUserManagementRepository {

    private final ArrayList<ProjectUser> projectUsers = new ArrayList<>(
            List.of(new ProjectUser(1, 2), new ProjectUser(1, 3)));

    @Override
    public int addUserTOProject(ProjectUser projectUser) {
        ProjectUser user = projectUsers.stream()
                .filter(projectUser1 -> projectUser1.getUserId() == projectUser.getUserId() && projectUser1.getProjectId() == projectUser.getProjectId())
                .findAny().orElse(null);
        if (user == null) {
            projectUsers.add(projectUser);
            ProjectUser user1 = projectUsers.stream()
                    .filter(projectUser1 -> projectUser1.getUserId() == projectUser.getUserId() && projectUser1.getProjectId() == projectUser.getProjectId())
                    .findAny().orElse(null);
            if (user1 == null) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 0;

        }
    }

    @Override
    public int deleteUserFromProject(ProjectUser projectUser) {
        ProjectUser user = projectUsers.stream()
                .filter(projectUser1 -> projectUser1.getUserId() == projectUser.getUserId() && projectUser1.getProjectId() == projectUser.getProjectId())
                .findAny().orElse(null);
        if (user == null) {
            return 0;
        } else {
            projectUsers.remove(projectUser);
            ProjectUser user1 = projectUsers.stream()
                    .filter(projectUser1 -> projectUser1.getUserId() == projectUser.getUserId() && projectUser1.getProjectId() == projectUser.getProjectId())
                    .findAny().orElse(null);
            if (user1 == null) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
