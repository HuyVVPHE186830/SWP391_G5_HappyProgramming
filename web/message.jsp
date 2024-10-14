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
            background-color: #f0f2f5;
        }
        .sidebar {
            width: 25%;
            background-color: #fff;
            padding: 10px;
            border-right: 1px solid #ccc;
            overflow-y: auto;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
        }
        .sidebar h3 {
            text-align: center;
            font-size: 1.5em;
            margin-bottom: 10px;
        }
        .search-container {
            margin-bottom: 15px;
            display: flex;
            justify-content: center;
        }
        .search-container input {
            width: 80%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            outline: none;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .conversation-list {
            max-height: 800px;
            overflow-y: auto;
            padding: 5px;
        }
        .conversation-item {
            display: flex;
            align-items: center;
            padding: 8px;
            border-radius: 5px;
            margin-bottom: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            background-color: #fff;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }
        .conversation-item:hover {
            background-color: #f1f1f1;
        }
        .conversation-item img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
        }
        .chat-area {
            width: 50%;
            display: flex;
            flex-direction: column;
            padding: 10px;
            background-color: #fff;
            overflow-y: auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            position: relative; /* Để định vị phần gửi tin nhắn */
        }
        .recipient-header {
            padding: 10px;
            background-color: #e9ecef;
            border-radius: 5px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            margin-bottom: 10px;
            text-align: center;
        }
        .user-info {
            width: 25%;
            background-color: #e9ecef;
            padding: 10px;
            border-left: 1px solid #ccc;
            box-shadow: -2px 0 5px rgba(0, 0, 0, 0.1);
        }
        .message {
            margin: 5px 0;
            padding: 10px;
            border-radius: 5px;
        }
        .message.sent {
            background-color: #d1e7dd;
            align-self: flex-end;
        }
        .message.received {
            background-color: #f8d7da;
            align-self: flex-start;
        }
        .send-message {
            display: flex;
            margin-top: auto; /* Đẩy input xuống cuối */
            position: sticky; /* Cố định ở cuối */
            bottom: 0; /* Đặt ở dưới cùng */
            background-color: #fff; /* Nền cho phần gửi tin nhắn */
            padding: 10px; /* Khoảng cách cho phần gửi */
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1); /* Bóng đổ cho phần gửi */
        }
        .send-message input {
            flex: 1;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-right: 5px;
            outline: none;
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
        .user-info .user-details {
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }
        .user-info .avatar-image {
            width: 200px;
            height: 200px;
            border-radius: 50%;
            margin-bottom: 10px;
        }
        .options-icon {
            margin-left: auto;
            cursor: pointer;
            font-size: 24px;
            color: #888;
            transition: color 0.3s;
        }
        .options-icon:hover {
            color: #333;
        }
        .options-menu {
            display: none;
            position: absolute;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            z-index: 1;
            margin-left: 300px; /* Khoảng cách giữa biểu tượng và menu */
        }
        .conversation-item:hover .options-menu {
            display: block; /* Hiện menu khi hover vào ô hội thoại */
        }
        .options-menu a {
            display: block;
            padding: 10px;
            text-decoration: none;
            color: #333;
        }
        .options-menu a:hover {
            background-color: #f1f1f1; /* Đổi màu khi hover */
        }
    </style>
</head>
<body>

<div class="sidebar">
    <h3>Cuộc trò chuyện trước</h3>
    <div class="search-container">
        <input type="text" id="searchInput" placeholder="Tìm kiếm cuộc hội thoại..." onkeyup="searchConversations()">
    </div>
    <div class="conversation-list">
        <c:forEach items="${sessionScope.conversations}" var="conversation">
            <c:forEach items="${sessionScope.userConversation}" var="userConversation">
                <c:if test="${userConversation.conversationId == conversation.conversationId}">
                    <c:forEach items="${sessionScope.userList2}" var="userList">
                        <c:if test="${userList.username == userConversation.username}">
                            <div class="conversation-item" data-username="${conversation.conversationName}" onclick="location.href = 'sendMessage?conversationId=${conversation.conversationId}'">
                                <img src="data:image/jpeg;base64,${userList.avatarPath}" alt="Avatar" class="avatar-image">
                                <div>${conversation.conversationName}</div>
                                <div class="options-icon">&#x22EE;</div>
                                <div class="options-menu">
                                    <a href="editConversation?conversationId=${conversation.conversationId}">Chỉnh sửa</a>
                                    <a href="deleteConversation?conversationId=${conversation.conversationId}">Xóa</a>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </c:if>
            </c:forEach>
        </c:forEach>
    </div>
</div>

<div class="chat-area">
    <div class="recipient-header">
        <h3>${currentChatRecipient.firstName} ${sessionScope.currentChatRecipient.lastName}</h3>
    </div>
    <div class="messages">
        <c:forEach items="${currentChatMessages}" var="message">
            <div class="message ${message.sentBy == sessionScope.currentUser.username ? 'sent' : 'received'}">
                <strong>${message.sentBy}:</strong> ${message.msgContent} <em>(${message.sentAt})</em>
            </div>
        </c:forEach>
    </div>

    <div class="send-message">
        <form action="sendMessage" method="post" style="width: 100%; display: flex;">
            <input type="text" name="message" placeholder="Tin nhắn của bạn" required>
            <input type="hidden" name="recipient" value="${currentChatRecipient.username}">
            <input type="hidden" name="conversationId" value="${currentConversationId}">
            <button type="submit">Gửi</button>
        </form>
    </div>
</div>

<div class="user-info">
    <h3>Thông tin người nhận</h3>
    <div class="user-details">
        <img src="data:image/jpeg;base64,${sessionScope.currentChatRecipient.avatarPath}" alt="Avatar" class="avatar-image">
        <p>Tên: ${sessionScope.currentChatRecipient.firstName} ${sessionScope.currentChatRecipient.lastName}</p>
        <p>Ngày sinh: ${sessionScope.currentChatRecipient.dob}</p>
        <p>Email: ${sessionScope.currentChatRecipient.mail}</p>
        <p>Trạng thái: ${sessionScope.currentChatRecipient.activeStatus}</p>
    </div>
</div>

<script>
    function searchConversations() {
        const input = document.getElementById('searchInput');
        const filter = input.value.toLowerCase();
        const conversationItems = document.getElementsByClassName('conversation-item');

        for (let i = 0; i < conversationItems.length; i++) {
            const username = conversationItems[i].getAttribute('data-username').toLowerCase();
            if (username.includes(filter)) {
                conversationItems[i].style.display = ''; // Hiện nếu tìm thấy
            } else {
                conversationItems[i].style.display = 'none'; // Ẩn nếu không tìm thấy
            }
        }
    }
</script>

</body>
</html>