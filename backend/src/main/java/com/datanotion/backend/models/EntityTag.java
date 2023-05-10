package com.datanotion.backend.models;

import java.util.List;

import com.datanotion.backend.repositories.IEntityTagRepository;
import com.datanotion.backend.requests.TagRequest;

public class EntityTag extends Tag {
    private int id;
    private int projectId;

    public EntityTag(int id, String tagName, int projectId) {
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
        return this.tagName;
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

    public static List<EntityTag> getEntityTagsByProjectId(IEntityTagRepository entityTagRepository, int id) {
        return (List<EntityTag>) entityTagRepository.getEntityTagsByProjectId(id);
    }

    public static EntityTag getEntityTagById(IEntityTagRepository entityTagRepository, int id) {
        return entityTagRepository.getEntityTagById(id);
    }

    public static int createEntityTag(IEntityTagRepository entityTagRepository, TagRequest tagRequest) {
        return entityTagRepository.createEntityTag(tagRequest);
    }

    public static int deleteEntityTag(IEntityTagRepository entityTagRepository, int id) {
        return entityTagRepository.deleteEntityTag(id);
    }
}
