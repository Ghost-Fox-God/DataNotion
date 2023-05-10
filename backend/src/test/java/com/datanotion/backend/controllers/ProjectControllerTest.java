package com.datanotion.backend.controllers;

import com.datanotion.backend.BackendApplication;
import com.datanotion.backend.responses.AuthSuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackendApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectControllerTest {
    @Autowired
    private MockMvc mvc;

    private static String AUTH_TOKEN = "";
    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(1)
    void setUp() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(URI.create("/login")).content(
                "{\"email\":\"manager@datanotion.com\",\"password\":\"manager123!\"}")).andReturn();
        String response = result.getResponse().getContentAsString();
        AuthSuccessResponse response2 = mapper.readValue(response, AuthSuccessResponse.class);
        AUTH_TOKEN = "Bearer " + response2.getAccess_token();
    }

    @Test
    @Order(2)
    void loadProjectsTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects").header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk());
    }

   @Test
   @Order(3)
   void createProject() throws Exception {
       mvc.perform(MockMvcRequestBuilders.post("/projects").header("Authorization", AUTH_TOKEN)
                                              .content("{\"projectName\":\"project made in test1\"}")
                                              .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
   }

    @Test
    @Order(4)
    void getProjectByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{id}", 1).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json("{\"name\":\"steleace lsugebskdj\",\"id\":1}"));

    }

    @Test
    @Order(5)
    void updateProjectTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/projects/{id}", 65).contentType(MediaType.APPLICATION_JSON).header("Authorization", AUTH_TOKEN).content("{\"id\":65,\"name\":\"2\"}")).andExpect(status().isOk());
    }


    @Test
    @Order(6)
    void deleteProjectByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/projects/{id}", 63).header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    void getUsersByProjectIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{id}/users", 64).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json(
                        "[{\"id\":108586,\"fullName\":\"The manager\",\"email\":\"manager@datanotion.com\",\"role\":\"manager\"}]"));
    }

    @Test
    @Order(8)
    void getManagersByProjectIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{id}/users/managers", 64).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json(
                        "[{\"id\":108586,\"fullName\":\"The manager\",\"email\":\"manager@datanotion.com\",\"role\":\"manager\"}]"));
    }

    @Test
    @Order(9)
    void getAnnotatorsByProjectIdTest() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.get("/projects/{id}/users/annotators", 64).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json(
                        "[]"));
    }
}
