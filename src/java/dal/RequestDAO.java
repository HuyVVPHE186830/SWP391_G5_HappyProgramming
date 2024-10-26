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
import model.Request;

/**
 *
 * @author Admin
 */
public class RequestDAO extends DBContext {

    public List<Request> getAll() {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM [Request]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                String username = rs.getString("username");
                Date requestTime = rs.getDate("requestTime");
                int requestStatus = rs.getInt("requestStatus");
                String requestReason = rs.getString("requestReason");
                Request r = new Request(courseId, username, requestTime, requestStatus, requestReason);
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Request> getAllRequestByUsername(String username) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM [Request] where username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                Date requestTime = rs.getDate("requestTime");
                int requestStatus = rs.getInt("requestStatus");
                String requestReason = rs.getString("requestReason");
                Request r = new Request(courseId, username, requestTime, requestStatus, requestReason);
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public Request getRequestByUsername(String username, int courseId) {
        Request request = new Request();
        String sql = "SELECT * FROM [Request] where username = ? and courseId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setInt(2, courseId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Date requestTime = rs.getDate("requestTime");
                int requestStatus = rs.getInt("requestStatus");
                String requestReason = rs.getString("requestReason");
                request = new Request(courseId, username, requestTime, requestStatus, requestReason);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return request;
    }

    public void addRequest(Request request) {
        String sql = "INSERT INTO [Request] (courseId, username, requestTime, requestStatus, requestReason) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, request.getCourseId());
            st.setString(2, request.getUsername());
            st.setDate(3, new java.sql.Date(request.getRequestTime().getTime()));
            st.setInt(4, request.getRequestStatus());
            st.setString(5, request.getRequestReason());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public boolean updateRequest(int oldCourseId, int newCourseId, String username, String requestReason) {
        boolean f = false;
        try {
            String sql = "Update Request Set courseId = ?, requestReason = ? Where username = ? and courseId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, newCourseId);
            ps.setString(2, requestReason);
            ps.setString(3, username);
            ps.setInt(4, oldCourseId);
            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public boolean deleteRequest(int courseId, String username) {
        boolean f = false;
        String sql = "DELETE FROM Request WHERE courseId = ? and username = ?";
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
        RequestDAO dao = new RequestDAO();
//        List<Request> list = dao.getRequestByStatus(0);
//        for (Request l : list) {
//            System.out.println(l);
//        }
//        Date date = new Date();
//        int courseId = 1;
////        dao.updateRequest(1, 2, "anmentor", "helo");
//        Request r = dao.getRequestByUsername("anmentor", 1);
//        System.out.println(r);
////        dao.addRequest(new Request(1, "anmentor", date, 0, "1"));
////        List<Request> list = dao.getAllRequestByUsername("anmentor");
////        for (Request l : list) {
////            System.out.println(l);
////        }
    dao.deleteRequest2(20, "ducmentor");
    }

    public List<Request> getRequestByStatus(int status) {
        String sql = "SELECT * FROM [Request] WHERE requestStatus = " + status;
        List<Request> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                int courseId = rs.getInt("courseId");
                Date requestTime = rs.getDate("requestTime");
                String requestReason = rs.getString("requestReason");
                Request r = new Request(courseId, username, requestTime, status, requestReason);
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return list;
    }

    public void changeRequest(String menId, int couId, int i) {
        String sql = "UPDATE [dbo].[Request]\n"
                + "   SET [requestStatus] = ?\n"
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

    public void deleteRequest2(int courseId, String username) {
        String sql = "DELETE FROM Request WHERE courseId = ? and username = ?";
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
