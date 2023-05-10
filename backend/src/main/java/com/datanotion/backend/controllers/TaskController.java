package com.datanotion.backend.controllers;

import com.datanotion.backend.models.Task;
import com.datanotion.backend.models.User;
import com.datanotion.backend.repositories.TaskListRepository;

import com.datanotion.backend.repositories.UserRepository;
import com.datanotion.backend.responses.GetTaskRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}")
public class TaskController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskListRepository taskListRepository;

    @GetMapping("/tasks")
    public List<GetTaskRequestResponse> getAllTaskByUser(@PathVariable("projectId") int projectId) {
        User user = userRepository.getUserByEmail((String) SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal());

        return Task.fetchAllTaskByUser(taskListRepository, user, projectId);
    }

    @GetMapping("/tasks/{taskId}")
    public List<GetTaskRequestResponse> getTaskOfProject(@PathVariable("projectId") int projectId,
            @PathVariable("taskId") int taskId) {
        return Task.fetchTaskOfProject(taskListRepository, taskId, projectId);
    }

    @DeleteMapping("/tasks/{taskId}")
    public int deleteTask(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId) {
        return Task.deleteTaskOfProject(taskListRepository, taskId, projectId);
    }
}
