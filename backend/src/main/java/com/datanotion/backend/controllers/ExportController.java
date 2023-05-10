package com.datanotion.backend.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datanotion.backend.exceptions.BadRequestException;
import com.datanotion.backend.factories.DatanotionTextAnnotationFactory;
import com.datanotion.backend.jobs.ExportJob;
import com.datanotion.backend.models.TextAnnotationExport;
import com.datanotion.backend.repositories.ExportRepository;
import com.datanotion.backend.repositories.FileRepository;

@RestController
@RequestMapping("projects/{id}/exports")
public class ExportController {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ExportRepository exportRepository;

    @Autowired
    private ExportJob exportJob;

    @Autowired
    public DatanotionTextAnnotationFactory datanotionTextAnnotationFactory;

    @GetMapping("")
    public ResponseEntity<Object> listExports(
            @PathVariable("id") int projectId) {
        return ResponseEntity.ok(exportRepository.getExportsByProjectId(projectId));
    }

    @PostMapping("")
    public ResponseEntity<Object> createExport(
            @PathVariable("id") int projectId) {

        TextAnnotationExport newExport = (TextAnnotationExport) datanotionTextAnnotationFactory.createExport(
                new Date(System.currentTimeMillis()),
                "Pending", 0);

        newExport.setProjectId(projectId);
        newExport = exportRepository.createExport(newExport);
        exportJob.exportAnnotatedTasks(newExport);

        return ResponseEntity.ok(newExport);
    }

    @GetMapping("/{export_id}")
    public ResponseEntity<Resource> getExportsByProjectId(
            @PathVariable("export_id") int exportId) throws BadRequestException {
        Resource file = null;
        try {

            file = fileRepository.load(exportId + ".json");
        } catch (Exception e) {
            throw new BadRequestException("");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
