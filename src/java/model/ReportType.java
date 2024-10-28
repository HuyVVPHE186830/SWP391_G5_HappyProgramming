package model;

import java.util.ArrayList;
import java.util.List;

public class ReportType {

    private int reportTypeId;
    private String reportName;
    private String reportDescription;
    private List<Report> reports;

    public ReportType() {
        reports = new ArrayList<>();
    }

    public ReportType(int reportTypeId, String reportName, String reportDescription, List<Report> reports) {
        this.reportTypeId = reportTypeId;
        this.reportName = reportName;
        this.reportDescription = reportDescription;
        this.reports = reports;
    }
    
    public ReportType(int reportTypeId, String reportName, String reportDescription) {
        this.reportTypeId = reportTypeId;
        this.reportName = reportName;
        this.reportDescription = reportDescription;
    }

    public int getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(int reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    @Override
    public String toString() {
        return "ReportType{" + "reportTypeId=" + reportTypeId + ", reportName=" + reportName + ", reportDescription=" + reportDescription + '}';
    }
}
