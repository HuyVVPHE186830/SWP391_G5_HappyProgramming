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
import model.Course;
import model.Participate;
import model.Request;

/**
 *
 * @author Admin
 */
public class ParticipateDAO extends DBContext {

    public List<Participate> getAll() {
        List<Participate> list = new ArrayList<>();
        String sql = "SELECT * FROM [Participate]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                String username = rs.getString("username");
                int participateRole = rs.getInt("participateRole");
                int statusId = rs.getInt("statusId");
                String mentorUsername = rs.getString("mentorUsername");
                Participate p = new Participate(courseId, username, participateRole, statusId, mentorUsername);

                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Participate> getAllByUsername(String username) {
        List<Participate> list = new ArrayList<>();
        String sql = "SELECT * FROM [Participate] where username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                int participateRole = rs.getInt("participateRole");
                int statusId = rs.getInt("statusId");
                String mentorUsername = rs.getString("mentorUsername");
                Participate p = new Participate(courseId, username, participateRole, statusId, mentorUsername);

                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Participate> getAllByUsernameOfMentee(String username) {
        List<Participate> list = new ArrayList<>();
        String sql = "SELECT * FROM [Participate] where username = ? and participateRole = 3";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                int participateRole = rs.getInt("participateRole");
                int statusId = rs.getInt("statusId");
                String mentorUsername = rs.getString("mentorUsername");
                Participate p = new Participate(courseId, username, participateRole, statusId, mentorUsername);

                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public void addParticipate(Participate participate) {
        String sql = "INSERT INTO [Participate] (courseId, username, ParticipateRole, statusId, mentorUsername) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, participate.getCourseId());
            st.setString(2, participate.getUsername());
            st.setInt(3, participate.getParticipateRole());
            st.setInt(4, participate.getStatusId());
            st.setString(5, participate.getMentorUsername());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public boolean updateParticipate(int oldCourseId, int newCourseId, String username) {
        boolean f = false;
        try {
            String sql = "Update Participate Set courseId = ? Where username = ? and courseId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, newCourseId);
            ps.setString(2, username);
            ps.setInt(3, oldCourseId);
            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public boolean updateParticipateStatus(int courseId, String username, int statusId) {
        boolean f = false;
        try {
            String sql = "Update Participate Set statusId = ? Where username = ? and courseId = ? and statusId = 0";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, statusId);
            ps.setString(2, username);
            ps.setInt(3, courseId);
            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public boolean updateParticipateStatusForMentor(Participate participate) {
        boolean f = false;
        try {
            String sql = "Update Participate Set statusId = ? Where username = ? and courseId = ? and mentorUsername = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, participate.getStatusId());
            ps.setString(2, participate.getUsername());
            ps.setInt(3, participate.getCourseId());
            ps.setString(4, participate.getMentorUsername());
            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public boolean deleteParticipate(int courseId, String username) {
        boolean f = false;
        String sql = "DELETE FROM Participate WHERE courseId = ? and username = ? and mentorUsername = ? and statusId != 1";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setString(2, username);
            ps.setString(3, username);
            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }
    
    public boolean deleteParticipateForMentee(int courseId, String username, String mentorUsername) {
        boolean f = false;
        String sql = "DELETE FROM Participate WHERE courseId = ? and username = ? and mentorUsername = ? and statusId != 1";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setString(2, username);
            ps.setString(3, mentorUsername);
            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static void main(String[] args) {
        ParticipateDAO dao = new ParticipateDAO();
        RequestDAO daoR = new RequestDAO();
//        List<Participate> list = dao.getParticipateByUsernameAndCourseIdAndMentorUsername(7, "anmentor", "anmentor");
        Participate p = dao.getParticipateByUsernameAndCourseIdAndMentorUsername(7, "anmentor", "anmentor");
//        for (Participate l : list) {
//            System.out.println(l);
//        }
        System.out.println(p);
//        Request req1 = daoR.getRequestByUsername("anmentor", 1);
////        dao.addParticipate(new Participate(4, req1.getUsername(), 2, req1.getRequestStatus()));
////        daoR.updateRequest(4, 1, "anmentor", "hel");
//        dao.deleteParticipate(2, "chauntm");
    }

    public void changeParticipate(String menId, int couId, int i) {
        String sql = "UPDATE [dbo].[Participate]\n"
                + "   SET [statusId] = ?\n"
                + " WHERE username =? and courseId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, i);
            st.setString(2, menId);
            st.setInt(3, couId);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteParticipate2(int courseId, String username) {
        String sql = "DELETE FROM Participate WHERE courseId = ? and username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Participate> getParticipateByStatus(int i) {
        List<Participate> list = new ArrayList<>();
        String sql = "SELECT * FROM [Participate] where statusId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, i);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                int courseId = rs.getInt("courseId");
                int participateRole = rs.getInt("participateRole");
                int statusId = rs.getInt("statusId");
                String mentorUsername = rs.getString("mentorUsername");
                Participate p = new Participate(courseId, username, participateRole, statusId, mentorUsername);
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Participate> getAllParticipateOfMenteeByKeyword(String keyword, String username) {
        List<Participate> list = new ArrayList<>();
        String sql = "SELECT * FROM [Participate] JOIN Course ON Participate.CourseId = Course.CourseId WHERE username = ? and ParticipateRole = 3 AND (Course.CourseName LIKE ? OR Participate.mentorUsername LIKE ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, "%" + keyword + "%");
            st.setString(3, "%" + keyword + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("CourseId");
                int statusId = rs.getInt("statusId");
                int participateRole = rs.getInt("ParticipateRole");
                String mentorUsername = rs.getString("mentorUsername");
                Participate r = new Participate(courseId, username, participateRole, statusId, mentorUsername);
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public Participate getParticipateByUsernameAndCourseId(int courseId, String username) {
        Participate p = new Participate();
        String sql = "SELECT * FROM [Participate] where courseId = ? and username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, courseId);
            st.setString(2, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int participateRole = rs.getInt("participateRole");
                int statusId = rs.getInt("statusId");
                String mentorUsername = rs.getString("mentorUsername");
                p = new Participate(courseId, username, participateRole, statusId, mentorUsername);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return p;
    }

    public Participate getParticipateByUsernameAndCourseIdAndMentorUsername(int courseId, String username, String mentorUsername) {
        Participate p = new Participate();
        String sql = "SELECT * FROM [Participate] where courseId = ? and username = ? and mentorUsername = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, courseId);
            st.setString(2, username);
            st.setString(3, mentorUsername);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int participateRole = rs.getInt("participateRole");
                int statusId = rs.getInt("statusId");
                p = new Participate(courseId, username, participateRole, statusId, mentorUsername);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return p;
    }
}
