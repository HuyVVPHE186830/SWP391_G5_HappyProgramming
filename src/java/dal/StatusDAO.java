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
import model.Status;

/**
 *
 * @author Admin
 */
public class StatusDAO extends DBContext {
public List<Status> getAll() {
        List<Status> list = new ArrayList<>();
        String sql = "SELECT * FROM [Status]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int statusId = rs.getInt("statusId");
                String statusName = rs.getString("statusName");
                Status s = new Status(statusId, statusName);
                list.add(s);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
}
