<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>ChatGPT Chatbot</title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            .chat-container {
                width: 300px;
                height: 400px;
                border: 1px solid #ccc;
                padding: 10px;
                overflow-y: scroll;
            }
            .input-container {
                display: flex;
            }
            input {
                flex: 1;
                padding: 5px;
            }
            button {
                padding: 5px 10px;
            }
            .message {
                margin: 10px 0;
            }
            .user {
                text-align: right;
            }
        </style>
    </head>
    <body>

        <h2>ChatGPT Chatbot</h2>
        <div class="chat-container" id="chatContainer">
            <div class="message user"><%= request.getAttribute("userMessage") != null ? request.getAttribute("userMessage") : "" %></div>
            <div class="message bot"><%= request.getAttribute("botResponse") != null ? request.getAttribute("botResponse") : "" %></div>
        </div>

        <form action="ChatBotServlet" method="post">
            <div class="input-container">
                <input type="text" name="message" placeholder="Type your message here..." required />
                <button type="submit">Send</button>
            </div>
        </form>

    </body>
</html>
