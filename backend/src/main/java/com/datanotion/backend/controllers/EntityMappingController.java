package com.datanotion.backend.controllers;

import com.datanotion.backend.models.Entity;
import com.datanotion.backend.repositories.EntityRepository;
import com.datanotion.backend.responses.GetMappingRequestResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}")
public class EntityMappingController {

    @Autowired
    EntityRepository entityRepository;

    @GetMapping("/tasks/{taskId}/entity")
    public List<GetMappingRequestResponse> getAllEntityByTask(@PathVariable("projectId") int projectId,
            @PathVariable("taskId") int taskId) {
        return Entity.fetchAllEntityByTask(entityRepository, projectId, taskId);
    }

    @PostMapping("/tasks/{taskId}/entity")
    public int addEntityByTask(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId,
            @RequestBody List<Entity> newEntity) {
        return Entity.createEntityByTask(entityRepository, projectId, taskId, newEntity);
    }
}
