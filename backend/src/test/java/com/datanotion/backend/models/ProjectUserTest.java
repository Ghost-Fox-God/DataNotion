package com.datanotion.backend.models;

import com.datanotion.backend.mockDB.ProjectUserDbMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectUserTest {

    @Autowired
    private ProjectUserDbMock projectUserDbMock;

    @Test
    void projectUserObjectTest() {
        ProjectUser projectUser = new ProjectUser(1, 1);

        assertEquals(1, projectUser.getProjectId());
        assertEquals(1, projectUser.getUserId());
    }

    @Test
    void projectUserGetterSetterTest() {
        ProjectUser projectUser = new ProjectUser(1, 1);
        projectUser.setProjectId(2);
        projectUser.setUserId(2);

        assertEquals(2, projectUser.getProjectId());
        assertEquals(2, projectUser.getUserId());
    }

    @Test
    void addUserToProjectSuccessTest(){
        int outputResult = ProjectUser.addUserToProject(projectUserDbMock, new ProjectUser(1, 1));

        assertEquals(1,outputResult);
    }

    @Test
    void addUserToProjectFailureTest(){
        int outputResult = ProjectUser.addUserToProject(projectUserDbMock, new ProjectUser(1, 2));

        assertEquals(0,outputResult);
    }
    @Test
    void removeUserFromProjectSuccessTest(){
        int outputResult = ProjectUser.removeUserFromProject(projectUserDbMock, new ProjectUser(1, 3));

        assertEquals(1,outputResult);
    }
    @Test
    void removeUserFromProjectFailureTest(){
        int outputResult = ProjectUser.removeUserFromProject(projectUserDbMock, new ProjectUser(1, 5));

        assertEquals(0,outputResult);
    }
}
