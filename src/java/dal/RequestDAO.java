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
import model.Participate;
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
                String mentorUsername = rs.getString("mentorUsername");
                Request r = new Request(courseId, username, requestTime, requestStatus, requestReason, mentorUsername);
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Request> getAllRequestByUsernameForList(String username) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM [Request] where username = ? order by requestTime DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                Date requestTime = rs.getDate("requestTime");
                int requestStatus = rs.getInt("requestStatus");
                String requestReason = rs.getString("requestReason");
                String mentorUsername = rs.getString("mentorUsername");
                Request r = new Request(courseId, username, requestTime, requestStatus, requestReason, mentorUsername);
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Request> getAllRequestByUsername(String username) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM [Request] where username = ? and requestStatus != -1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                Date requestTime = rs.getDate("requestTime");
                int requestStatus = rs.getInt("requestStatus");
                String requestReason = rs.getString("requestReason");
                String mentorUsername = rs.getString("mentorUsername");
                Request r = new Request(courseId, username, requestTime, requestStatus, requestReason, mentorUsername);
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Request> getAllRequestOfMentorByKeyword(String keyword, String username) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM [Request] JOIN Course ON Request.CourseId = Course.CourseId WHERE username = ? AND (Course.CourseName LIKE ? OR Request.RequestReason LIKE ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, "%" + keyword + "%");
            st.setString(3, "%" + keyword + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("CourseId");
                Date requestTime = rs.getDate("requestTime");
                int requestStatus = rs.getInt("requestStatus");
                String requestReason = rs.getString("requestReason");
                String mentorUsername = rs.getString("mentorUsername");
                Request r = new Request(courseId, username, requestTime, requestStatus, requestReason, mentorUsername);
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Request> getAllRequestMentorByKeyword(String keyword) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM [Request] "
                + "JOIN Course ON Request.CourseId = Course.CourseId "
                + "JOIN [User] ON [User].username = Request.username "
                + "WHERE [User].roleId = 2 AND (Course.CourseName LIKE ? "
                + "OR Request.RequestReason LIKE ? "
                + "OR [User].firstName LIKE ? "
                + "OR [User].lastName LIKE ? "
                + "OR [User].Username LIKE ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + keyword + "%");
            st.setString(2, "%" + keyword + "%");
            st.setString(3, "%" + keyword + "%");
            st.setString(4, "%" + keyword + "%");
            st.setString(5, "%" + keyword + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("CourseId");
                String username = rs.getString("username");
                Date requestTime = rs.getDate("requestTime");
                int requestStatus = rs.getInt("requestStatus");
                String requestReason = rs.getString("requestReason");
                String mentorUsername = rs.getString("mentorUsername");
                Request r = new Request(courseId, username, requestTime, requestStatus, requestReason, mentorUsername);
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Request> getAllRequestByRole(int roleId) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM [Request] JOIN [User] ON Request.Username = [User].Username "
                + "WHERE [User].roleId = ? "
                + "ORDER BY Request.requestTime";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roleId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("CourseId");
                String username = rs.getString("username");
                Date requestTime = rs.getDate("requestTime");
                int requestStatus = rs.getInt("requestStatus");
                String requestReason = rs.getString("requestReason");
                String mentorUsername = rs.getString("mentorUsername");
                Request r = new Request(courseId, username, requestTime, requestStatus, requestReason, mentorUsername);
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
                String mentorUsername = rs.getString("mentorUsername");
                request = new Request(courseId, username, requestTime, requestStatus, requestReason, mentorUsername);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return request;
    }

    public Request getRequestByUsernameAndMentorUsername(String username, int courseId, String mentorUsername) {
        Request request = new Request();
        String sql = "SELECT * FROM [Request] where username = ? and courseId = ? and mentorUsername = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setInt(2, courseId);
            st.setString(3, mentorUsername);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Date requestTime = rs.getDate("requestTime");
                int requestStatus = rs.getInt("requestStatus");
                String requestReason = rs.getString("requestReason");
                request = new Request(courseId, username, requestTime, requestStatus, requestReason, mentorUsername);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return request;
    }

    public void addRequest(Request request) {
        String sql = "INSERT INTO [Request] (courseId, username, requestTime, requestStatus, requestReason, mentorUsername) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, request.getCourseId());
            st.setString(2, request.getUsername());
            st.setDate(3, new java.sql.Date(request.getRequestTime().getTime()));
            st.setInt(4, request.getRequestStatus());
            st.setString(5, request.getRequestReason());
            st.setString(6, request.getMentorUsername());
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

    public boolean updateRequestStatusForMentor(Request request) {
        boolean f = false;
        try {
            String sql = "Update Request Set requestStatus = ?, requestTime = ?, requestReason = ? Where username = ? and courseId = ? and mentorUsername = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, request.getRequestStatus());
            ps.setDate(2, new java.sql.Date(request.getRequestTime().getTime()));
            ps.setString(3, request.getRequestReason());
            ps.setString(4, request.getUsername());
            ps.setInt(5, request.getCourseId());
            ps.setString(6, request.getMentorUsername());
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
        String sql = "DELETE FROM Request WHERE courseId = ? and username = ? and mentorUsername = ? and requestStatus != 1";
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

    public static void main(String[] args) {
        RequestDAO dao = new RequestDAO();
        ParticipateDAO a = new ParticipateDAO();
        Date date = new Date();
        List<Request> r = dao.getAllRequestByUsernameForList("anmentor");
        List<Request> requests = dao.getAll();
        List<Participate> p = a.getAllByUsername("anmentor");
        for (Request l : r) {
            System.out.println(l);
        }
        Participate par = a.getParticipateByUsernameAndCourseIdAndMentorUsername(1, "anmentor", "anmentor");
        boolean found1 = false;
        for (Request r1 : requests) {
            if (par.getCourseId() == r1.getCourseId() && par.getUsername().equals(r1.getUsername()) && par.getMentorUsername().equals(r1.getMentorUsername())) {
                found1 = true;
                break;
            }

        }
        System.out.println(found1);

        a.updateParticipateStatusForMentor(new Participate(7, "anmentor", 2, 0, "anmentor"));
        dao.updateRequestStatusForMentor(new Request(7, "anmentor", date, 0, "e", "anmentor"));
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
        String sql = "	SELECT * \n"
                + "FROM [Request] r \n"
                + "JOIN [Participate] p ON r.courseId = p.courseId \n"
                + "JOIN [User] u ON u.username = p.username \n"
                + "WHERE r.requestStatus = " + status + " and p.statusId  =" + status + " and p.username  = r.username and u.roleId = 2;";/// Fix this after, status must be 0 in both Request and Participate(fix count request in mentor list screen)
        List<Request> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                int courseId = rs.getInt("courseId");
                Date requestTime = rs.getDate("requestTime");
                String requestReason = rs.getString("requestReason");
                String mentorUsername = rs.getString("mentorUsername");
                Request r = new Request(courseId, username, requestTime, status, requestReason, mentorUsername);
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
