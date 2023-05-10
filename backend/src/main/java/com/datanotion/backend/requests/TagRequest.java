package com.datanotion.backend.requests;

public class TagRequest {
    private String tagName;
    private int projectId;

    public TagRequest(String tagName, int projectId) {
        this.tagName = tagName;
        this.projectId = projectId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
