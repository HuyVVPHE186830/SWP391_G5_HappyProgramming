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

    public Request() {
    }

    public Request(int courseId, String username, Date requestTime, int requestStatus, String requestReason) {
        this.courseId = courseId;
        this.username = username;
        this.requestTime = requestTime;
        this.requestStatus = requestStatus;
        this.requestReason = requestReason;
    }

    @Override
    public String toString() {
        return "Request{" + "courseId=" + courseId + ", username=" + username + ", requestTime=" + requestTime + ", requestStatus=" + requestStatus + ", requestReason=" + requestReason + '}';
    }
    
}
