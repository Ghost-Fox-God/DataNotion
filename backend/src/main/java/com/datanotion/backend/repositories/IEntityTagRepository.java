package com.datanotion.backend.repositories;

import java.util.List;

import com.datanotion.backend.models.EntityTag;
import com.datanotion.backend.models.Tag;
import com.datanotion.backend.requests.TagRequest;

public interface IEntityTagRepository {
    List<? extends Tag> getEntityTagsByProjectId(int id);

    EntityTag getEntityTagById(int id);

    int createEntityTag(TagRequest tagRequest);

    int deleteEntityTag(int id);
}
