package com.datanotion.backend.models;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExportRowDataModelTest {

    List<EntityAnnotation> entityAnnotationList = new ArrayList<>(List.of(new EntityAnnotation(10,15,"Happy"),new EntityAnnotation(16,20,"Sad")));
    ExportRowDataModel exportRowDataModel = new ExportRowDataModel("Content","Classification",entityAnnotationList);

    @Test
    @Order(1)
    void getContentTest(){
        assertEquals("Content",exportRowDataModel.getContent());
    }
    @Test
    @Order(2)
    void getClassificationTest(){
        assertEquals("Classification",exportRowDataModel.getClassification());
    }
    @Test
    @Order(3)
    void getEntitiesTest(){
        assertEquals(entityAnnotationList.get(0),exportRowDataModel.getEntities().get(0));
    }
    @Test
    @Order(4)
    void setContentTest(){
        exportRowDataModel.setContent("Content Updated");
        assertEquals("Content Updated",exportRowDataModel.getContent());
    }
    @Test
    @Order(5)
    void setClassificationTest(){
        exportRowDataModel.setClassification("Classification Updated");
        assertEquals("Classification Updated",exportRowDataModel.getClassification());
    }
    @Test
    @Order(6)
    void setEntitiesTest(){
        ArrayList<EntityAnnotation> entityAnnotations = new ArrayList<>(List.of(new EntityAnnotation(3,10,"Positive"),new EntityAnnotation(5,15,"Negative")));
        exportRowDataModel.setEntities(entityAnnotations);
        assertEquals(entityAnnotations.get(0).getEntity(),exportRowDataModel.getEntities().get(0).getEntity());
    }
}