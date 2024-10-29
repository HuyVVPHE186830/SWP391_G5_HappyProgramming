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
    private String submissionContent;
    private boolean isLate;
    private boolean status;

    public Submission() {
    }
    
    public Submission(int submissionId, int postId, String submittedBy, Timestamp submittedAt, String submissionContent, boolean isLate, boolean status) {
        this.submissionId = submissionId;
        this.postId = postId;
        this.submittedBy = submittedBy;
        this.submittedAt = submittedAt;
        this.submissionContent = submissionContent;
        this.isLate = isLate;
        this.status = status;
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

    public String getSubmissionContent() {
        return submissionContent;
    }

    public void setSubmissionContent(String submissionContent) {
        this.submissionContent = submissionContent;
    }

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
