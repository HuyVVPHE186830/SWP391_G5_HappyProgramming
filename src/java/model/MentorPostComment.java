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
import java.util.List;
public class MentorPostComment {
    private int commentId;
    private int postId;
    private String commentedBy;
    private Timestamp commentedAt;
    private String commentContent;
    private Integer parentCommentId;
    private List<MentorPostComment> replies;

    public MentorPostComment() {
    }

    public MentorPostComment(int commentId, int postId, String commentedBy, Timestamp commentedAt, String commentContent, Integer parentCommentId) {
        this.commentId = commentId;
        this.postId = postId;
        this.commentedBy = commentedBy;
        this.commentedAt = commentedAt;
        this.commentContent = commentContent;
        this.parentCommentId = parentCommentId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public Timestamp getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(Timestamp commentedAt) {
        this.commentedAt = commentedAt;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
    
    public void setReplies(List<MentorPostComment> replies) {
        this.replies = replies;
    }

    public List<MentorPostComment> getReplies() {
        return replies;
    }

    @Override
    public String toString() {
        return "MentorPostComment{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", commentedBy='" + commentedBy + '\'' +
                ", commentedAt=" + commentedAt +
                ", commentContent='" + commentContent + '\'' +
                ", parentCommentId=" + parentCommentId +
                '}';
    }
}

