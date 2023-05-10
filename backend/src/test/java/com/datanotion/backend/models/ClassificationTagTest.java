package com.datanotion.backend.models;

import com.datanotion.backend.mockDB.ClassificationForTaskDbMock;
import com.datanotion.backend.mockDB.ClassificationTagDbMock;
import com.datanotion.backend.requests.ClassificationAdd;
import com.datanotion.backend.requests.TagRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClassificationTagTest {
    @Autowired
    private ClassificationTagDbMock classificationTagDbMock;
    @Autowired
    ClassificationForTaskDbMock classification;

    @Test
    @Order(1)
    void classificationTagObjectTest() {
        ClassificationTag tag = new ClassificationTag(1, "testing classification tag", 2);

        assertEquals(1, tag.getId());
        assertEquals("testing classification tag", tag.getTagName());
        assertEquals(2, tag.getProjectId());
    }

    @Test
    @Order(2)
    void classificationTagGetterSetterTest() {
        ClassificationTag tag = new ClassificationTag(1, "testing classification tag", 2);
        tag.setId(2);
        tag.setTagName("new test");
        tag.setProjectId(1);

        assertEquals(2, tag.getId());
        assertEquals("new test", tag.getTagName());
        assertEquals(1, tag.getProjectId());
    }

    @Test
    @Order(3)
    void getAllClassificationTagsByProjectIdSuccessTest() {
        List<ClassificationTag> classificationTags = ClassificationTag.getAllClassificationTagsByProjectId(
                classificationTagDbMock, 1);

        assertEquals(2, classificationTags.size());
    }

    @Test
    @Order(4)
    void getAllClassificationTagsByProjectIdFailureTest() {
        List<ClassificationTag> classificationTags = ClassificationTag.getAllClassificationTagsByProjectId(
                classificationTagDbMock, 5);

        assertEquals(0, classificationTags.size());
    }

    @Test
    @Order(5)
    void createClassificationTagSuccessTest() {
        int outputResult = ClassificationTag.createClassificationTag(classificationTagDbMock,
                                                                     new TagRequest("Test classidfication", 1)
        );
        assertEquals(1, outputResult);
    }

    @Test
    @Order(6)
    void deleteClassificationTagSuccessTest() {
        int outputResult = ClassificationTag.deleteClassificationTag(classificationTagDbMock, 1);
        assertEquals(1, outputResult);
    }
    @Test
    @Order(7)
    public void fetchClassificationTagOfTaskSuccessTest() {
        List<? extends Tag> response = ClassificationTag.fetchClassificationOfTask(classification, 3);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("Negative",response.get(0).getTagName());
    }

    @Test
    @Order(8)
    public void fetchClassificationTagOfTaskFailureTest() {
        List<? extends Tag> response = ClassificationTag.fetchClassificationOfTask(classification, 8);
        Assertions.assertNotEquals(1, response.size());
    }

    @Test
    @Order(9)
    public void createClassificationOfTaskSuccessTest() {
        int response = ClassificationTag.createClassificationForTask(classification, 2, new ClassificationAdd(2));
        Assertions.assertEquals(1, response);
    }

    @Test
    @Order(10)
    public void createClassificationOfTaskFailureTest() {
        int response = ClassificationTag.createClassificationForTask(classification, 7, new ClassificationAdd(2));
        Assertions.assertNotEquals(1, response);
    }

    @Test
    @Order(11)
    public void removeClassificationOfTaskSuccessTest() {
        int response = ClassificationTag.removeClassificationOfTask(classification, 4);
        Assertions.assertEquals(1, response);
    }

    @Test
    @Order(12)
    public void removeClassificationOfTaskFailureTest() {
        int response = ClassificationTag.removeClassificationOfTask(classification, 8);
        Assertions.assertNotEquals(1, response);
    }

    @Test
    @Order(7)
    void getClassificationTagByIdSuccessTest() {
        ClassificationTag outputResult = ClassificationTag.getClassificationTagById(classificationTagDbMock, 3);
        assertEquals(3, outputResult.getId());
    }

    @Test
    @Order(7)
    void getClassificationTagByIdFailureTest() {
        ClassificationTag outputResult = ClassificationTag.getClassificationTagById(classificationTagDbMock, 6);
        assertNull(outputResult);
    }

}
