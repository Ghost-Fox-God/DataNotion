package com.datanotion.backend.models;

import com.datanotion.backend.exceptions.BadRequestException;
import com.datanotion.backend.mockDB.UserDbMock;
import com.datanotion.backend.requests.UserRegistrationRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

    @Autowired
    private UserDbMock userDbMock;

    @Test
    @Order(1)
    void userObjectSuccessTest() {
        User user = new User(1, "test", "patel", "test@gmail.com", "passtest", "manager");
        assertEquals(1, user.getId());
        assertEquals("test", user.getFirstName());
        assertEquals("patel", user.getLastName());
        assertEquals("test@gmail.com", user.getEmail());
        assertEquals("passtest", user.getPassword());
        assertEquals("manager", user.getRole());
    }

    @Test
    @Order(2)
    void registerUserSuccessTest() throws BadRequestException {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("test@gmail.com", "test", "doe",
                                                                                      "john", "manager"
        );
        User user = User.saveUser(userDbMock, userRegistrationRequest);
        assertEquals("doe", user.getFirstName());
        assertEquals("john", user.getLastName());
        assertEquals("test@gmail.com", user.getEmail());
        assertEquals("test", user.getPassword());
        assertEquals("manager", user.getRole());
    }

    @Test
    @Order(3)
    void registerUserFailureTest() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("test3@gmail.com", "test", "doe",
                                                                                      "john", "manager"
        );
        assertThrows(BadRequestException.class, () -> User.saveUser(userDbMock, userRegistrationRequest));

    }

    @Test
    @Order(4)
    void getAllAnnotatorsSuccessTest() {
        List<User> users = User.getAllAnnotators(userDbMock);
        assertEquals(2, users.size());
    }

    @Test
    @Order(5)
    void getAllAnnotatorsFailureTest() {
        List<User> users = User.getAllAnnotators(userDbMock);
        assertNotEquals(1, users.size());
    }

    @Test
    @Order(6)
    void getAllManagersSuccessTest() {
        List<User> users = User.getAllManagers(userDbMock);
        assertEquals(2, users.size());
    }

    @Test
    @Order(7)
    void getAllManagersFailureTest() {
        List<User> users = User.getAllManagers(userDbMock);
        assertNotEquals(1, users.size());
    }

    @Test
    @Order(8)
    void getUserByEmailSuccessTest() {
        User user = User.getUserByEmail(userDbMock, "test3@gmail.com");
        assertEquals("test3", user.getFirstName());
    }

    @Test
    @Order(9)
    void getUserByEmailFailureTest() {
        assertThrows(UsernameNotFoundException.class, () -> User.getUserByEmail(userDbMock, "test4@gmail.com"));
    }

    @Test
    @Order(10)
    void getUsersByProjectIdSuccessTest() {
        List<User> users = User.getUsersByProjectId(userDbMock, 1);
        assertEquals(2, users.size());
    }
    @Test
    @Order(11)
    void getAnnotatorsByProjectIdSuccessTest() {
        List<User> users = User.getAnnotatorsByProjectId(userDbMock, 1);
        assertEquals(2, users.size());
    }
    @Test
    @Order(12)
    void getManagersByProjectIdSuccessTest() {
        List<User> users = User.getManagersByProjectId(userDbMock, 1);
        assertEquals(0, users.size());
    }

    @Test
    @Order(13)
    void userObjectGetterSetterTest() {
        User user = new User(1, "test", "patel", "test@gmail.com", "passtest", "manager");
        user.setId(2);
        user.setFirstName("temp");
        user.setLastName("newtemp");
        user.setEmail("newtest@test.com");
        user.setPassword("newpassword");
        user.setRole("newrole");

        assertEquals(2, user.getId());
        assertEquals("temp", user.getFirstName());
        assertEquals("newtemp", user.getLastName());
        assertEquals("newtest@test.com", user.getEmail());
        assertEquals("newpassword", user.getPassword());
        assertEquals("newrole", user.getRole());
    }

    @Test
    @Order(14)
    void getUserByIdSuccessTest() {
        User user = User.getUserById(userDbMock, 1);
        assertEquals(1, user.getId());
        assertEquals("test1", user.getFirstName());
    }
    @Test
    @Order(15)
    void getUserByIdFailureTest() {
        assertThrows(UsernameNotFoundException.class, () -> User.getUserById(userDbMock, 5));

    }

}
