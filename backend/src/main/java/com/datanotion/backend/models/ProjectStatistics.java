package com.datanotion.backend.models;

public abstract class ProjectStatistics {
    protected int totalDataTasks;
    protected int annotatedDataTasks;

    public int getTotalDataTasks() {
        return totalDataTasks;
    }

    public void setTotalDataTasks(int totalDataTasks) {
        this.totalDataTasks = totalDataTasks;
    }

    public int getAnnotatedDataTasks() {
        return annotatedDataTasks;
    }

    public void setAnnotatedDataTasks(int annotatedDataTasks) {
        this.annotatedDataTasks = annotatedDataTasks;
    }

    public ProjectStatistics(int totalDataTasks, int annotatedDataTasks) {
        this.totalDataTasks = totalDataTasks;
        this.annotatedDataTasks = annotatedDataTasks;
    }
}
