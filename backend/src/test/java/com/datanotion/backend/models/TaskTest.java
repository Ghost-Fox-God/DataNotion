package com.datanotion.backend.models;

import com.datanotion.backend.mockDB.TaskListDbMock;
import com.datanotion.backend.mockDB.UserDbMock;
import com.datanotion.backend.responses.GetTaskRequestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class TaskTest {
    @Autowired
    private UserDbMock userDbMock;
    @Autowired
    private TaskListDbMock taskRepository;

    @Test
    public void fetchAllTaskByUserSuccessTest() {
        User user = User.getUserByEmail(userDbMock, "test3@gmail.com");
        List<GetTaskRequestResponse> res = Task.fetchAllTaskByUser(taskRepository, user, 2);
        Assertions.assertEquals(1, res.size());
        Assertions.assertEquals(1, res.get(0).getClassificationId());
        Assertions.assertEquals("Task - 7", res.get(0).getTaskContent());
    }

    @Test
    public void fetchAllTaskByTaskFailureTest() {
        User user = User.getUserByEmail(userDbMock, "test3@gmail.com");
        List<GetTaskRequestResponse> res = Task.fetchAllTaskByUser(taskRepository, user, 1);
        Assertions.assertNotEquals(1, res.size());
    }

    @Test
    public void fetchTaskOfProjectSuccessTest() {
        List<GetTaskRequestResponse> res = Task.fetchTaskOfProject(taskRepository, 1, 2);
        Assertions.assertEquals(1, res.size());
        Assertions.assertEquals("Task - 1", res.get(0).getTaskContent());
    }

    @Test
    public void fetchTaskOfProjectFailureTest() {
        List<GetTaskRequestResponse> res = Task.fetchTaskOfProject(taskRepository, 2, 2);
        Assertions.assertNotEquals(1, res.size());
    }

    @Test
    public void deleteTaskOfProjectSuccessTest() {
        int res = Task.deleteTaskOfProject(taskRepository, 1, 2);
        Assertions.assertEquals(1, res);
    }

    @Test
    public void deleteTaskOfProjectFailureTest() {
        int res = Task.deleteTaskOfProject(taskRepository, 2, 2);
        Assertions.assertNotEquals(1, res);
    }
}
