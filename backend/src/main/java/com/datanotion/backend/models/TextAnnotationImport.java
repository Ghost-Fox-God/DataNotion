package com.datanotion.backend.models;

import java.sql.Date;

public class TextAnnotationImport extends Import {

    private int id;
    private int projectId;

    public TextAnnotationImport(int id, int projectId, Date importDate, String fileName, int recordCount,
            String status) {
        super(importDate, fileName, recordCount, status);
        this.id = id;
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

}
