package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {

    private int courseId;
    private String courseName;
    private String courseDescription;
    private Date createdAt;
    private int countMentee;
    private int countMentor;

    public Course(int courseId, String courseName, String courseDescription, Date createdAt, int countMentee, int countMentor, List<Category> categories) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.createdAt = createdAt;
        this.countMentee = countMentee;
        this.countMentor = countMentor;
        this.categories = categories;
    }

    public int getCountMentor() {
        return countMentor;
    }

    public void setCountMentor(int countMentor) {
        this.countMentor = countMentor;
    }

    public Course(int courseId, String courseName, String courseDescription, Date createdAt, int countMentee, List<Category> categories) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.createdAt = createdAt;
        this.countMentee = countMentee;
        this.categories = categories;
    }

    public int getCountMentee() {
        return countMentee;
    }

    public void setCountMentee(int countMentee) {
        this.countMentee = countMentee;
    }
    
    

    private List<Category> categories;  // A course can belong to multiple categories

    // Constructors
    public Course() {
        categories = new ArrayList<>();
    }

    public Course(int courseId, String courseName, String courseDescription, Date createdAt, List<Category> categories) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.createdAt = createdAt;
        this.categories = categories;
    }
    
    public Course(int courseId, String courseName, String courseDescription, Date createdAt) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.createdAt = createdAt;
    }

    public Course(int courseId, String courseName, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Course{" + "courseId=" + courseId + ", courseName=" + courseName + ", courseDescription=" + courseDescription + ", createdAt=" + createdAt + '}';
    }


}
