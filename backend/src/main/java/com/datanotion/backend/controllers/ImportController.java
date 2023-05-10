package com.datanotion.backend.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datanotion.backend.exceptions.BadRequestException;
import com.datanotion.backend.exceptions.InternalServerErrorException;
import com.datanotion.backend.factories.DatanotionTextAnnotationFactory;
import com.datanotion.backend.jobs.ImportJob;
import com.datanotion.backend.models.TextAnnotationImport;
import com.datanotion.backend.repositories.FileRepository;
import com.datanotion.backend.repositories.ImportRepository;

@RestController
@RequestMapping("projects/{id}/imports")
public class ImportController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ImportRepository importRepository;

    @Autowired
    private ImportJob importJob;

    @Autowired
    private DatanotionTextAnnotationFactory datanotionTextAnnotationFactory;

    @GetMapping("")
    public ResponseEntity<Object> getImportsByProjectId(@PathVariable("id") int projectId) {
        return ResponseEntity.ok(importRepository.getImportsByProjectId(projectId));
    }

    @PostMapping("")
    public ResponseEntity<Object> createImport(@RequestParam("file") MultipartFile file,
            @PathVariable("id") int projectId, @RequestParam("assignTo") String assignTo)
            throws BadRequestException, InternalServerErrorException {

        int[] assigneesId;
        try {
            assigneesId = Arrays.stream(assignTo.split(",")).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid assignees");
        }

        if (assigneesId.length == 0) {
            throw new BadRequestException("You must assign atleast one annotator to this import");
        }
        TextAnnotationImport newImport = (TextAnnotationImport) datanotionTextAnnotationFactory.createImport(
                new Date(System.currentTimeMillis()),
                file.getOriginalFilename(), 0, "Pending");
        newImport.setProjectId(projectId);
        newImport = importRepository.createImport(newImport);

        for (int assigneeId : assigneesId) {
            importRepository.assignAnnotatorToImport(newImport, assigneeId);
        }

        String filePath;
        try {
            filePath = fileRepository.save(file.getBytes(), projectId + "-" + newImport.getId() + ".txt");
        } catch (IOException e) {
            newImport.setStatus("Failed");
            importRepository.updateImport(newImport);
            throw new InternalServerErrorException("Error while saving file");
        }

        importJob.loadAndAssignToAnnotators(filePath,
                assigneesId, newImport);
        return ResponseEntity.accepted().body(newImport);
    }
}
