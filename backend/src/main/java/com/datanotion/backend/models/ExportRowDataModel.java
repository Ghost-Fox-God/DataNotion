package com.datanotion.backend.models;

import java.util.List;

public class ExportRowDataModel {
    private String content;
    private String classification;
    private List<EntityAnnotation> entities;

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

    public List<EntityAnnotation> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityAnnotation> entities) {
        this.entities = entities;
    }

    public ExportRowDataModel(String content, String classification, List<EntityAnnotation> entities) {
        this.content = content;
        this.classification = classification;
        this.entities = entities;
    }
}
