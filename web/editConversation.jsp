<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Edit Conversation</title>
    <style>
        .dialog {
            display: block; /* Hiện dialog */
            position: fixed;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            z-index: 1000;
        }
        .dialog-overlay {
            display: block; /* Hiện overlay */
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            z-index: 999;
        }
        .close-button {
            cursor: pointer;
            float: right;
            color: red;
        }
    </style>
</head>
<body>

<div class="dialog-overlay"></div>
<div class="dialog">
    <span class="close-button" onclick="location.href='index.jsp'">X</span>
    <h3>Edit Conversation</h3>
    <form action="manageConversation" method="post">
        <input type="hidden" name="action" value="edit-conversation">
        <input type="hidden" name="conversationId" value="${param.conversationId}">
        <label for="conversationName">Conversation Name:</label>
        <input type="text" name="conversationName" required>
        <button type="submit">Save</button>
    </form>
</div>

</body>
</html>
