/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author yeuda
 */
public class Message {

    private int conversationId;
    private String sentBy;
    private Timestamp sentAt;
    private String msgContent;
    private String contentType;
    private int messageId;

    public Message(int conversationId, String sentBy, Timestamp sentAt, String msgContent, String contentType, int messageId) {
        this.conversationId = conversationId;
        this.sentBy = sentBy;
        this.sentAt = sentAt;
        this.msgContent = msgContent;
        this.contentType = contentType;
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    
   

    public Message() {
    }

    public Message(int conversationId, String sentBy, Timestamp sentAt, String msgContent, String contentType) {
        this.conversationId = conversationId;
        this.sentBy = sentBy;
        this.sentAt = sentAt;
        this.msgContent = msgContent;
        this.contentType = contentType;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Message{" + "conversationId=" + conversationId + ", sentBy=" + sentBy + ", sentAt=" + sentAt + ", msgContent=" + msgContent + ", contentType=" + contentType + ", messageId=" + messageId + '}';
    }

    
    
    
}
