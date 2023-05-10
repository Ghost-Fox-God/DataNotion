package com.datanotion.backend.models;

public class EntityAnnotation {
    private int start;
    private int end;
    private String entity;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public EntityAnnotation(int start, int end, String entity) {
        this.start = start;
        this.end = end;
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
