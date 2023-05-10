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
public class ExportRowTest {
    ExportRow exportRow = new ExportRow("Content","Positive","sad,happy","2,3","8,9");
    ExportRow exportRowWithoutEntity = new ExportRow("Content","Positive",null,null,null);
    @Test
    @Order(1)
    void setContentTest(){
        exportRow.setContent("Content Updated");
        assertEquals("Content Updated",exportRow.getContent());
    }
    @Test
    @Order(2)
    void setClassificationTest(){
        exportRow.setClassification("Classification Updated");
        assertEquals("Classification Updated",exportRow.getClassification());
    }
    @Test
    @Order(3)
    void setEntityTest(){
        exportRow.setEntity("Entity Updated");
        assertEquals("Entity Updated",exportRow.getEntity());
    }
    @Test
    @Order(4)
    void setStartTest(){
        exportRow.setStart("8");
        assertEquals("8",exportRow.getStart());
    }
    @Test
    @Order(5)
    void setEndTest(){
        exportRow.setEnd("11");
        assertEquals("11",exportRow.getEnd());
    }

    @Test
    @Order(6)
    void getContentTest(){
        assertEquals("Content",exportRow.getContent());
    }
    @Test
    @Order(7)
    void getClassificationTest(){
        assertEquals("Positive",exportRow.getClassification());
    }
    @Test
    @Order(8)
    void getEntityTest(){
        assertEquals("sad,happy",exportRow.getEntity());
    }
    @Test
    @Order(9)
    void getStartTest(){
        assertEquals("2,3",exportRow.getStart());
    }
    @Test
    @Order(10)
    void getEndTest(){
        assertEquals("8,9",exportRow.getEnd());
    }

    @Test
    @Order(11)
    void toExportRowDataModelSuccessTest(){
        List<EntityAnnotation> entityList = new ArrayList<EntityAnnotation>();
        entityList.add(new EntityAnnotation(2,8,"sad"));
        entityList.add(new EntityAnnotation(3,9,"happy"));
        ExportRowDataModel withEntities = new ExportRowDataModel(exportRow.getContent(), exportRow.getClassification(), entityList);
        assertEquals(withEntities.getClassification(), exportRow.toExportRowDataModel().getClassification());
        assertEquals(withEntities.getContent(),exportRow.getContent());

        List<EntityAnnotation> entityListEmpty = new ArrayList<EntityAnnotation>();
        ExportRowDataModel withoutEntities = new ExportRowDataModel(exportRow.getContent(), exportRow.getClassification(), entityListEmpty);
        assertEquals(withoutEntities.getClassification(), exportRowWithoutEntity.toExportRowDataModel().getClassification());
        assertEquals(withoutEntities.getContent(),exportRowWithoutEntity.getContent());
    }
}


