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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackendApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClassificationControllerTest {
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
    void getClassificationOfTask() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks/{taskId}/classification", 72, 79)
                            .header("Authorization", AUTH_TOKEN))
                .andExpect(content().json("[{\"tagName\":\"positive\",\"id\":32,\"projectId\":72}]  "));
    }

    @Test
    @Order(3)
    void addClassificationOfTask() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/projects/{projectId}/tasks/{taskId}/classification", 72, 80)
                            .header("Authorization", AUTH_TOKEN).content("{\n    \"classificationId\":32\n}")
                            .contentType(
                                    MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    void deleteClassificationOfTask() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/projects/{projectId}/tasks/{taskId}/classification", 8, 69)
                            .header("Authorization", AUTH_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
