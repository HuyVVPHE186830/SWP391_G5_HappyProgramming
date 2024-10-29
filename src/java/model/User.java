package model;

import java.util.Date;

public class User {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date dob;
    private String mail;
    private Date createdDate;
    private String avatarPath;
    private String cvPath;
    private boolean activeStatus;
    private boolean isVerified;
    private String verificationCode;
    private int roleId;

    // Constructors
    public User() {
    }

    public User(int id, String username, String password, String firstName, String lastName, Date dob, String mail, Date createdDate, String avatarPath, String cvPath, boolean activeStatus, boolean isVerified, String verificationCode, int roleId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.mail = mail;
        this.createdDate = createdDate;
        this.avatarPath = avatarPath;
        this.cvPath = cvPath;
        this.activeStatus = activeStatus;
        this.isVerified = isVerified;
        this.verificationCode = verificationCode;
        this.roleId = roleId;
    }

    public User(String username, String password, String firstName, String lastName, Date dob, String mail, Date createdDate, String avatarPath, String cvPath, boolean activeStatus,boolean isVerified, int roleId) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.mail = mail;
        this.createdDate = createdDate;
        this.avatarPath = avatarPath;
        this.cvPath = cvPath;
        this.activeStatus = activeStatus;
        this.isVerified = isVerified;
        this.roleId = roleId;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getCvPath() {
        return cvPath;
    }

    public void setCvPath(String cvPath) {
        this.cvPath = cvPath;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", mail=" + mail + ", createdDate=" + createdDate + ", activeStatus=" + activeStatus + ", isVerified=" + isVerified + ", verificationCode=" + verificationCode + ", roleId=" + roleId + '}';
    }
    
}
