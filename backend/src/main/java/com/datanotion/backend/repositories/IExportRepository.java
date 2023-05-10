package com.datanotion.backend.repositories;

import java.util.List;

import com.datanotion.backend.models.Export;
import com.datanotion.backend.models.ExportRow;

public interface IExportRepository {
    List<? extends Export> getExportsByProjectId(int projectId);

    Export createExport(Export newExport);

    Export updateExport(Export exportToBeUpdated);

    List<ExportRow> exportDataAnnotationsByProjectId(int projectId);
}
