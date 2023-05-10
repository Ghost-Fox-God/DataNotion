package com.datanotion.backend.repositories;

import java.util.List;

import com.datanotion.backend.models.ClassificationTag;
import com.datanotion.backend.requests.TagRequest;

public interface IClassificationTagRepository {
    List<ClassificationTag> getAllClassificationTagsByProjectId(int projectId);

    ClassificationTag getClassificationTagById(int id);

    int createClassificationTag(TagRequest tagRequest);

    int deleteClassificationTag(int id);
}
