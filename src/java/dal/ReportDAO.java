package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Report;
import model.ReportType;

public class ReportDAO extends DBContext {

    public static void main(String[] args) {
        ReportDAO dao = new ReportDAO();
        List<ReportType> reportTypes = dao.getAllReportTypes();
        for (ReportType reportType : reportTypes) {
            System.out.println(reportType.toString());
        }
    }

    public List<ReportType> getAllReportTypes() {
        List<ReportType> list = new ArrayList<>();
        String sql = "SELECT * FROM ReportType";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int reportTypeId = rs.getInt("reportTypeId");
                String reportName = rs.getString("reportName");
                String reportDescription = rs.getString("reportDescription");

                list.add(new ReportType(reportTypeId, reportName, reportDescription));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public ReportType getReportTypeById(int id) {
        ReportType reportType = null;
        String sql = "SELECT * FROM ReportType WHERE reportTypeId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                reportType = new ReportType();
                reportType.setReportTypeId(id);
                reportType.setReportName("reportName");
                reportType.setReportDescription("reportDescription");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportType;
    }

    public boolean addReport(Report report) {
        String sql = "INSERT INTO Report (commentId, reportedBy, reportTime, reportTypeId, reportContent) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, report.getComment().getCommentId());
            ps.setString(2, report.getUser().getUsername());
            ps.setTimestamp(3, report.getReportTime());
            ps.setInt(4, report.getReportType().getReportTypeId());
            ps.setString(5, report.getReportContent());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
}
