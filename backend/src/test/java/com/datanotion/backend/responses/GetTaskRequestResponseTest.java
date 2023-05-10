package com.datanotion.backend.responses;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class GetTaskRequestResponseTest {

    @Test
    void getTaskRequestResponseObjectTest() {
        GetTaskRequestResponse getTaskRequestResponse = new GetTaskRequestResponse(1, "task content", 1, 1, 1,
                                                                                   "classification tag"
        );
        assertEquals(1, getTaskRequestResponse.getTaskId());
        assertEquals("task content", getTaskRequestResponse.getTaskContent());
        assertEquals(1, getTaskRequestResponse.getClassificationId());
        assertEquals(1, getTaskRequestResponse.getProjectId());
        assertEquals(1, getTaskRequestResponse.getUserId());
        assertEquals("classification tag", getTaskRequestResponse.getClassificationTag());
    }
    @Test
    void getTaskRequestResponseGetterSetterTest() {
        GetTaskRequestResponse getTaskRequestResponse = new GetTaskRequestResponse(1, "task content", 1, 1, 2,
                                                                                   "classification tag"
        );
        getTaskRequestResponse.setTaskId(2);
        getTaskRequestResponse.setClassificationId(2);
        getTaskRequestResponse.setProjectId(2);
        getTaskRequestResponse.setUserId(2);
        getTaskRequestResponse.setTaskContent("new content");
        getTaskRequestResponse.setClassificationTag("new tag");

        assertEquals(2, getTaskRequestResponse.getTaskId());
        assertEquals("new content", getTaskRequestResponse.getTaskContent());
        assertEquals(2, getTaskRequestResponse.getClassificationId());
        assertEquals(2, getTaskRequestResponse.getProjectId());
        assertEquals(2, getTaskRequestResponse.getUserId());
        assertEquals("new tag", getTaskRequestResponse.getClassificationTag());
    }
}
