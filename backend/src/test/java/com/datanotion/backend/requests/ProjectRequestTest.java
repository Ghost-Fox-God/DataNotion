package com.datanotion.backend.requests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectRequestTest {
    ProjectRequest projectRequest = new ProjectRequest();

    @Test
    @Order(1)
    void setProjectNameTest(){
        projectRequest.setProjectName("Datanotion");
        assertEquals("Datanotion",projectRequest.getProjectName());
    }
    @Test
    @Order(2)
    void getProjectTest(){
        assertEquals(null,projectRequest.getProjectName());
    }
}