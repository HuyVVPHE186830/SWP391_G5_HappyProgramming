package model;

import java.sql.Timestamp;

public class Report {

    private BlogComment comment;
    private User user;
    private Timestamp reportTime;
    private ReportType reportType;
    private String reportContent;

    public Report() {
    }

    public Report(BlogComment comment, User user, Timestamp reportTime, ReportType reportType, String reportContent) {
        this.comment = comment;
        this.user = user;
        this.reportTime = reportTime;
        this.reportType = reportType;
        this.reportContent = reportContent;
    }

    public BlogComment getComment() {
        return comment;
    }

    public void setComment(BlogComment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }
}
