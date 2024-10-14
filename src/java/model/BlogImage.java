package model;

public class BlogImage {
    private int imageId;
    private int blogId;
    private String imageUrl;

    // Constructors
    public BlogImage(int imageId, int blogId, String imageUrl) {
        this.imageId = imageId;
        this.blogId = blogId;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "BlogImage{" +
                "imageId=" + imageId +
                ", blogId=" + blogId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}