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
                padding: 10px;
                position: relative;
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
                position: relative;
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
                position: relative;
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
                margin-top: auto;
                position: sticky;
                bottom: 0;
                background-color: #fff;
                padding: 10px;
                box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
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
            .ms-options-icon {
                cursor: pointer;
                font-size: 18px;
                position: absolute;
                right: 10px;
                top: 10px;
            }
            .ms-options-menu {
                display: none;
                position: absolute;
                background-color: white;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 5px;
                z-index: 1;
                min-width: 120px;
                padding: 8px 0;
                margin-top: 0;
                right: 0;
            }
            .message:hover .ms-options-menu {
                display: block;
            }

            .ms-options-menu a,
            .cv-options-menu a {
                display: block;
                padding: 8px 12px;
                color: black;
                text-decoration: none;
            }
            .ms-options-menu a:hover,
            .action-icons {
                display: flex;
                gap: 10px;
                margin-top: 10px;
            }
            .action-icons button {
                background: none;
                border: none;
                cursor: pointer;
                font-size: 20px;
            }
            /* Style for the input field */
            .styled-input {
                padding: 8px; /* Slightly smaller padding */
                border: 1px solid #ccc;
                border-radius: 5px;
                width: calc(100% - 22px); /* Adjust width */
                margin-right: 5px; /* Less spacing between input and button */
            }
            /* Style for the button */
            .styled-button {
                background-color: #5e3fd3; /* Purple background */
                color: white; /* White text */
                border: none; /* No border */
                border-radius: 5px; /* Rounded corners */
                padding: 8px 12px; /* Smaller padding */
                cursor: pointer; /* Pointer cursor */
                font-size: 14px; /* Smaller font size */
            }
            .input-group {
                display: flex;
                align-items: center; /* Center align items vertically */
            }
            #editToggle:checked ~ .edit-form {
                display: block; /* Show edit form when checked */
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <h3>Conversations</h3>
            <div class="search-container">
                <input type="text" id="searchInput" placeholder="Search..." onkeyup="searchConversations()">
            </div>
            <div class="conversation-list">
                <c:set var="displayedConversations" value=""/>
                <c:forEach items="${sessionScope.conversations}" var="conversation">
                    <c:set var="avatarPath" value=""/>
                    <c:set var="hasUser" value="false"/>
                    <c:forEach items="${sessionScope.userConversation}" var="userConversation">
                        <c:if test="${userConversation.conversationId == conversation.conversationId}">
                            <c:forEach items="${sessionScope.userList2}" var="userList">
                                <c:if test="${userList.username == userConversation.username}">
                                    <c:if test="${userList.avatarPath != null && userList.avatarPath != ''}">
                                        <c:set var="avatarPath" value="data:image/jpeg;base64,${userList.avatarPath}"/>
                                    </c:if>
                                    <c:set var="hasUser" value="true"/>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                    <c:if test="${hasUser}">
                        <div class="conversation-item" data-username="${conversation.conversationName}" onclick="location.href = 'sendMessage?conversationId=${conversation.conversationId}'">
                            <img src="${avatarPath != '' ? avatarPath : 'path/to/default-avatar.png'}" alt="Avatar" class="avatar-image">
                            <div class="conversation-name">${conversation.conversationName}</div>
                        </div>
                    </c:if>
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
                        <div class="ms-options-icon" tabindex="0">&#x22EE;</div>
                        <div class="ms-options-menu">
                            <form action="manageConversation?action=edit-message" method="post" style="display: inline;" id="editForm-${message.messageId}">
                                <input type="hidden" name="conversationId" value="${currentConversationId}">
                                <input type="hidden" name="messageId" value="${message.messageId}"> 
                            </form>
                            <a href="#" onclick="if (confirm('Are you sure you want to savethis message?')) {
                                        document.getElementById('editForm-${message.messageId}').submit();
                                    }">Edit</a>                       
                            <form action="manageConversation?action=delete-message" method="post" style="display: inline;" id="deleteForm-${message.messageId}">
                                <input type="hidden" name="conversationId" value="${currentConversationId}">
                                <input type="hidden" name="messageId" value="${message.messageId}"> 
                            </form>
                            <a href="#" onclick="if (confirm('Are you sure you want to delete this message?')) {
                                        document.getElementById('deleteForm-${message.messageId}').submit();
                                    }">Delete</a>                       
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="send-message">
                <form action="sendMessage" method="post" style="width: 100%; display: flex;">
                    <input type="text" name="message" placeholder="Your message" required>
                    <input type="hidden" name="recipient" value="${currentChatRecipient.username}">
                    <input type="hidden" name="conversationId" value="${currentConversationId}">
                    <button type="submit">Send</button>
                </form>
            </div>
        </div>
        <div class="user-info">
            <h3>Conversation Manage</h3>
            <div class="user-details">
                <img src="data:image/jpeg;base64,${sessionScope.currentChatRecipient.avatarPath}" alt="Avatar" class="avatar-image">
                <p>Name: ${sessionScope.currentChatRecipient.firstName} ${sessionScope.currentChatRecipient.lastName}</p>
                <p>DOB: ${sessionScope.currentChatRecipient.dob}</p>
                <p>Email: ${sessionScope.currentChatRecipient.mail}</p>
                <p>Status: ${sessionScope.currentChatRecipient.activeStatus}</p>
                <p>Role: 
                    ${sessionScope.currentChatRecipient.roleId == 1 ? 'Mentee' : 
                      (sessionScope.currentChatRecipient.roleId == 2 ? 'Mentor' : 'Unknown Role')}
                </p>    
                <p>
                    <c:if test="${currentChatRecipient.roleId == 2}">
                        <a href="#miniProfileModal_${username}" data-toggle="modal" class="btn" style="background-color: #5e3fd3; color: white;">View</a>
                    </c:if>
                    <button class="btn" style="background-color: #5e3fd3; color: white;" data-toggle="modal" data-target="#ratingModal">Rate</button>
                </p> 

                <div class="action-icons">
                    <input type="checkbox" id="editToggle" style="display:none;">
                    <label for="editToggle" style="cursor: pointer;">✏️</label>
                    <form action="manageConversation?action=delete-conversation" method="post" style="display: inline;">
                        <input type="hidden" name="conversationId" value="${currentConversationId}">
                        <button type="submit" onclick="return confirm('Are you sure you want to delete this conversation?');">🗑️</button>
                    </form>
                </div>
                <br><br>
                <div class="edit-form" style="display: none;">
                    <form action="manageConversation?action=edit-conversation" method="post">
                        <input type="hidden" name="conversationId" value="${currentConversationId}">
                        <div class="input-group">
                            <input type="text" name="newConversationName" placeholder="New Conversation Name" required class="styled-input">
                            <button type="submit" class="styled-button">Update</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Rating Modal -->
        <div class="modal fade" id="ratingModal" tabindex="-1" aria-labelledby="ratingModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="ratingModalLabel">Feedback about  ${currentChatRecipient.username}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="Rating?action=rate-recipient" method="post">
                            <input type="hidden" name="recipientUsername" value="${sessionScope.currentChatRecipient.username}">
                            <input type="hidden" name="currentUser" value="${sessionScope.currentUser.username}">
                            <div class="mb-3">
                                <label for="courseId" class="form-label">Select Course:</label>
                                <select name="courseId" id="courseId" class="form-select" required>
                                    <option value="">--Choose course--</option>
                                    <c:forEach items ="${listUser4}" var = "u">
                                        <c:if test = "${u.username == currentChatRecipient.username}">
                                            <c:forEach items ="${listParticipate4}" var = "p">
                                                <c:if test = "${p.username == u.username}">
                                                    <c:forEach items ="${listCourse4}" var = "c">
                                                        <c:if test = "${c.courseId == p.courseId}">
                                                            <option value = "${c.courseId}">${c.courseName}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="rating" class="form-label">Select Rating:</label>
                                <select name="rating" id="rating" class="form-select" required>
                                    <option value="">--Choose a rating--</option>
                                    <option value="1">1 Star</option>
                                    <option value="2">2 Stars</option>
                                    <option value="3">3 Stars</option>
                                    <option value="4">4 Stars</option>
                                    <option value="5">5 Stars</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="comments" class="form-label">Comments:</label>
                                <textarea name="comments" id="comments" class="form-control" rows="3"></textarea>
                            </div>
                            <button type="submit" style="background-color: #5e3fd3; color: white;" class="btn btn-primary">Submit Rating</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Include Bootstrap CSS and JS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                            document.getElementById('editToggle').addEventListener('change', function () {
                                const editForm = document.querySelector('.edit-form');
                                editForm.style.display = this.checked ? 'block' : 'none';
                            });
        </script>
        <script>
            function searchConversations() {
                const input = document.getElementById('searchInput');
                const filter = input.value.toLowerCase();
                const conversationItems = document.getElementsByClassName('conversation-item');

                for (let i = 0; i < conversationItems.length; i++) {
                    const username = conversationItems[i].getAttribute('data-username').toLowerCase();
                    if (username.includes(filter)) {
                        conversationItems[i].style.display = '';
                    } else {
                        conversationItems[i].style.display = 'none';
                    }
                }
            }

            function fetchLatestMessages() {
                const conversationId = '${currentConversationId}';
                const recipientUsername = '${currentChatRecipient.username}';

                const url = 'sendMessage?conversationId=' + conversationId + '&username=' + recipientUsername;

                fetch(url)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.text();
                        })
                        .then(data => {
                            const parser = new DOMParser();
                            const doc = parser.parseFromString(data, 'text/html');

                            const newMessages = doc.querySelector('.messages').innerHTML;

                            const messagesContainer = document.querySelector('.messages');
                            messagesContainer.innerHTML = newMessages;
                        })
                        .catch(error => console.error('Error fetching messages:', error));
            }

            setInterval(fetchLatestMessages, 2000);
        </script>


    </body>
</html>
