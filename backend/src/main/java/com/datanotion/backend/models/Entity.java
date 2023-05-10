package com.datanotion.backend.models;

import com.datanotion.backend.repositories.IEntityRepository;
import com.datanotion.backend.responses.GetMappingRequestResponse;
import java.util.List;

public class Entity {
    private int start;
    private int end;
    private int tagId;
    private int dataTaskId;

    public Entity(int start, int end, int entity, int dataTaskId) {
        this.start = start;
        this.end = end;
        this.tagId = entity;
        this.dataTaskId = dataTaskId;
    }

    public static List<GetMappingRequestResponse> fetchAllEntityByTask(IEntityRepository entityRepository,
            int projectId, int tagId) {
        return entityRepository.getAllEntityByTask(projectId, tagId);
    }

    public static int createEntityByTask(IEntityRepository entityRepository, int projectId, int taskId,
            List<Entity> newEntity) {
        return entityRepository.addEntityByTask(projectId, taskId, newEntity);
    }

    public static int removeEntityByTask(IEntityRepository entityRepository, int projectId, int taskId) {
        return entityRepository.deleteEntityByTask(projectId, taskId);
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public void setDataTaskId(int dataTaskId) {
        this.dataTaskId = dataTaskId;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getTagId() {
        return tagId;
    }

    public int getDataTaskId() {
        return dataTaskId;
    }
}
