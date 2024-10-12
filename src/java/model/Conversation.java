/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author yeuda
 */
public class Conversation {

    private int conversationId;
    private String conversationName;
    private int courseId;

    public Conversation() {
    }

    public Conversation(int conversationId, String conversationName, int courseId) {
        this.conversationId = conversationId;
        this.conversationName = conversationName;
        this.courseId = courseId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Conversaiton{" + "conversationId=" + conversationId + ", conversationName=" + conversationName + ", courseId=" + courseId + '}';
    }
    
    

}
