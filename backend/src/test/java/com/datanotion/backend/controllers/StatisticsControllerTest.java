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

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackendApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatisticsControllerTest {
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
    void getStatisticsByProjectIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{id}/statistics", 50).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json(
                    "{\"totalDataTasks\":9,\"annotatedDataTasks\":3,\"entityStatistics\":[{\"tagName\":\"anger\",\"tagCount\":2},{\"tagName\":\"anxious\",\"tagCount\":1},{\"tagName\":\"fear\",\"tagCount\":1},{\"tagName\":\"happy\",\"tagCount\":1},{\"tagName\":\"sad\",\"tagCount\":1}],\"classificationStatistics\":[{\"tagName\":\"negative\",\"tagCount\":1},{\"tagName\":\"positive\",\"tagCount\":2}]}"));
    }
}