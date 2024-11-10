/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.MentorPost;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import model.MentorPostComment;
import model.Submission;
import model.User;

/**
 *
 * @author Huy Võ
 */
public class MentorPostDAO extends DBContext {

    public List<MentorPost> getAllPost(int courseId, String createdBy) {
        List<MentorPost> list = new ArrayList<>();
        String sql = "SELECT * FROM MentorPosts WHERE courseId = ? AND createdBy = ? ORDER BY createdAt DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, courseId); // Gán giá trị courseId vào tham số 1
            st.setString(2, createdBy); // Gán giá trị createdBy vào tham số 2
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("postId");
                String postTitle = rs.getString("postTitle");
                String postContent = rs.getString("postContent");
                int postType = rs.getInt("postTypeId");
                Timestamp deadline = rs.getTimestamp("deadline");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                byte[] fileContent = rs.getBytes("fileContent");
                String fileName = rs.getString("fileName");
                String fileType = rs.getString("fileType");
                

                MentorPost mentorPost = new MentorPost(id, postTitle, postContent, postType, deadline, courseId, createdBy, createdAt, fileContent, fileName, fileType); // Điều chỉnh constructor nếu cần
                list.add(mentorPost); 
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list; 
    }
    
    public MentorPost getPostById(int postId) {
        MentorPost mp = new MentorPost();
        String sql = " Select * from [dbo].[MentorPosts] where [postId] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, postId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                mp.setPostId(postId);
                mp.setPostTitle(rs.getString("postTitle"));
                mp.setPostContent(rs.getString("postContent"));
                mp.setPostTypeId(rs.getInt("postTypeId"));
                mp.setDeadline(rs.getTimestamp("deadline"));
                mp.setCreatedAt( rs.getTimestamp("createdAt"));
                mp.setFileContent(rs.getBytes("fileContent"));
                mp.setFileName(rs.getString("fileName"));
                mp.setFileType(rs.getString("fileType"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return mp;
    }

    public void addMentorPost(MentorPost mentorPost) {
        String sql = "INSERT INTO MentorPosts (postTitle, postContent, postTypeId, deadline, createdAt, courseId, createdBy, fileContent, fileName, fileType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, mentorPost.getPostTitle());
            st.setString(2, mentorPost.getPostContent());
            st.setInt(3, mentorPost.getPostTypeId());
            st.setTimestamp(4, mentorPost.getDeadline());
            st.setTimestamp(5, new Timestamp(System.currentTimeMillis())); 
            st.setInt(6, mentorPost.getCourseId());
            st.setString(7, mentorPost.getCreatedBy());
            st.setBytes(8, mentorPost.getFileContent());
            st.setString(9, mentorPost.getFileName());
            st.setString(10, mentorPost.getFileType());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deletePost(int postId) {
        String sql = "DELETE FROM MentorPosts WHERE postId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, postId); 
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex); 
        }
    }

    public String getPostType(int postTypeId) {
        String postType = null; 
        String sql = "SELECT postType FROM MentorPostTypes WHERE postTypeId = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, postTypeId);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                postType = rs.getString("postType");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return postType;
    }

    public Integer getPostTypeId(String postType) {
        Integer postTypeId = null;
        String sql = "SELECT postTypeId FROM MentorPostTypes WHERE postType = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, postType);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                postTypeId = rs.getInt("postTypeId");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return postTypeId;
    }

    public void updateMentorPost(MentorPost mentorPost, int postId) {
        String sql = "UPDATE MentorPosts SET postTitle = ?, postContent = ?, postTypeId = ?, deadline = ?, fileContent = ?, fileName = ?, fileType =? WHERE postId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, mentorPost.getPostTitle());
            st.setString(2, mentorPost.getPostContent());
            st.setInt(3, mentorPost.getPostTypeId());
            st.setTimestamp(4, mentorPost.getDeadline());
            st.setBytes(5, mentorPost.getFileContent());
            st.setString(6, mentorPost.getFileName());
            st.setString(7, mentorPost.getFileType());
            st.setInt(8, postId);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public List<User> getMenteeList(int courseId, int status, String mentorName) {
        CourseDAO daoC = new CourseDAO();
        UserDAO daoU = new UserDAO();
        List<String> username = daoC.getMenteeByCourse(courseId, status, mentorName);
        List<User> listMentee = new ArrayList<>();
        for (String string : username) {
            User user1 = daoU.getUserByUsernameM(string);
            if (user1 != null) {
                listMentee.add(user1);
            } else {
                System.out.println("User not found for username: " + string);
            }
        }
        return listMentee;
    }

    public List<User> getUserList(int courseId, int status, String mentorName) {
        CourseDAO daoC = new CourseDAO();
        UserDAO daoU = new UserDAO();
        List<String> username = daoC.getUserByCourse(courseId, status, mentorName);
        List<User> listMentee = new ArrayList<>();
        for (String string : username) {
            User user1 = daoU.getUserByUsernameM(string);
            if (user1 != null) {
                listMentee.add(user1);
            } else {
                System.out.println("User not found for username: " + string);
            }
        }
        return listMentee;
    }

    public List<MentorPostComment> getAllCommentsByPostId(int postId) {
        List<MentorPostComment> commentList = new ArrayList<>();
        String sql = "SELECT * FROM MentorPostComments WHERE postId = ? ORDER BY commentedAt DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, postId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int commentId = rs.getInt("commentId");
                String commentedBy = rs.getString("commentedBy");
                Timestamp commentedAt = rs.getTimestamp("commentedAt");
                String commentContent = rs.getString("commentContent");
                MentorPostComment comment = new MentorPostComment(commentId, postId, commentedBy, commentedAt, commentContent);
                commentList.add(comment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commentList;
    }

    public void addComment(MentorPostComment comment) {
        String sql = "INSERT INTO MentorPostComments (postId, commentedBy, commentedAt, commentContent) "
                + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, comment.getPostId());
            st.setString(2, comment.getCommentedBy());
            st.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            st.setString(4, comment.getCommentContent());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error inserting comment: " + ex.getMessage());
        }
    }

    public void addOrUpdateSubmission(Submission submission) {
        String sql = "MERGE INTO Submissions AS target "
                + "USING (SELECT ? AS postId, ? AS submittedBy) AS source "
                + "ON target.postId = source.postId AND target.submittedBy = source.submittedBy "
                + "WHEN MATCHED THEN "
                + "    UPDATE SET target.submittedAt = ?, target.submissionContent = ?, target.isLate = ?, "
                + "    target.fileName = ?, target.fileType = ? "
                + "WHEN NOT MATCHED THEN "
                + "    INSERT (postId, submittedBy, submittedAt, submissionContent, isLate, fileName, fileType) "
                + "    VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, submission.getPostId());
            st.setString(2, submission.getSubmittedBy());
            st.setTimestamp(3, submission.getSubmittedAt());
            st.setBytes(4, submission.getSubmissionContent());
            st.setBoolean(5, submission.isIsLate());
            st.setString(6, submission.getFileName());
            st.setString(7, submission.getFileType());
            st.setInt(8, submission.getPostId());
            st.setString(9, submission.getSubmittedBy());
            st.setTimestamp(10, submission.getSubmittedAt());
            st.setBytes(11, submission.getSubmissionContent());
            st.setBoolean(12, submission.isIsLate());
            st.setString(13, submission.getFileName());
            st.setString(14, submission.getFileType());

            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error adding/updating submission: " + ex.getMessage());
        }
    }

