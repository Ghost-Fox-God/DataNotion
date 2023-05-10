package com.datanotion.backend.mockDB;

import com.datanotion.backend.models.ClassificationTag;
import com.datanotion.backend.repositories.IClassificationTagRepository;
import com.datanotion.backend.requests.TagRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClassificationTagDbMock implements IClassificationTagRepository {

    private final List<ClassificationTag> classificationTags = new ArrayList<>(
            List.of(new ClassificationTag(1, "test tag", 1), new ClassificationTag(2, "test tag 2", 1),
                    new ClassificationTag(3, "test tag 3", 2)
            ));

    @Override
    public List<ClassificationTag> getAllClassificationTagsByProjectId(int projectId) {
        return classificationTags.stream().filter(classificationTag -> classificationTag.getProjectId() == projectId)
                .collect(
                        Collectors.toList());
    }

    @Override
    public ClassificationTag getClassificationTagById(int id) {
        return classificationTags.stream().filter(classificationTag -> classificationTag.getId() == id).findAny()
                .orElse(null);
    }

    @Override
    public int createClassificationTag(TagRequest tagRequest) {
        boolean b = classificationTags.add(new ClassificationTag(4, tagRequest.getTagName(), 2));
        ClassificationTag tag = classificationTags.stream()
                .filter(classificationTag -> classificationTag.getTagName().equals(tagRequest.getTagName())).findAny()
                .orElse(null);
        if (tag != null && tag.getTagName().equals(tagRequest.getTagName())) {
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteClassificationTag(int id) {
        ClassificationTag tag1 = classificationTags.stream()
                .filter(classificationTag -> classificationTag.getId() == id).findAny()
                .orElse(null);
        if (tag1 == null) {
            return 0;
        } else {
            classificationTags.remove(tag1);
            ClassificationTag tag = classificationTags.stream()
                    .filter(classificationTag -> classificationTag.getId() == id).findAny()
                    .orElse(null);
            if (tag == null) {
                return 1;
            } else {
                return 0;
            }
        }

    }
}
