package com.datanotion.backend.controllers;

import com.datanotion.backend.BackendApplication;
import com.datanotion.backend.responses.AuthSuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackendApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mvc;
    private static String AUTH_TOKEN = "";
    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(1)
    public void userLoginTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(URI.create("/login"))
                                               .content(
                                                       "{\"email\":\"manager@datanotion.com\",\"password\":\"manager123!\"}"))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        AuthSuccessResponse response2 = mapper.readValue(response, AuthSuccessResponse.class);
        AUTH_TOKEN = "Bearer " + response2.getAccess_token();
    }

    @Test
    @Order(2)
    void getAllTaskByUserTask() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks",72)
                            .header("Authorization", AUTH_TOKEN))
                .andExpect(content().json("[{\"taskId\":79,\"taskContent\":\"Discover new relationships and patterns hidden in your semantically enriched business knowledge.\",\"classificationId\":32,\"projectId\":72,\"userId\":964357,\"classificationTag\":\"positive\"},{\"taskId\":80,\"taskContent\":\"Extract more powerful and more relevant insights from your Big Data analytics by putting your information into context and drawing new conclusions from it.\",\"classificationId\":32,\"projectId\":72,\"userId\":964357,\"classificationTag\":\"positive\"},{\"taskId\":81,\"taskContent\":\"Benefit from predictive models and data-backed decision support systems that have access to untapped business intelligence.\",\"classificationId\":0,\"projectId\":72,\"userId\":964357,\"classificationTag\":null}]"));
    }

    @Test
    @Order(3)
    void getTaskOfProjectTask() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks/{taskId}",72,79)
                            .header("Authorization", AUTH_TOKEN))
                .andExpect(content().json("[{\"taskId\":79,\"taskContent\":\"Discover new relationships and patterns hidden in your semantically enriched business knowledge.\",\"classificationId\":32,\"projectId\":72,\"userId\":964357,\"classificationTag\":\"positive\"}]"));
    }

    @Test
    @Order(4)
    void deleteTaskTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/projects/{projectId}/tasks/{taskId}",60,78)
                            .header("Authorization", AUTH_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
