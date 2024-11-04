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

public class Submission {

    private int submissionId;
    private int postId;
    private String submittedBy;
    private Timestamp submittedAt;
    private byte[] submissionContent;
    private boolean isLate;
    private String avatarPath;
    private String fullName;
    private String fileName;
    private String fileType;

    public Submission() {
    }
    
    public Submission(int submissionId, int postId, String submittedBy, Timestamp submittedAt, byte[] submissionContent, boolean isLate, String fileName, String fileType) {
        this.submissionId = submissionId;
        this.postId = postId;
        this.submittedBy = submittedBy;
        this.submittedAt = submittedAt;
        this.submissionContent = submissionContent;
        this.isLate = isLate;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }

    public byte[] getSubmissionContent() {
        return submissionContent;
    }

    public void setSubmissionContent(byte[] submissionContent) {
        this.submissionContent = submissionContent;
    }

    public boolean isIsLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }
    
    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
}
