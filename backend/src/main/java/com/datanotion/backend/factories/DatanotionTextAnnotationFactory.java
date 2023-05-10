package com.datanotion.backend.factories;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.datanotion.backend.models.TextAnnotationExport;
import com.datanotion.backend.models.TextAnnotationImport;
import com.datanotion.backend.models.TextAnnotationProject;
import com.datanotion.backend.models.TextAnotationProjectStatistics;
import com.datanotion.backend.models.ClassificationTag;
import com.datanotion.backend.models.EntityTag;
import com.datanotion.backend.models.Export;
import com.datanotion.backend.models.Import;
import com.datanotion.backend.models.Project;
import com.datanotion.backend.models.ProjectStatistics;
import com.datanotion.backend.models.Tag;

@Component
public class DatanotionTextAnnotationFactory implements IDatanotionFactory {

    @Override
    public Project createProject(String name) {
        return new TextAnnotationProject(0, name);
    }

    @Override
    public Tag createTag(String tagName) {
        return new EntityTag(0, tagName, 0);
    }

    public Tag createClassificationTag(String tagName) {
        return new ClassificationTag(0, tagName, 0);
    }

    @Override
    public ProjectStatistics createProjectStatistics(int totalDataTasks, int annotatedDataTasks) {
        return new TextAnotationProjectStatistics(null, null, totalDataTasks, annotatedDataTasks);
    }

    @Override
    public Import createImport(Date importDate, String fileName, int recordCount, String status) {
        return new TextAnnotationImport(0, 0, importDate, fileName, recordCount, status);
    }

    @Override
    public Export createExport(Date exportDate, String status, int recordCount) {
        return new TextAnnotationExport(0, 0, exportDate, status, recordCount);
    }

}