    public List<Submission> getSubmissionsByPostId(int postId) {
        List<Submission> submissions = new ArrayList<>();
        String sql = "SELECT s.submissionId, s.postId, s.submittedBy, s.submittedAt, "
                + "s.submissionContent, s.isLate, s.fileName, s.fileType, u.avatarPath, u.firstName, u.lastName "
                + "FROM Submissions s "
                + "JOIN [dbo].[User] u ON s.submittedBy = u.username "
                + "WHERE s.postId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, postId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Submission submission = new Submission(
                        rs.getInt("submissionId"),
                        rs.getInt("postId"),
                        rs.getString("submittedBy"),
                        rs.getTimestamp("submittedAt"),
                        rs.getBytes("submissionContent"),
                        rs.getBoolean("isLate"),
                        rs.getString("fileName"),
                        rs.getString("fileType")
                );
                submission.setAvatarPath(rs.getString("avatarPath"));
                submission.setFullName(rs.getString("lastName") + " " + rs.getString("firstName"));

                submissions.add(submission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return submissions;
    }

    public Submission getSubmissionBySubmissionId(int submissionId) {
        Submission submission = null; 
        String sql = "SELECT s.submissionId, s.postId, s.submittedBy, s.submittedAt, "
                + "s.submissionContent, s.isLate, s.fileName, s.fileType, u.avatarPath, u.firstName, u.lastName "
                + "FROM Submissions s "
                + "JOIN [dbo].[User] u ON s.submittedBy = u.username "
                + "WHERE s.submissionId = ?"; 

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, submissionId); 
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                submission = new Submission(
                        rs.getInt("submissionId"),
                        rs.getInt("postId"),
                        rs.getString("submittedBy"),
                        rs.getTimestamp("submittedAt"),
                        rs.getBytes("submissionContent"), 
                        rs.getBoolean("isLate"),
                        rs.getString("fileName"),
                        rs.getString("fileType")
                );
                submission.setAvatarPath(rs.getString("avatarPath"));
                submission.setFullName(rs.getString("lastName") + " " + rs.getString("firstName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return submission; // Trả về null nếu không tìm thấy
    }

    public List<Submission> getMenteeSubmissionsInfo(int postId, int courseId, String mentorName) {
        List<Submission> menteeSubmissions = new ArrayList<>();
        List<User> mentees = getMenteeList(courseId, 1, mentorName);
        List<Submission> submissions = getSubmissionsByPostId(postId);
        Map<String, Submission> submissionMap = new HashMap<>();
        for (Submission submission : submissions) {
            submissionMap.put(submission.getSubmittedBy(), submission);
        }
        for (User mentee : mentees) {
            Submission submission = submissionMap.get(mentee.getUsername());
            if (submission != null) {
                menteeSubmissions.add(submission);
            } else {
                Submission emptySubmission = new Submission();
                emptySubmission.setFullName(mentee.getLastName() + " " + mentee.getFirstName());
                emptySubmission.setAvatarPath(mentee.getAvatarPath());
                emptySubmission.setSubmittedAt(null); 
                emptySubmission.setLate(false);
                emptySubmission.setFileName("-");
                emptySubmission.setFileType("-");
                menteeSubmissions.add(emptySubmission);
            }
        }

        return menteeSubmissions;
    }

    public static void main(String[] args) {
        MentorPostDAO dao = new MentorPostDAO();
        List<User> string = dao.getMenteeList(1, 1, "huyenmentor");
        for (User string1 : string) {
            System.out.println(string1.toString());
        }
        List<Submission> list = dao.getMenteeSubmissionsInfo(1, 3, "huyenmentor");
        for (Submission submission : list) {
            System.out.println(submission.toString());
        }
    }

}
