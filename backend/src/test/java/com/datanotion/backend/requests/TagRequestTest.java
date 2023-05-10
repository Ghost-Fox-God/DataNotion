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
public class TagRequestTest {
    TagRequest tagRequest = new TagRequest("happy",2);

    @Test
    @Order(1)
    void setTagNameTest(){
        tagRequest.setTagName("sad");
        assertEquals("sad",tagRequest.getTagName());
    }
    @Test
    @Order(2)
    void setProjectIdTest(){
        tagRequest.setProjectId(4);
        assertEquals(4,tagRequest.getProjectId());
    }
    @Test
    @Order(3)
    void getTagNameTest(){
        assertEquals("happy",tagRequest.getTagName());
    }
    @Test
    @Order(4)
    void getProjectIdTest(){
        assertEquals(2,tagRequest.getProjectId());
    }
}