<%-- 
    Document   : verifyEmail
    Created on : Sep 27, 2024, 9:40:40 AM
    Author     : Huy Võ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Account</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            form {
                background-color: #fff;
                border-radius: 25px;
                display: flex;
                width: 40%;
                box-shadow: 0 0 10px #888;
                flex-direction: column;
                padding: 20px;
            }

            input {
                padding: 10px;
                font-size: 1rem;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
                width: 70%;
                margin: 0 auto;
            }

            .button-verify {
                width: 20%;
                background-color: #5d3fd3;
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                background-color: #5d3fd3;
                margin: 20px auto 0;
                transition: background-color 0.3s ease;
            }

            .button-verify:hover {
                background-color: #452cbf;
            }
            .error-message {
                color: red;
                font-weight: bold;
                text-align: center;
                font-size: 12px;
            }

            .success-message {
                color: green;
                font-weight: bold;
                text-align: center;
                font-size: 12px;
            }

            .notification {
                position: fixed;
                top: 50px;
                right: 20px;
                background-color: #4caf50;
                color: white;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                font-size: 16px;
                font-family: Arial, sans-serif;
                z-index: 9999;
                opacity: 0;
                transform: scale(0.8) translateY(20px);
                transition: opacity 0.3s ease, transform 0.5s ease;
            }
            .hidden {
                display: none;
            }
        </style>
    </head>
    <body>
        <%
            User user = (User) session.getAttribute("user");
            if (user == null) {
               response.sendRedirect("login.jsp");
               return;
            }
        %>
        <jsp:include page="header.jsp" />
        <div id="notification" class="notification hidden"></div>
        <script>
            const errorMessage = '<%= session.getAttribute("error") != null ? session.getAttribute("error") : "" %>';
            if (errorMessage) {
                showError(errorMessage);
            <% session.removeAttribute("error"); %>
            }
            function showError(message) {
                const notification = document.getElementById('notification');
                notification.textContent = message;
                notification.classList.remove('hidden');
                setTimeout(() => {
                    notification.style.opacity = '1';
                    notification.style.transform = 'translateY(0)';
                    notification.style.backgroundColor = '#dc133b';
                }, 100);
                setTimeout(() => {
                    notification.style.opacity = '0';
                    notification.style.transform = 'scale(0.8) translateY(20px)';
                    setTimeout(() => {
                        notification.classList.add('hidden');
                    }, 500);
                }, 3000);
            }
        </script>
        <form action="verifyEmail" method="post">
            <c:if test="${not empty sessionScope['error-message-verify']}">
                <p style="text-align: center; margin-bottom: 20px; font-weight: 600; color: red;">${sessionScope['error-message-verify']}</p>
            </c:if>
            <c:if test="${not empty sessionScope['message-verify']}">

                <p style="text-align: center; margin-bottom: 20px; font-weight: 600; color: green;">${sessionScope['message-verify']}</p>

            </c:if>
            <input type="text" placeholder="Verification Code" name="verificationCode" required>
            <button type="submit" class="button-verify" id="verifyButton">VERIFY</button>
        </form>

    </body>
</html>
