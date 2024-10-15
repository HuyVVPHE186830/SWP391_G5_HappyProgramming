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
                // Lấy các trường bạn cần từ kết quả truy vấn
                int id = rs.getInt("postId");
                String postTitle = rs.getString("postTitle");
                String postContent = rs.getString("postContent");
                int postType = rs.getInt("postTypeId");
                Timestamp deadline = rs.getTimestamp("deadline");
                Timestamp createdAt = rs.getTimestamp("createdAt");

                // Tạo đối tượng MentorPost từ dữ liệu trong cơ sở dữ liệu
                MentorPost mentorPost = new MentorPost(id, postTitle, postContent, postType, deadline, courseId, createdBy, createdAt); // Điều chỉnh constructor nếu cần
                list.add(mentorPost); // Thêm bài viết vào danh sách
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list; // Trả về danh sách các bài viết
    }

    public void addMentorPost(MentorPost mentorPost) {
        String sql = "INSERT INTO MentorPosts (postTitle, postContent, postTypeId, deadline, createdAt, courseId, createdBy) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, mentorPost.getPostTitle());
            st.setString(2, mentorPost.getPostContent());
            st.setInt(3, mentorPost.getPostTypeId());
            st.setTimestamp(4, mentorPost.getDeadline()); // Lưu deadline với thông tin giờ
            st.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // Lưu createdAt với thời gian hiện tại
            st.setInt(6, mentorPost.getCourseId());
            st.setString(7, mentorPost.getCreatedBy());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deletePost(int postId) {
        String sql = "DELETE FROM MentorPosts WHERE postId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, postId); // Đặt ID của bài viết vào câu lệnh SQL
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex); // In ra lỗi nếu có
        }
    }

    public String getPostType(int postTypeId) {
        String postType = null; // Biến để lưu trữ postType
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
        String sql = "UPDATE MentorPosts SET postTitle = ?, postContent = ?, postTypeId = ?, deadline = ? WHERE postId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, mentorPost.getPostTitle());
            st.setString(2, mentorPost.getPostContent());
            st.setInt(3, mentorPost.getPostTypeId());
            st.setTimestamp(4, mentorPost.getDeadline());
            st.setInt(5, postId);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public List<User> getMenteeList(int courseId, int status) {
        CourseDAO daoC = new CourseDAO();
        UserDAO daoU = new UserDAO();
        List<String> username = daoC.getMenteeByCourse(courseId, status);
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

    public static void main(String[] args) {
        MentorPostDAO dao = new MentorPostDAO();
        List<MentorPost> posts = dao.getAllPost(1, "huyenmentor");
        for (MentorPost post : posts) {
            System.out.println(post.toString());
        }
    }

}
