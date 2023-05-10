package com.datanotion.backend.models;

import java.util.ArrayList;
import java.util.List;

public class ExportRow {
    private String content;
    private String classification;
    private String entity;
    private String start;
    private String end;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public ExportRow(String content, String classification, String entity, String start, String end) {
        this.content = content;
        this.classification = classification;
        this.entity = entity;
        this.start = start;
        this.end = end;
    }

    public String toCSV() {
        return content + "," + classification + "," + entity + "," + start + "," + end;
    }

    public ExportRowDataModel toExportRowDataModel() {
        List<EntityAnnotation> entityList = new ArrayList<EntityAnnotation>();
        if (start == null || end == null || entity == null) {
            return new ExportRowDataModel(content, classification, entityList);
        }
        String[] startArray = start.split(",");
        String[] endArray = end.split(",");
        String[] entityArray = entity.split(",");
        for (int i = 0; i < startArray.length; i++) {
            entityList.add(new EntityAnnotation(Integer.parseInt(startArray[i]), Integer.parseInt(endArray[i]),
                    entityArray[i]));
        }
        return new ExportRowDataModel(content, classification, entityList);
    }
}
