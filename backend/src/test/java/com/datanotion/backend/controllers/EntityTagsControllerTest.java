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
class EntityTagsControllerTest {
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
    void getEntityTagsByProjectIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{id}/entity", 64).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json("[{\"tagName\":\"new\",\"id\":66,\"projectId\":64},{\"tagName\":\"new\",\"id\":67,\"projectId\":64},{\"tagName\":\"new\",\"id\":68,\"projectId\":64}]"));
    }

    @Test
    void getEntityTagByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects/{id}/entity/{entityId}", 64,66).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json("{\"tagName\":\"new\",\"id\":66,\"projectId\":64}"));
    }

    @Test
    void createEntityTagTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/projects/{id}/entity", 64).content("{\"tagName\":\"new\",\"projectId\":57}").header("Authorization", AUTH_TOKEN).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deleteEntityTagTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/projects/{id}/entity/{entityId}", 64,1000).header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk());
    }
}