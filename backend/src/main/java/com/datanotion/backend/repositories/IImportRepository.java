package com.datanotion.backend.repositories;

import java.util.List;

import com.datanotion.backend.models.Import;

public interface IImportRepository {
    List<? extends Import> getImportsByProjectId(int projectId);

    Import createImport(Import newImports);

    Import updateImport(Import importToBeUpdated);

    void assignAnnotatorToImport(Import importToBeUpdated, int annotatorId);
}
