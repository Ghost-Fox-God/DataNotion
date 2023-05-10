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

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackendApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClassificationTagControllerTest {
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
    void getClassificationTagsByProjectIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{id}/classification", 1).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json("[{\"tagName\":\"xyz\",\"id\":3,\"projectId\":1}]"));
    }

    @Test
    void getClassificationTagByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{id}/classification/{classificationTagId}", 1,3).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json("{\"tagName\":\"xyz\",\"id\":3,\"projectId\":1}"));
    }

    @Test
    void createClassificationTag() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/projects/{id}/classification", 64).content("{\"tagName\":\"new\",\"projectId\":64}").header("Authorization", AUTH_TOKEN).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deleteClassificationTagTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/projects/{id}/classification/{classificationTagId}", 64,1000).header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk());
    }
}