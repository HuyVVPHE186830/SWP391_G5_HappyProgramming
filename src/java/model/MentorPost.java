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
    private int postId;
    private String postTitle;
    private String postContent;
    private int postTypeId;
    private Timestamp deadline;
    private int courseId;
    private String createdBy;
    private Timestamp createdAt;
    private byte[] fileContent;
    private String fileName;
    private String fileType;

    public MentorPost() {
    }
    
    
    public MentorPost(String postTitle, String postContent, int postTypeId, Timestamp deadline, int courseId, String createdBy, byte[] fileContent, String fileName, String fileType) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTypeId = postTypeId;
        this.deadline = deadline;
        this.courseId = courseId;
        this.createdBy = createdBy;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.fileType = fileType;
    }
    
    public MentorPost(String postTitle, String postContent, int postTypeId, Timestamp deadline, Timestamp createdAt, byte[] fileContent, String fileName, String fileType) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTypeId = postTypeId;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public MentorPost(int postId, String postTitle, String postContent, int postTypeId, Timestamp deadline, int courseId, String createdBy, Timestamp createdAt, byte[] fileContent, String fileName, String fileType) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTypeId = postTypeId;
        this.deadline = deadline;
        this.courseId = courseId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
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

    public int getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(int postTypeId) {
        this.postTypeId = postTypeId;
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

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    

    @Override
    public String toString() {
        return "MentorPost{" + "postId=" + postId + ", postTitle=" + postTitle + ", postContent=" + postContent + ", postTypeId=" + postTypeId + ", deadline=" + deadline + ", courseId=" + courseId + ", createdBy=" + createdBy + ", createdAt=" + createdAt + '}';
    }

    
    
}
