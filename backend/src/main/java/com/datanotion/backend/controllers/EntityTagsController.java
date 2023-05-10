package com.datanotion.backend.controllers;

import com.datanotion.backend.requests.TagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.datanotion.backend.models.EntityTag;
import com.datanotion.backend.repositories.EntityTagRepository;

import java.util.List;

@RestController
@RequestMapping("/projects/{id}")
public class EntityTagsController {
    @Autowired
    EntityTagRepository entityTagRepository;

    @GetMapping("/entity")
    public List<EntityTag> getEntityTagsByProjectId(@PathVariable("id") int projectId) {
        return EntityTag.getEntityTagsByProjectId(entityTagRepository, projectId);
    }

    @GetMapping("/entity/{entityId}")
    public EntityTag getEntityTagById(@PathVariable("entityId") int id) {
        return EntityTag.getEntityTagById(entityTagRepository, id);
    }

    @PostMapping("/entity")
    public int createEntityTag(@RequestBody TagRequest tagRequest) {
        return EntityTag.createEntityTag(entityTagRepository, tagRequest);
    }

    @DeleteMapping("/entity/{entityId}")
    public int deleteEntityTag(@PathVariable("entityId") int id) {
        return EntityTag.deleteEntityTag(entityTagRepository, id);
    }
}
