<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="CSS/bootstrap.min.css" rel="stylesheet">
    <title>ChatGPT Chatbot</title>
    <style>
        .chat-bubble-button {
            position: fixed;
            bottom: 20px;
            right: 20px;
            width: 60px;
            height: 60px;
            border-radius: 50%;
            background-color: #430c74;
            color: white;
            font-size: 30px;
            text-align: center;
            line-height: 60px;
            cursor: pointer;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .chat-container {
            visibility: hidden;
            position: fixed;
            bottom: 90px;
            right: 20px;
            width: 300px;
            height: 400px;
            border: 1px solid #ccc;
            background-color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            border-radius: 8px;
            z-index: 1000;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }

        .chat-header {
            padding: 15px;
            font-size: 20px;
            color: #430c74;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
            text-align: center;
            margin: 0;
        }

        .messages {
            flex: 1;
            padding: 10px;
            overflow-y: auto;
        }

        .input-container {
            display: flex;
            padding: 10px;
            border-top: 1px solid #ccc;
        }

        .input-text {
            flex: 1;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }

        button {
            padding: 8px 12px;
            margin-left: 5px;
            border: none;
            background-color: #430c74;
            color: white;
            border-radius: 8px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }

        .message {
            margin: 8px 0;
            padding: 8px 12px;
            border-radius: 5px;
        }

        .user {
            text-align: right;
            background-color: #e9ecef;
        }

        .bot {
            text-align: left;
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>

<div class="chat-bubble-button" onclick="toggleChat()">💬</div>

<div class="chat-container" id="chatContainer">
    <h2 class="chat-header">Walah Chatbot</h2>
    <div class="messages" id="chatMessages"></div>

    <form id="chatForm" onsubmit="sendMessage(event)">
        <div class="input-container">
            <input type="text" name="message" placeholder="Type your message here..." class="input-text" required />
            <button type="submit">Send</button>
        </div>
    </form>
</div>

<script>
    function toggleChat() {
        const chatContainer = document.getElementById("chatContainer");
        chatContainer.style.visibility = chatContainer.style.visibility === "hidden" ? "visible" : "hidden";
    }

    function sendMessage(event) {
        event.preventDefault(); // Prevent form from submitting normally

        const message = document.querySelector('input[name="message"]').value;

        // Add user's message to the chat window
        const chatMessages = document.getElementById("chatMessages");
        const userMessageDiv = document.createElement("div");
        userMessageDiv.classList.add("message", "user");
        userMessageDiv.textContent = message;
        chatMessages.appendChild(userMessageDiv);

        // Clear the input field
        document.querySelector('input[name="message"]').value = "";

        // Send AJAX request to the server
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "ChatBotServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        xhr.onload = function () {
            if (xhr.status === 200) {
                const botMessageDiv = document.createElement("div");
                botMessageDiv.classList.add("message", "bot");
                chatMessages.appendChild(botMessageDiv);

                // Typing effect for bot response
                const botResponse = xhr.responseText;
                let i = 0;

                function typeWriter() {
                    if (i < botResponse.length) {
                        botMessageDiv.textContent += botResponse.charAt(i);
                        i++;
                        setTimeout(typeWriter, 50); // Adjust typing speed here
                    } else {
                        // Scroll to the bottom of the chat container when typing is complete
                        chatMessages.scrollTop = chatMessages.scrollHeight;
                    }
                }

                typeWriter(); // Start typing animation
            }
        };

        xhr.send("message=" + encodeURIComponent(message));
    }
</script>

</body>
</html>
