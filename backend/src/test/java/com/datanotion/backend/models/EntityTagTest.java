package com.datanotion.backend.models;

import com.datanotion.backend.mockDB.EntityTagDbMock;
import com.datanotion.backend.requests.TagRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityTagTest {
    @Autowired
    private EntityTagDbMock entityTagDbMock;

    @Test
    @Order(1)
    void entityTagObjectTest() {
        EntityTag tag = new EntityTag(1, "test entity", 2);

        assertEquals(1, tag.getId());
        assertEquals("test entity", tag.getTagName());
        assertEquals(2, tag.getProjectId());
    }

    @Test
    @Order(2)
    void entityTagGetterSetterTest() {
        EntityTag tag = new EntityTag(1, "test entity", 2);
        tag.setId(2);
        tag.setTagName("new test");
        tag.setProjectId(1);

        assertEquals(2, tag.getId());
        assertEquals("new test", tag.getTagName());
        assertEquals(1, tag.getProjectId());
    }

    @Test
    @Order(3)
    void getEntityTagsByProjectIdSuccessTest() {
        List<EntityTag> entities = EntityTag.getEntityTagsByProjectId(entityTagDbMock, 1);

        assertEquals(2, entities.size());
        assertEquals(1, entities.get(1).getProjectId());

    }

    @Test
    @Order(4)
    void getEntityTagsByProjectIdFailureTest() {
        List<EntityTag> entities = EntityTag.getEntityTagsByProjectId(entityTagDbMock, 5);
        assertEquals(0, entities.size());
    }

    @Test
    @Order(5)
    void getEntityTagByIdSuccessTest() {
        EntityTag tag = EntityTag.getEntityTagById(entityTagDbMock,1);

        assertEquals(1, tag.getId());
        assertEquals("test tag", tag.getTagName());
        assertEquals(1, tag.getProjectId());
    }

    @Test
    @Order(6)
    void getEntityTagByIdFailureTest() {
        EntityTag tag = EntityTag.getEntityTagById(entityTagDbMock,5);

        assertNull(tag);
    }

    @Test
    @Order(7)
    void createEntityTagSuccessTest() {
        int outputResult = EntityTag.createEntityTag(entityTagDbMock, new TagRequest("test entitytag", 1));

        assertEquals(1, outputResult);
    }

    @Test
    @Order(8)
    void createEntityTagFailureTest() {
        int outputResult = EntityTag.createEntityTag(entityTagDbMock, new TagRequest("test tag", 1));

        assertEquals(0, outputResult);
    }

    @Test
    @Order(9)
    void deleteEntityTagSuccessTest() {
        int outputResult = EntityTag.deleteEntityTag(entityTagDbMock, 2);
        assertEquals(1, outputResult);
    }

    @Test
    void deleteEntityTagFailureTest() {
        int outputResult = EntityTag.deleteEntityTag(entityTagDbMock, 5);

        assertEquals(0, outputResult);
    }
}
