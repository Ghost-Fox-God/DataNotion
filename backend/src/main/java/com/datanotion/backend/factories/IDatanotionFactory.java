package com.datanotion.backend.factories;

import java.sql.Date;

import com.datanotion.backend.models.Export;
import com.datanotion.backend.models.Import;
import com.datanotion.backend.models.Project;
import com.datanotion.backend.models.ProjectStatistics;
import com.datanotion.backend.models.Tag;

public interface IDatanotionFactory {
    Project createProject(String name);

    Tag createTag(String tagName);

    ProjectStatistics createProjectStatistics(int totalDataTasks, int annotatedDataTasks);

    Import createImport(Date importDate, String fileName, int recordCount, String status);

    Export createExport(Date exportDate, String status, int recordCount);
}
