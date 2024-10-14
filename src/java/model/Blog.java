package model;

import java.util.List;

public class Blog {
    private int blogId;
    private String title;
    private String content;
    private String userName;
    private List<String> imageUrls;
    private List<Tag> tags;

    // Constructors
    public Blog(int blogId, String title, String content, String userName, List<String> imageUrls, List<Tag> tags) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.userName = userName;
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
                ", imageUrls=" + imageUrls +
                ", tags=" + tags +
                '}';
    }
}