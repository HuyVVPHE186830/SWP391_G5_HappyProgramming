package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import model.BlogComment;
import model.User;

public class BlogCommentDAO extends DBContext {

    private UserDAO userDAO;
    private BlogDAO blogDAO;

    public BlogCommentDAO() {
        userDAO = new UserDAO();
        blogDAO = new BlogDAO();
    }

    public static void main(String[] args) {

    }

    public List<BlogComment> getCommentsForBlog(int blogId) {
        List<BlogComment> list = new ArrayList<>();
        String sql = "SELECT * FROM Comment WHERE blogId = ? and parentId is null ORDER BY commentedAt DESC";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BlogComment comment = new BlogComment();
                comment.setCommentId(rs.getInt("commentId"));
                comment.setCommentedAt(rs.getTimestamp("commentedAt"));
                comment.setCommentContent(rs.getString("commentContent"));

                // Set the blog for the comment
                Blog blog = blogDAO.getBlogById(blogId);
                comment.setBlog(blog);

                // Retrieve the user who commented
                User user = userDAO.getUserByUsernameM(rs.getString("commentedBy"));
                comment.setUser(user);

                // Fetch and set replies for this comment
                List<BlogComment> replies = getRepliesForComment(comment.getCommentId());
                comment.setReplies(replies);

                list.add(comment);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<BlogComment> getRepliesForComment(int parentId) {
        List<BlogComment> replies = new ArrayList<>();
        String sql = "SELECT * FROM Comment WHERE parentId = ? ORDER BY commentedAt DESC";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, parentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BlogComment reply = new BlogComment();
                reply.setCommentId(rs.getInt("commentId"));
                reply.setCommentedAt(rs.getTimestamp("commentedAt"));
                reply.setCommentContent(rs.getString("commentContent"));

                // Retrieve the user who commented
                User user = userDAO.getUserByUsernameM(rs.getString("commentedBy"));
                reply.setUser(user);

                // Set parent comment
                BlogComment parentComment = getCommentById(parentId);
                reply.setParent(parentComment);

                // Fetch any replies to this reply (nested comments)
                List<BlogComment> nestedReplies = getRepliesForComment(reply.getCommentId());
                reply.setReplies(nestedReplies);

                replies.add(reply);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return replies;
    }

    public BlogComment getCommentById(int commentId) {
        BlogComment comment = null;
        String sql = "SELECT * FROM Comment WHERE commentId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, commentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                comment = new BlogComment();
                comment.setCommentId(commentId);
                comment.setCommentedAt(rs.getTimestamp("commentedAt"));
                comment.setCommentContent(rs.getString("commentContent"));
                comment.setBlog(blogDAO.getBlogById(rs.getInt("blogId")));
                // Retrieve the user who commented
                User user = userDAO.getUserByUsernameM(rs.getString("commentedBy"));
                comment.setUser(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comment;
    }

    public void addComment(BlogComment comment) {
        String sql = "INSERT INTO Comment (commentedAt, commentContent, blogId, commentedBy, parentId) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, comment.getCommentedAt());
            ps.setString(2, comment.getCommentContent());
            ps.setInt(3, comment.getBlog().getBlogId());
            ps.setString(4, comment.getUser().getUsername());
            if (comment.getParent() != null) {
                ps.setInt(5, comment.getParent().getCommentId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(int commentId) {
        String sql = "DELETE FROM Comment WHERE commentId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, commentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteReplies(int parentId) {
        String sql = "DELETE FROM Comment WHERE parentId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, parentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
