/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Participate {

    private int courseId;
    private String username;
    private int participateRole;
    private int statusId;
    private String mentorUsername;
    
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

    public int getParticipateRole() {
        return participateRole;
    }

    public void setParticipateRole(int participateRole) {
        this.participateRole = participateRole;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getMentorUsername() {
        return mentorUsername;
    }

    public void setMentorUsername(String mentorUsername) {
        this.mentorUsername = mentorUsername;
    }
    

    public Participate(int courseId, String username, int participateRole, int statusId, String mentorUsername) {
        this.courseId = courseId;
        this.username = username;
        this.participateRole = participateRole;
        this.statusId = statusId;
        this.mentorUsername = mentorUsername;
    }

    public Participate() {
    }

}
