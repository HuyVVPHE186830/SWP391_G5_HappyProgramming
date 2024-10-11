/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Huy Võ
 */
import java.sql.Timestamp;

public class MentorPost {
    private String postTitle;
    private String postContent;
    private String postType;
    private Timestamp deadline; // Đổi từ Date sang Timestamp
    private int courseId;
    private String createdBy;
    private Timestamp createdAt; // Thêm thuộc tính createdAt

    // Constructor

    public MentorPost() {
    }
    
    
    public MentorPost(String postTitle, String postContent, String postType, Timestamp deadline, int courseId, String createdBy) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postType = postType;
        this.deadline = deadline;
        this.courseId = courseId;
        this.createdBy = createdBy;
        this.createdAt = new Timestamp(System.currentTimeMillis()); // Gán thời gian hiện tại cho createdAt
    }

    public MentorPost(String postTitle, String postContent, String postType, Timestamp deadline, int courseId, String createdBy, Timestamp createdAt) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postType = postType;
        this.deadline = deadline;
        this.courseId = courseId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
    
    
    // Getter và Setter
    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt; // Thêm getter cho createdAt
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MentorPost{" + "postTitle=" + postTitle + ", postContent=" + postContent + ", postType=" + postType + ", deadline=" + deadline + ", courseId=" + courseId + ", createdBy=" + createdBy + ", createdAt=" + createdAt + '}';
    }
    
}
