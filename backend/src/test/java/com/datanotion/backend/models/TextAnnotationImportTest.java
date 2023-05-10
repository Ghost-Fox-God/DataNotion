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
public class TextAnnotationImportTest {
    TextAnnotationImport textAnnotationImport = new TextAnnotationImport(1, 5, new Date(1999, 8, 17), "File", 100,
                                                                         "Completed");

    @Test
    @Order(1)
    void setIdTest() {
        textAnnotationImport.setId(2);
        assertEquals(2, textAnnotationImport.getId());
    }

    @Test
    @Order(2)
    void setProjectIdTest() {
        textAnnotationImport.setProjectId(10);
        assertEquals(10, textAnnotationImport.getProjectId());
    }

    @Test
    @Order(3)
    void setImportDateTest() {
        textAnnotationImport.setImportDate(new Date(2022, 12, 13));
        assertEquals(new Date(2022, 12, 13), textAnnotationImport.getImportDate());
    }

    @Test
    @Order(4)
    void setStatusTest() {
        textAnnotationImport.setStatus("pending");
        assertEquals("pending", textAnnotationImport.getStatus());
    }

    @Test
    @Order(5)
    void setRecordCountTest() {
        textAnnotationImport.setRecordCount(200);
        assertEquals(200, textAnnotationImport.getRecordCount());
    }

    @Test
    @Order(6)
    void setFileNameTest() {
        textAnnotationImport.setFileName("NewFile");
        assertEquals("NewFile", textAnnotationImport.getFileName());
    }

    @Test
    @Order(7)
    void getIdTest() {
        assertEquals(1, textAnnotationImport.getId());
    }

    @Test
    @Order(8)
    void getProjectIdTest() {
        assertEquals(5, textAnnotationImport.getProjectId());
    }

    @Test
    @Order(9)
    void getStatusTest() {
        assertEquals("Completed", textAnnotationImport.getStatus());
    }

    @Test
    @Order(10)
    void getRecordCountTest() {
        assertEquals(100, textAnnotationImport.getRecordCount());
    }

    @Test
    @Order(11)
    void getImportDateTest() {
        assertEquals(new Date(1999, 8, 17), textAnnotationImport.getImportDate());
    }

    @Test
    @Order(12)
    void getFileNameTest() {
        assertEquals("File", textAnnotationImport.getFileName());
    }
}