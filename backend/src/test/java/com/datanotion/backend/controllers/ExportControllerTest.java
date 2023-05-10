package com.datanotion.backend.controllers;

import java.net.URI;

import com.datanotion.backend.BackendApplication;
import com.datanotion.backend.responses.AuthSuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackendApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExportControllerTest {
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
    void listExportsTest()  throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/projects/{id}/exports", 50).header("Authorization", AUTH_TOKEN))
                .andExpect(content().json("[{\"exportDate\":\"2022-12-13\",\"status\":\"Done\",\"recordCount\":0,\"id\":27,\"projectId\":50}]"));
    }
}
