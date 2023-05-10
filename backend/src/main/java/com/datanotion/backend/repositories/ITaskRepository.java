package com.datanotion.backend.repositories;

import com.datanotion.backend.models.Task;
import com.datanotion.backend.models.User;
import com.datanotion.backend.responses.GetTaskRequestResponse;

import java.util.List;

public interface ITaskRepository {
     List<GetTaskRequestResponse> getAllTaskByUser(User user, int projectId);

     List<GetTaskRequestResponse> getTaskOfProject(int taskId, int projectId);

     int deleteTask(int taskId, int projectId);

     Task createTask(Task task);
}
