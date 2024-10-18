package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BlogComment {

    private int commentId;
    private Timestamp commentedAt;
    private String commentContent;
    private Blog blog;
    private User user;
    private List<BlogComment> replies;
    private BlogComment parent;

    public BlogComment() {
        this.replies = new ArrayList<>();
    }

    public BlogComment(int commentId, Timestamp commentedAt, String commentContent, Blog blog, User user, List<BlogComment> replies, BlogComment parent) {
        this.commentId = commentId;
        this.commentedAt = commentedAt;
        this.commentContent = commentContent;
        this.blog = blog;
        this.user = user;
        this.replies = replies;
        this.parent = parent;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BlogComment> getReplies() {
        return replies;
    }

    public void setReplies(List<BlogComment> replies) {
        this.replies = replies;
    }

    public BlogComment getParent() {
        return parent;
    }

    public void setParent(BlogComment parent) {
        this.parent = parent;
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sdf.format(commentedAt);
    }

    public boolean hasReplies() {
        return replies != null && !replies.isEmpty();
    }

}
