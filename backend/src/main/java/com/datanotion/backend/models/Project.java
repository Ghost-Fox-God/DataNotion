package com.datanotion.backend.models;

public abstract class Project {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project(String name) {
        this.name = name;
    }
}
