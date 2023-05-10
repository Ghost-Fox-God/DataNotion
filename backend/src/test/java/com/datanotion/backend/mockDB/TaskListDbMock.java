package com.datanotion.backend.mockDB;

import com.datanotion.backend.models.Task;
import com.datanotion.backend.models.User;
import com.datanotion.backend.repositories.ITaskRepository;
import com.datanotion.backend.responses.GetTaskRequestResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TaskListDbMock implements ITaskRepository {
    List<Task> tasks;

    public TaskListDbMock() {
        tasks = new ArrayList<>();
        Task t1 = new Task(1, "Task - 1", 2, 2, 1);
        Task t2 = new Task(2, "Task - 2", 1, 1, 2);
        Task t3 = new Task(3, "Task - 3", 2, 2, 1);
        Task t4 = new Task(4, "Task - 4", 1, 1, 2);
        Task t5 = new Task(5, "Task - 5", 2, 2, 1);
        Task t6 = new Task(6, "Task - 6", 1, 1, 2);
        Task t7 = new Task(7, "Task - 7", 1, 2, 3);
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        tasks.add(t4);
        tasks.add(t5);
        tasks.add(t6);
        tasks.add(t7);

    }

    @Override
    public List<GetTaskRequestResponse> getAllTaskByUser(User user, int projectID) {
        List<GetTaskRequestResponse> responseList = new ArrayList<>();
        for (Task t : tasks) {
            if (user != null && user.getId() == t.getUserId() && projectID == t.getProjectId()) {
                responseList.add(new GetTaskRequestResponse(t.getId(), t.getContent(), t.getClassificationId(),
                        t.getProjectId(), t.getUserId(), "Positive"));
            }
        }
        return responseList;
    }

    @Override
    public List<GetTaskRequestResponse> getTaskOfProject(int taskId, int projectId) {
        List<GetTaskRequestResponse> responseList = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getProjectId() == projectId && t.getId() == taskId) {
                responseList.add(new GetTaskRequestResponse(t.getId(), t.getContent(), t.getClassificationId(),
                        t.getProjectId(), t.getUserId(), "Positive"));
            }
        }
        return responseList;
    }

    @Override
    public int deleteTask(int taskId, int projectId) {
        for (Task t : tasks) {
            if (t.getProjectId() == projectId && t.getId() == taskId) {
                Arrays.asList().remove(t);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public Task createTask(Task task) {
        return null;
    }
}
