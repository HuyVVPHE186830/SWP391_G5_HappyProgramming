<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Message Chat</title>
   <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            display: flex;
            height: 100vh;
        }
        .sidebar {
            width: 20%;
            background-color: #f4f4f4;
            padding: 10px;
            border-right: 1px solid #ccc;
            overflow-y: auto;
        }
        .chat-area {
            width: 60%;
            display: flex;
            flex-direction: column;
            padding: 10px;
            background-color: #fff;
            overflow-y: auto;
        }
        .user-info {
            width: 20%;
            background-color: #e9ecef;
            padding: 10px;
            border-left: 1px solid #ccc;
        }
        .message {
            margin: 5px 0;
        }
        .send-message {
            display: flex;
            margin-top: auto; /* Pushes the input to the bottom */
        }
        .send-message input {
            flex: 1; /* Allow the input to take full width */
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-right: 5px; /* Space between input and button */
        }
        .send-message button {
            padding: 10px;
            background-color: #5e3fd3;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .send-message button:hover {
            background-color: #4cae4c;
        }
        @media (max-width: 768px) {
            body {
                flex-direction: column;
            }
            .sidebar, .chat-area, .user-info {
                width: 100%;
                border: none;
                padding: 5px;
            }
            .sidebar {
                border-bottom: 1px solid #ccc;
            }
            .user-info {
                border-top: 1px solid #ccc;
            }
        }
    </style>
</head>
<body>

    <div class="sidebar">
        <h3>Previous Conversations</h3>
        <c:forEach items="${sessionScope.conversations}" var="conversation">
            <div class="message">
                <a href="message?conversationId=${conversation.conversationId}">${conversation.conversationName}</a>
            </div>
        </c:forEach>
    </div>
    <!-- ----------------------------------------------------------------------------------------------------------- -->
    <div class="chat-area">
        <h3>Chat with ${sessionScope.currentChatRecipient.firstName} ${sessionScope.currentChatRecipient.lastName}</h3>
        <div class="messages">
            <c:forEach items="${sessionScope.currentChatMessages}" var="message">
                <div class="message">
                    <strong>${message.sentBy}:</strong> ${message.msgContent} <em>(${message.sentAt})</em>
                </div>
            </c:forEach>
        </div>

        <div class="send-message">
            <form action="sendMessage" method="post" style="width: 100%; display: flex;">
                <input type="text" name="message" placeholder="Your message" required>
                <input type="hidden" name="recipient" value="${sessionScope.currentChatRecipient.username}">
                <input type="hidden" name="conversationId" value="${sessionScope.currentConversationId}">
                <button type="submit">Send</button>
            </form>
        </div>
    </div>
    <!-- ----------------------------------------------------------------------------------------------------------- -->

    <div class="user-info">
        <h3>Recipient's Information</h3>
        <p>Name: ${sessionScope.currentChatRecipient.firstName} ${sessionScope.currentChatRecipient.lastName}</p>
        <p>Status: ${sessionScope.currentChatRecipient.status}</p>
        <p>Last seen: ${sessionScope.currentChatRecipient.lastSeen}</p>
    </div>

</body>
</html>
