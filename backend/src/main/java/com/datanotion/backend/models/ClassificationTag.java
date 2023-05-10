package com.datanotion.backend.models;

import java.util.List;

import com.datanotion.backend.repositories.IClassificationForTaskRepository;
import com.datanotion.backend.repositories.IClassificationTagRepository;
import com.datanotion.backend.requests.ClassificationAdd;
import com.datanotion.backend.requests.TagRequest;

public class ClassificationTag extends Tag {
    private int id;
    private int projectId;

    public ClassificationTag(int id, String tagName, int projectId) {
        super(tagName);
        this.id = id;
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public static List<ClassificationTag> getAllClassificationTagsByProjectId(
            IClassificationTagRepository classificationTagRepository, int projectId) {
        return classificationTagRepository.getAllClassificationTagsByProjectId(projectId);
    }

    public static ClassificationTag getClassificationTagById(IClassificationTagRepository classificationTagRepository,
            int classificationTagId) {
        return classificationTagRepository.getClassificationTagById(classificationTagId);
    }

    public static int createClassificationTag(IClassificationTagRepository classificationTagRepository,
            TagRequest tagRequest) {
        return classificationTagRepository.createClassificationTag(tagRequest);
    }

    public static int deleteClassificationTag(IClassificationTagRepository classificationTagRepository, int id) {
        return classificationTagRepository.deleteClassificationTag(id);
    }

    public static List<? extends Tag> fetchClassificationOfTask(IClassificationForTaskRepository classification,
            int taskId) {
        return classification.getClassificationOfTask(taskId);
    }

    public static int createClassificationForTask(IClassificationForTaskRepository classification, int taskId,
            ClassificationAdd classificationId) {
        return classification.addClassificationForTask(taskId, classificationId);
    }

    public static int removeClassificationOfTask(IClassificationForTaskRepository classification, int taskId) {
        return classification.deleteClassificationOfTask(taskId);
    }
}
