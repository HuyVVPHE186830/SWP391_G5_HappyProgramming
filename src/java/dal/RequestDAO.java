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

    public static void main(String[] args) {
        RequestDAO dao = new RequestDAO();
        List<Request> list = dao.getAll();
        for (Request l : list) {
            System.out.println(l);
        }
    }
}
