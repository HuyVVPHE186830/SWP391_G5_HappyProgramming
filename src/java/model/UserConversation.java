/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author yeuda
 */
public class UserConversation {

    private int conversationId;
    private String username;

    public UserConversation() {
    }

    public UserConversation(int conversationId, String username) {
        this.conversationId = conversationId;
        this.username = username;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserConversation{" + "conversationId=" + conversationId + ", username=" + username + '}';
    }
    
    
}
