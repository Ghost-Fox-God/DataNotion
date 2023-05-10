package com.datanotion.backend.models;

public abstract class Tag {

    protected String tagName;

    public Tag(String name) {
        this.tagName = name;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String name) {
        this.tagName = name;
    }

}
