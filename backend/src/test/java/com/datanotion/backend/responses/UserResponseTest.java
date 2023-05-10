package com.datanotion.backend.responses;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class UserResponseTest {

    @Test
    void UserResponseObjectTest() {
        UserResponse userResponse = new UserResponse(1, "temp user", "temp@temp.com", "manager");

        assertEquals(1, userResponse.getId());
        assertEquals("temp user", userResponse.getFullName());
        assertEquals("temp@temp.com", userResponse.getEmail());
        assertEquals("manager", userResponse.getRole());
    }

    @Test
    void userResponseGetterSetterTest(){
        UserResponse userResponse = new UserResponse(1, "temp user", "temp@temp.com", "manager");
        userResponse.setId(1);
        userResponse.setFullName("new user");
        userResponse.setEmail("newtemp@gmail.com");
        userResponse.setRole("new manager");

        assertEquals(1, userResponse.getId());
        assertEquals("new user", userResponse.getFullName());
        assertEquals("newtemp@gmail.com", userResponse.getEmail());
        assertEquals("new manager", userResponse.getRole());
    }
}
