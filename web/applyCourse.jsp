<%-- 
    Document   : applyCourse
    Created on : Oct 18, 2024, 9:26:09 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <title>JSP Page</title>
        <style>
            .middle {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .applyCourse-form {
                background-color: #fff;
                border-radius: 25px;
                display: flex;
                width: 950px;
                box-shadow: 0 0 10px #888;
                justify-content: space-between;
                overflow: hidden;
                height: 530px;
                position: relative;
            }

            .applyCourse-form a {
                position: absolute;
                color: #452cbf;
                left: 10px;
                top: 10px;
                border-radius: 5px;
                text-decoration: none;
                font-size: 1.2rem;
                font-weight: bold;
                margin-bottom: 20px;
                transition: background-color 0.3s ease;
            }

            .applyCourse-form-main {
                margin: auto 10px;
                width: 100%;
            }

            .applyCourse-form-main h2 {
                font-size: 2rem;
                margin-bottom: 20px;
                text-align: center;
            }

            form {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }

            .input-text {
                margin: 0 auto;
                width: 100%;
                display: flex;
                justify-content: center;
            }

            input {
                padding: 10px;
                font-size: 0.8rem;
                width: 70%;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
                margin: 0 auto;
            }

            select.form-select {
                padding: 10px;
                font-size: 16px;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
                width: 70%;
                margin: 0 auto;
                display: block;
            }

            .button-applyCourse {
                background-color: #5d3fd3;
                color: #fff;
                padding: 10px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
                font-size: 1rem;
                width: 70%;
                margin: 0 auto;
                font-weight: bold;
                transition: all 0.3s ease;
            }

            .button-applyCourse:hover {
                background-color: #452cbf;
            }

            .success-message {
                color: green;
                font-weight: bold;
                text-align: center;
                font-size: 12px;
            }
            /* Notify */
            .notification {
                position: fixed;
                top: 20px;
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
        <c:if test="${empty user}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>
        <!-- Notification Container -->
        <div id="notification" class="notification hidden"></div>
        <script>
            const sessionMessage = '<%= session.getAttribute("message") != null ? session.getAttribute("message") : "" %>';
            if (sessionMessage) {
                showNotification(sessionMessage);
            <% session.removeAttribute("message"); %>
            }
            function showNotification(message) {
                const notification = document.getElementById('notification');
                notification.textContent = message;
                notification.classList.remove('hidden');

                // Make the notification visible
                setTimeout(() => {
                    notification.style.opacity = '1';
                    notification.style.transform = 'translateY(0)';
                }, 100); // Small delay for smooth animation

                // Automatically hide the notification after 3 seconds
                setTimeout(() => {
                    notification.style.opacity = '0';
                    notification.style.transform = 'scale(0.8) translateY(20px)';
                    setTimeout(() => {
                        notification.classList.add('hidden');
                    }, 500); // Allow animation to finish before hiding
                }, 3000);
            }
        </script>
        <div class="middle">
            <div class="applyCourse-form">
                <a href="viewMyCourses">
                    <i class="fas fa-arrow-left"></i> 
                </a>
                <div class="applyCourse-form-main">
                    <h2>Apply Course</h2>
                    <form action="applyCourse" method="post">
                        <input type="hidden" value="${user.username}" name="username">
                        <div class="input-text">
                            <input type="text" name="name" value="${user.lastName} ${user.firstName}" readonly>
                        </div>
                        <select name="courseId" class="form-select">
                            <c:forEach items="${sessionScope.otherCourse}" var="oC">
                                <option value="${oC.courseId}" style="font-size: 1rem">${oC.courseName}</option>
                            </c:forEach>
                        </select>
                        <div class="input-text" style="height: auto">
                            <textarea name="requestReason" placeholder="Request reason" style="width: 70%; height: 130px; padding: 10px; border: none; border-radius: 5px; background-color: #eeeded; font-size: 16px"></textarea>
                        </div>
                        <button type="submit" class="button-applyCourse">SUBMIT</button>
                        <c:if test="${not empty sessionScope.message}">
                            <div class="success-message">
                                ${sessionScope.message}
                            </div>
                        </c:if>
                        <% session.removeAttribute("message"); %>
                    </form>
                </div>

            </div>
        </div>

    </body>
</html>
