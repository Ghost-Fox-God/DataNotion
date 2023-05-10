package com.datanotion.backend.mockDB;

import com.datanotion.backend.models.EntityTag;
import com.datanotion.backend.models.Tag;
import com.datanotion.backend.repositories.IEntityTagRepository;
import com.datanotion.backend.requests.TagRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EntityTagDbMock implements IEntityTagRepository {
    private final List<EntityTag> entityTags = new ArrayList<>(
            List.of(new EntityTag(1, "test tag", 1), new EntityTag(2, "test tag 2", 1),
                    new EntityTag(3, "test tag 3", 2)
            ));

    @Override
    public List<? extends Tag> getEntityTagsByProjectId(int id) {
        return entityTags.stream().filter(entityTag -> entityTag.getProjectId() == id)
                .collect(
                        Collectors.toList());
    }

    @Override
    public EntityTag getEntityTagById(int id) {
        return entityTags.stream().filter(entityTag -> entityTag.getId() == id).findAny()
                .orElse(null);
    }

    @Override
    public int createEntityTag(TagRequest tagRequest) {
        EntityTag entityTag1 = entityTags.stream()
                .filter(entityTag -> entityTag.getTagName()
                        .equals(tagRequest.getTagName()) && entityTag.getProjectId() == tagRequest.getProjectId())
                .findAny()
                .orElse(null);
        if (entityTag1 == null) {
            boolean b = entityTags.add(new EntityTag(4, tagRequest.getTagName(), 2));
            EntityTag tag = entityTags.stream()
                    .filter(entityTag -> entityTag.getTagName().equals(tagRequest.getTagName())).findAny()
                    .orElse(null);
            if (tag != null && tag.getTagName().equals(tagRequest.getTagName())) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int deleteEntityTag(int id) {
        EntityTag entityTag1 = entityTags.stream()
                .filter(entityTag -> entityTag.getId() == id).findAny()
                .orElse(null);
        if(entityTag1 == null){
            return 0;
        }
        else{
            entityTags.remove(entityTag1);
            EntityTag tag = entityTags.stream()
                    .filter(entityTag -> entityTag.getId() == id).findAny()
                    .orElse(null);
            if (tag == null) {
                return 1;
            } else {
                return 0;
            }
        }

    }
}
