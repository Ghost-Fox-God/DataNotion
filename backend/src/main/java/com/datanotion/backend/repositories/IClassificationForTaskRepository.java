package com.datanotion.backend.repositories;

import com.datanotion.backend.models.Tag;
import com.datanotion.backend.requests.ClassificationAdd;

import java.util.List;

public interface IClassificationForTaskRepository {
    List<? extends Tag> getClassificationOfTask(int taskId);

    int addClassificationForTask(int taskId, ClassificationAdd classificationTagID);

    int deleteClassificationOfTask(int taskId);
}
