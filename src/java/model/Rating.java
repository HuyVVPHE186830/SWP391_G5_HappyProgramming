/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author yeuda
 */
public class Rating {
    private String ratedFromUser, ratedToUser;
    private float noStar;
    private int courseId;
    private String ratingComment;

    public Rating() {
    }

    public Rating(String ratedFromUser, String ratedToUser, float noStar, int courseId, String ratingComment) {
        this.ratedFromUser = ratedFromUser;
        this.ratedToUser = ratedToUser;
        this.noStar = noStar;
        this.courseId = courseId;
        this.ratingComment = ratingComment;
    }

    public String getRatedFromUser() {
        return ratedFromUser;
    }

    public void setRatedFromUser(String ratedFromUser) {
        this.ratedFromUser = ratedFromUser;
    }

    public String getRatedToUser() {
        return ratedToUser;
    }

    public void setRatedToUser(String ratedToUser) {
        this.ratedToUser = ratedToUser;
    }

    public float getNoStar() {
        return noStar;
    }

    public void setNoStar(int noStar) {
        this.noStar = noStar;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getRatingComment() {
        return ratingComment;
    }

    public void setRatingComment(String ratingComment) {
        this.ratingComment = ratingComment;
    }

    @Override
    public String toString() {
        return "Rating{" + "ratedFromUser=" + ratedFromUser + ", ratedToUser=" + ratedToUser + ", noStar=" + noStar + ", courseId=" + courseId + ", ratingComment=" + ratingComment + '}';
    }
     
    
}
