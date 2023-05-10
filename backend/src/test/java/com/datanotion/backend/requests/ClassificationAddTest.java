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
public class ClassificationAddTest {
    ClassificationAdd classificationAdd = new ClassificationAdd(2);

    @Test
    @Order(1)
    void setClassificationAddTest(){
        classificationAdd.setClassificationId(4);
        assertEquals(4,classificationAdd.getClassificationId());
    }
    @Test
    @Order(2)
    void getClassificationIdTest(){
        assertEquals(2,classificationAdd.getClassificationId());
    }
}