package com.datanotion.backend.models;

import java.sql.Date;

public abstract class Export {
    protected Date exportDate;
    protected String status;
    protected int recordCount;

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public Export(Date exportDate, String status, int recordCount) {
        this.exportDate = exportDate;
        this.status = status;
        this.recordCount = recordCount;
    }

}
