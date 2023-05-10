package com.datanotion.backend.controllers;

import com.datanotion.backend.models.ClassificationTag;
import com.datanotion.backend.models.Tag;
import com.datanotion.backend.repositories.ClassificationForTaskRepository;
import com.datanotion.backend.requests.ClassificationAdd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projects/{projectId}")
public class ClassificationController {
    @Autowired
    ClassificationForTaskRepository classificationForTaskRepository;

    @GetMapping("/tasks/{taskId}/classification")
    public List<? extends Tag> getClassificationOfTask(@PathVariable("taskId") int taskId){
        return ClassificationTag.fetchClassificationOfTask(classificationForTaskRepository, taskId);
    }

    @PostMapping("/tasks/{taskId}/classification")
    public int addClassificationOfTask(@PathVariable("taskId") int taskId, @RequestBody ClassificationAdd classificationID){
        return ClassificationTag.createClassificationForTask(classificationForTaskRepository,taskId,classificationID);
    }

    @DeleteMapping("/tasks/{taskId}/classification")
    public int deleteClassificationOfTask(@PathVariable("taskId") int taskId){
        return ClassificationTag.removeClassificationOfTask(classificationForTaskRepository,taskId);
    }
}
