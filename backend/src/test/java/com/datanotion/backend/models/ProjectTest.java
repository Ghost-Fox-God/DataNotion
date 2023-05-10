package com.datanotion.backend.models;

import com.datanotion.backend.mockDB.ProjectDbMock;
import com.datanotion.backend.requests.ProjectRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectTest {
    @Autowired
    private ProjectDbMock projectDbMock;

    @Test
    @Order(1)
    void projectObjectSuccessTest() {
        TextAnnotationProject project = new TextAnnotationProject(1, "test project");

        assertEquals(1, project.getId());
        assertEquals("test project", project.getName());
    }

    @Test
    @Order(2)
    void projectObjectGetterSetterSuccessTest() {
        TextAnnotationProject project = new TextAnnotationProject(1, "test project");
        project.setId(2);
        project.setName("temp");

        assertEquals(2, project.getId());
        assertEquals("temp", project.getName());
    }

    @Test
    @Order(3)
    void getProjectByIdSuccessTest() {
        TextAnnotationProject outputProject = TextAnnotationProject.getProjectById(projectDbMock, 1);

        assertEquals(1, outputProject.getId());
        assertEquals("test project", outputProject.getName());
    }

    @Test
    @Order(4)
    void getProjectByIdFailureTest() {
        TextAnnotationProject outputProject = TextAnnotationProject.getProjectById(projectDbMock, 3);

        assertNull(outputProject);
    }

    @Test
    @Order(5)
    void updateProjectSuccessTest() {
        TextAnnotationProject project = new TextAnnotationProject(1, "testing update");

        int actualOutput = project.updateProject(projectDbMock);
        assertEquals(1, actualOutput);
    }

    @Test
    @Order(6)
    void updateProjectFailureTest() {
        TextAnnotationProject project = new TextAnnotationProject(3, "testing update");

        int actualOutput = project.updateProject(projectDbMock);
        assertEquals(0, actualOutput);
    }

    @Test
    @Order(7)
    void deleteProjectByIdSuccessTest() {

        int actualOutput = TextAnnotationProject.deleteProjectById(projectDbMock, 1);
        assertEquals(1, actualOutput);
    }

    @Test
    @Order(8)
    void deleteProjectByIdFailureTest() {

        int actualOutput = TextAnnotationProject.deleteProjectById(projectDbMock, 3);
        assertEquals(0, actualOutput);
    }

    @Test
    @Order(9)
    void getProjectsOfUserSuccessTest() {
        User user = new User(2, "texp", "texp",
                             "texp", "texp",
                             "texp"
        );
        List<TextAnnotationProject> projectList = TextAnnotationProject.getProjectsOfUser(projectDbMock, user);
        assertEquals(1, projectList.size());
    }

    @Test
    @Order(10)
    void getProjectsOfUserFailureTest() {
        User user = new User(5, "texp", "texp",
                             "texp", "texp",
                             "texp"
        );
        List<TextAnnotationProject> projectList = TextAnnotationProject.getProjectsOfUser(projectDbMock, user);
        assertEquals(0, projectList.size());
    }

    @Test
    @Order(11)
    void createProjectSuccessTest() {
        ProjectRequest project = new ProjectRequest();
        project.setProjectName("temp");
        TextAnnotationProject project1 = TextAnnotationProject.createProject(projectDbMock, project);
        assertEquals("temp", project1.getName());
        assertEquals(3, project1.getId());

    }
}
