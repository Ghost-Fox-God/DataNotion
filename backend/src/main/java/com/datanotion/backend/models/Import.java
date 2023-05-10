package com.datanotion.backend.models;

import java.sql.Date;

public abstract class Import {
    protected Date importDate;
    protected String fileName;
    protected int recordCount;
    protected String status;

    public Import(Date importDate, String fileName, int recordCount, String status) {
        this.importDate = importDate;
        this.fileName = fileName;
        this.recordCount = recordCount;
        this.status = status;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
