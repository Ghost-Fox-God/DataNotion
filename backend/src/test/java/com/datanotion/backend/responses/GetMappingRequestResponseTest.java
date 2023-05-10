package com.datanotion.backend.responses;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class GetMappingRequestResponseTest {
    @Test
    void getMappingRequestResponseObjectTest() {
        GetMappingRequestResponse getMappingRequestResponse = new GetMappingRequestResponse(1, 2, 3, "tag name");

        assertEquals(1, getMappingRequestResponse.getStart());
        assertEquals(2, getMappingRequestResponse.getEnd());
        assertEquals(3, getMappingRequestResponse.getTagId());
        assertEquals("tag name", getMappingRequestResponse.getTag());
    }
    @Test
    void getMappingRequestResponseGetterSetterTest() {
        GetMappingRequestResponse getMappingRequestResponse = new GetMappingRequestResponse(1, 2, 3, "tag name");
        getMappingRequestResponse.setStart(2);
        getMappingRequestResponse.setEnd(3);
        getMappingRequestResponse.setTagId(4);
        getMappingRequestResponse.setTag("new tag");

        assertEquals(2, getMappingRequestResponse.getStart());
        assertEquals(3, getMappingRequestResponse.getEnd());
        assertEquals(4, getMappingRequestResponse.getTagId());
        assertEquals("new tag", getMappingRequestResponse.getTag());
    }
}
