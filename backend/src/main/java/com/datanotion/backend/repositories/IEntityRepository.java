package com.datanotion.backend.repositories;

import com.datanotion.backend.models.Entity;
import com.datanotion.backend.responses.GetMappingRequestResponse;

import java.util.List;

public interface IEntityRepository {
    List<GetMappingRequestResponse> getAllEntityByTask(int projectId, int taskId);

    int addEntityByTask(int projectId, int taskId, List<Entity> newEntity);

    int deleteEntityByTask(int projectId, int taskId);
}
