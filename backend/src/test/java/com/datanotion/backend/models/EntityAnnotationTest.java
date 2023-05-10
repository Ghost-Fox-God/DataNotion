package com.datanotion.backend.models;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityAnnotationTest {
    EntityAnnotation entityAnnotation = new EntityAnnotation(1,5,"Happy");

    @Test
    @Order(1)
    void setStartTest(){
        entityAnnotation.setStart(3);
        assertEquals(3,entityAnnotation.getStart());
    }
    @Test
    @Order(2)
    void setEndTest(){
        entityAnnotation.setEnd(3);
        assertEquals(3,entityAnnotation.getEnd());
    }
    @Test
    @Order(3)
    void setEntityTest(){
        entityAnnotation.setEntity("Sad");
        assertEquals("Sad",entityAnnotation.getEntity());
    }
    @Test
    @Order(4)
    void getStartTest(){
        assertEquals(1,entityAnnotation.getStart());
    }
    @Test
    @Order(5)
    void getEndTest(){
        assertEquals(5,entityAnnotation.getEnd());
    }
    @Test
    @Order(6)
    void getEntityTest(){
        assertEquals("Happy",entityAnnotation.getEntity());
    }
}
