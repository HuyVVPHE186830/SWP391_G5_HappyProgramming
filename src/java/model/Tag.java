package model;

public class Tag {
    private int tagId;
    private String tagName;

    // Constructor
    public Tag(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    // Getters and Setters
    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tag{" + "tagId=" + tagId + ", tagName=" + tagName + '}';
    }
    
}
