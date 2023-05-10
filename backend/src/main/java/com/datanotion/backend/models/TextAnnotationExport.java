package com.datanotion.backend.models;

import java.sql.Date;

public class TextAnnotationExport extends Export {
    private int id;
    private int projectId;

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

    public TextAnnotationExport(int id, int projectId, Date exportDate, String status, int recordCount) {
        super(exportDate, status, recordCount);
        this.id = id;
        this.projectId = projectId;
    }
}
