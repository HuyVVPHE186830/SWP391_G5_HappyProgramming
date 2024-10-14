package model;

import java.util.List;

public class Blog {
    private int blogId;
    private String title;
    private String content;
    private String userName;  // Optional, if you still want to keep this
    private String createdBy;  // New field for tracking the creator
    private List<String> imageUrls;
    private List<Tag> tags;

    // Constructors
    public Blog(int blogId, String title, String content, String createdBy, List<String> imageUrls, List<Tag> tags) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;  // Set the createdBy field
        this.imageUrls = imageUrls;
        this.tags = tags;
    }

    // Getters and Setters
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatedBy() {
        return createdBy;  // Getter for createdBy
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;  // Setter for createdBy
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userName='" + userName + '\'' +
                ", createdBy='" + createdBy + '\'' +  // Include createdBy in the string representation
                ", imageUrls=" + imageUrls +
                ", tags=" + tags +
                '}';
    }
}
