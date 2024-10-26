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
                Participate p = new Participate(courseId, username, participateRole, statusId);

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
                Participate p = new Participate(courseId, username, participateRole, statusId);

                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public void addParticipate(Participate participate) {
        String sql = "INSERT INTO [Participate] (courseId, username, ParticipateRole, statusId) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, participate.getCourseId());
            st.setString(2, participate.getUsername());
            st.setInt(3, participate.getParticipateRole());
            st.setInt(4, participate.getStatusId());
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

    public boolean deleteParticipate(int courseId, String username) {
        boolean f = false;
        String sql = "DELETE FROM Participate WHERE courseId = ? and username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setString(2, username);
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
//        List<Participate> list = dao.getAll();
//        for (Participate l : list) {
//            System.out.println(l);
//        }
//        Request req1 = daoR.getRequestByUsername("anmentor", 1);
////        dao.addParticipate(new Participate(4, req1.getUsername(), 2, req1.getRequestStatus()));
////        daoR.updateRequest(4, 1, "anmentor", "hel");
        dao.deleteParticipate(20, "ducmentor");
    }

    public void changeParticipate(String menId, int couId, int i) {
        String sql = "UPDATE [dbo].[Participate]\n"
                + "   SET [statusId] = ?\n"
                + " WHERE username =? and courseId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,i);
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

  
}
