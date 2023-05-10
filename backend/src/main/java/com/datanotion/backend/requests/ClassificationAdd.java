package com.datanotion.backend.requests;

public class ClassificationAdd {
    private int classificationId;

    public ClassificationAdd() {
    }

    public int getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    public ClassificationAdd(int classificationId) {
        this.classificationId = classificationId;
    }
}
