package com.datanotion.backend.requests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRegistrationRequestTest {
    UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("DataNotion@gmail.com","DataNotion@123","Jainil","Sevalia","Manager");

    @Test
    @Order(1)
    void setPasswordTest(){
        userRegistrationRequest.setPassword("Pass@123");
        assertEquals("Pass@123",userRegistrationRequest.getPassword());
    }
    @Test
    @Order(2)
    void setEmailTest(){
        userRegistrationRequest.setEmail("Data123@notion.com");
        assertEquals("Data123@notion.com",userRegistrationRequest.getEmail());
    }
    @Test
    @Order(3)
    void setFirstNameTest(){
        userRegistrationRequest.setFirstName("JACK");
        assertEquals("JACK",userRegistrationRequest.getFirstName());
    }
    @Test
    @Order(4)
    void setLastNameTest(){
        userRegistrationRequest.setLastName("Rob");
        assertEquals("Rob",userRegistrationRequest.getLastName());
    }
    @Test
    @Order(5)
    void getPasswordTest(){
        assertEquals("DataNotion@123",userRegistrationRequest.getPassword());
    }
    @Test
    @Order(6)
    void getEmailTest(){
        assertEquals("DataNotion@gmail.com",userRegistrationRequest.getEmail());
    }
    @Test
    @Order(7)
    void getFirstNameTest(){
        assertEquals("Jainil",userRegistrationRequest.getFirstName());
    }
    @Test
    @Order(8)
    void getLastNameTest(){
        assertEquals("Sevalia",userRegistrationRequest.getLastName());
    }
    @Test
    @Order(9)
    void setRoleTest(){
        userRegistrationRequest.setRole("Annotator");
        assertEquals("Annotator",userRegistrationRequest.getRole());
    }
    @Test
    @Order(10)
    void getRoleTest(){
        assertEquals("Manager",userRegistrationRequest.getRole());
    }
}