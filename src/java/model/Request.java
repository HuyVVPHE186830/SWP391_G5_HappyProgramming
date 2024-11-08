/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Request {

    private int courseId;
    private String username;
    private Date requestTime;
    private int requestStatus;
    private String requestReason;
    private String mentorUsername;

    public Request() {
    }

    public Request(int courseId, String username, Date requestTime, int requestStatus, String requestReason, String mentorUsername) {
        this.courseId = courseId;
        this.username = username;
        this.requestTime = requestTime;
        this.requestStatus = requestStatus;
        this.requestReason = requestReason;
        this.mentorUsername = mentorUsername;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public String getMentorUsername() {
        return mentorUsername;
    }

    public void setMentorUsername(String mentorUsername) {
        this.mentorUsername = mentorUsername;
    }
    

    @Override
    public String toString() {
        return "Request{" + "courseId=" + courseId + ", username=" + username + ", requestTime=" + requestTime + ", requestStatus=" + requestStatus + ", requestReason=" + requestReason + '}';
    }

}
