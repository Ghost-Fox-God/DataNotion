package com.datanotion.backend.controllers;

import com.datanotion.backend.requests.TagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.datanotion.backend.models.ClassificationTag;
import com.datanotion.backend.repositories.ClassificationTagRepository;

import java.util.List;

@RestController
@RequestMapping("/projects/{id}")
public class ClassificationTagController {
    @Autowired
    ClassificationTagRepository classificationTagRepository;

    @GetMapping("/classification")
    public List<ClassificationTag> getClassificationTagsByProjectId(@PathVariable("id") int projectId) {
        return ClassificationTag.getAllClassificationTagsByProjectId(classificationTagRepository, projectId);
    }

    @GetMapping("/classification/{classificationTagId}")
    public ClassificationTag getClassificationTagById(@PathVariable("classificationTagId") int id) {
        return ClassificationTag.getClassificationTagById(classificationTagRepository, id);
    }

    @PostMapping("/classification")
    public int createClassificationTag(@RequestBody TagRequest tagRequest) {
        return ClassificationTag.createClassificationTag(classificationTagRepository, tagRequest);
    }

    @DeleteMapping("/classification/{classificationTagId}")
    public int deleteClassificationTag(@PathVariable("classificationTagId") int id) {
        return ClassificationTag.deleteClassificationTag(classificationTagRepository, id);
    }
}
