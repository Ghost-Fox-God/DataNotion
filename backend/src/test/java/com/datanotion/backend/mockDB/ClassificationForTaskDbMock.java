package com.datanotion.backend.mockDB;

import com.datanotion.backend.models.ClassificationTag;
import com.datanotion.backend.models.Task;
import com.datanotion.backend.repositories.IClassificationForTaskRepository;
import com.datanotion.backend.requests.ClassificationAdd;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClassificationForTaskDbMock implements IClassificationForTaskRepository {

    private final ArrayList<Task> tasks = new ArrayList<>(List.of(
            new Task(1, "Task - 1", 2, 2, 1), new Task(2, "Task - 2", 1, 1, 2), new Task(3, "Task - 3", 2, 2, 1),
            new Task(4, "Task - 4", 1, 1, 2), new Task(5, "Task - 5", 2, 2, 1), new Task(6, "Task - 6", 1, 1, 2)));

    private final ArrayList<ClassificationTag> classificationTags = new ArrayList<>(
            List.of(new ClassificationTag(1, "Positive", 1), new ClassificationTag(2, "Negative", 2),
                    new ClassificationTag(3, "Sad", 3), new ClassificationTag(4, "Happy", 2)));

    @Override
    public List<ClassificationTag> getClassificationOfTask(int taskId) {
        List<Task> task = tasks.stream().filter(tasks -> tasks.getId() == taskId).collect(Collectors.toList());
        List<ClassificationTag> responseTag = new ArrayList<>();
        for(ClassificationTag classificationTag : classificationTags){
            for(Task task1 : task){
                if(task1.getClassificationId() == classificationTag.getId()){
                    responseTag.add(classificationTag);
                }
            }
        }
        return responseTag;
    }

    @Override
    public int addClassificationForTask(int taskId, ClassificationAdd classificationTagID) {
        for (Task t : tasks) {
            if (t.getId() == taskId) {
                t.setClassificationId(classificationTagID.getClassificationId());
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int deleteClassificationOfTask(int taskId) {
        for (Task t : tasks) {
            if (t.getId() == taskId) {
                t.setClassificationId(0);
                return 1;
            }
        }
        return 0;
    }
}
