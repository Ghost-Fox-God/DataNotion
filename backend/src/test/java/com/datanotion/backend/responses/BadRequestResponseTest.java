package com.datanotion.backend.responses;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class BadRequestResponseTest {
    @Test
    void badRequestResponseObjectTest(){
        BadRequestResponse badRequestResponse = new BadRequestResponse("not found");
        assertEquals("not found",badRequestResponse.getMessage());
    }
    @Test
    void badRequestResponseGetterSetterTest(){
        BadRequestResponse badRequestResponse = new BadRequestResponse("not found");
        badRequestResponse.setMessage("new not found");
        assertEquals("new not found",badRequestResponse.getMessage());
    }
}
