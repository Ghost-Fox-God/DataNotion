package com.datanotion.backend.models;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TextAnnotationExportTest {
    TextAnnotationExport textAnnotationExport = new TextAnnotationExport(1, 5, new Date(1999,8,17), "Pending", 100);

    @Test
    @Order(1)
    void setIdTest(){
        textAnnotationExport.setId(2);
        assertEquals(2,textAnnotationExport.getId());
    }
    @Test
    @Order(2)
    void setProjectIdTest(){
        textAnnotationExport.setProjectId(10);
        assertEquals(10,textAnnotationExport.getProjectId());
    }
    @Test
    @Order(3)
    void setExportDateTest(){
        textAnnotationExport.setExportDate(new Date(2022,12,13));
        assertEquals(new Date(2022,12,13),textAnnotationExport.getExportDate());
    }
    @Test
    @Order(4)
    void setStatusTest(){
        textAnnotationExport.setStatus("Completed");
        assertEquals("Completed",textAnnotationExport.getStatus());
    }
    @Test
    @Order(5)
    void setRecordCountTest(){
        textAnnotationExport.setRecordCount(200);
        assertEquals(200,textAnnotationExport.getRecordCount());
    }
    @Test
    @Order(6)
    void getIdTest(){
        assertEquals(1,textAnnotationExport.getId());
    }
    @Test
    @Order(7)
    void getProjectIdTest(){
        assertEquals(5,textAnnotationExport.getProjectId());
    }
    @Test
    @Order(8)
    void getStatusTest(){
        assertEquals("Pending",textAnnotationExport.getStatus());
    }
    @Test
    @Order(9)
    void getRecordCountTest(){
        assertEquals(100,textAnnotationExport.getRecordCount());
    }
    @Test
    @Order(10)
    void getExportDateTest(){
        assertEquals(new Date(1999,8,17),textAnnotationExport.getExportDate());
    }
}
