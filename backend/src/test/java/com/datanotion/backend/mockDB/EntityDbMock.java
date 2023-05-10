package com.datanotion.backend.mockDB;

import com.datanotion.backend.models.Entity;
import com.datanotion.backend.models.EntityTag;
import com.datanotion.backend.repositories.IEntityRepository;
import com.datanotion.backend.responses.GetMappingRequestResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EntityDbMock implements IEntityRepository {

    private final ArrayList<Entity> entityMapping = new ArrayList<>(
            List.of(new Entity(10, 15, 1, 1), new Entity(16, 20, 2, 2), new Entity(21, 25, 3, 3),
                    new Entity(26, 30, 4, 4)));

    private final ArrayList<EntityTag> entityTags = new ArrayList<>(
            List.of(new EntityTag(1, "happy", 1), new EntityTag(2, "sad", 2), new EntityTag(3, "positive", 3),
                    new EntityTag(4, "negative", 4)));

    @Override
    public List<GetMappingRequestResponse> getAllEntityByTask(int projectId, int taskId) {
        List<Entity> entity = entityMapping.stream().filter(entityMapping -> entityMapping.getDataTaskId() == taskId).collect(
                Collectors.toList());
        List<GetMappingRequestResponse> responses = new ArrayList<>();
        for (EntityTag entityTag : entityTags){
            for(Entity entity1 : entity){
                if (entity1.getTagId() == entityTag.getId()){
                    responses.add(new GetMappingRequestResponse(entity1.getStart(),entity1.getEnd(),entity1.getTagId(),entityTag.getTagName()));
                }
            }
        }
        return responses;
    }

    @Override
    public int addEntityByTask(int projectId, int taskId, List<Entity> newEntity) {
        int newEntityId = 0;
        Iterator<Entity> entity = newEntity.iterator();
        while (entity.hasNext()){
            newEntityId =  entity.next().getTagId();
        }
        for(Entity e: entityMapping){
            if (e.getDataTaskId() == taskId){
                e.setTagId(newEntityId);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int deleteEntityByTask(int projectId, int taskId) {
        for(Entity e: entityMapping){
            if (e.getDataTaskId() == taskId){
                e.setTagId(0);
                return 1;
            }
        }
        return 0;
    }
}
