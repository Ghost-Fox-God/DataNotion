package com.datanotion.backend.responses;

public class GetMappingRequestResponse {
    public int start;
    public int end;
    public int tagId;
    public String tag;

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getTagId() {
        return tagId;
    }

    public String getTag() {
        return tag;
    }

    public GetMappingRequestResponse(int start, int end, int tagId, String tag) {
        this.start = start;
        this.end = end;
        this.tagId = tagId;
        this.tag = tag;
    }
}
