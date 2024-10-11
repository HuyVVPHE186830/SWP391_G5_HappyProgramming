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
                String postTitle = rs.getString("postTitle");
                String postContent = rs.getString("postContent");
                String postType = rs.getString("postType");
                Timestamp deadline = rs.getTimestamp("deadline");
                Timestamp createdAt = rs.getTimestamp("createdAt");

                // Tạo đối tượng MentorPost từ dữ liệu trong cơ sở dữ liệu
                MentorPost mentorPost = new MentorPost(postTitle, postContent, postType, deadline, courseId, createdBy, createdAt); // Điều chỉnh constructor nếu cần
                list.add(mentorPost); // Thêm bài viết vào danh sách
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list; // Trả về danh sách các bài viết
    }

    public void addMentorPost(MentorPost mentorPost) {
        String sql = "INSERT INTO MentorPosts (postTitle, postContent, postType, deadline, createdAt, courseId, createdBy) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, mentorPost.getPostTitle());
            st.setString(2, mentorPost.getPostContent());
            st.setString(3, mentorPost.getPostType());
            st.setTimestamp(4, mentorPost.getDeadline()); // Lưu deadline với thông tin giờ
            st.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // Lưu createdAt với thời gian hiện tại
            st.setInt(6, mentorPost.getCourseId());
            st.setString(7, mentorPost.getCreatedBy());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public static void main(String[] args) {
        MentorPostDAO dao = new MentorPostDAO();
        List<MentorPost> posts = dao.getAllPost(1, "huyenmentor");
        for (MentorPost post : posts) {
            System.out.println(post.toString());
        }
    }

}
