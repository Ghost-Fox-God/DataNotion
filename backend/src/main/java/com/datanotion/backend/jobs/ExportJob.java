package com.datanotion.backend.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.datanotion.backend.models.TextAnnotationExport;
import com.datanotion.backend.models.ExportRow;
import com.datanotion.backend.models.ExportRowDataModel;
import com.datanotion.backend.repositories.ExportRepository;
import com.datanotion.backend.repositories.FileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExportJob {

    @Autowired
    ExportRepository exportRepository;

    @Autowired
    FileRepository fileRepository;

    @Async("executor")
    public void exportAnnotatedTasks(TextAnnotationExport currentExport) {

        ObjectMapper mapper = new ObjectMapper();
        List<ExportRow> rows = exportRepository.exportDataAnnotationsByProjectId(currentExport.getProjectId());
        List<ExportRowDataModel> dataRows = new ArrayList<ExportRowDataModel>();
        rows.forEach(row -> dataRows.add(row.toExportRowDataModel()));
        try {
            fileRepository.save(mapper.writeValueAsString(dataRows).getBytes(), currentExport.getId() + ".json");
            currentExport.setStatus("Done");
            currentExport.setRecordCount(rows.size());
        } catch (Exception e) {
            currentExport.setStatus("Failed");
        }
        exportRepository.updateExport(currentExport);

    }

}
