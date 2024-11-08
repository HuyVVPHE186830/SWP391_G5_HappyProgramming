<%-- 
    Document   : viewRequestForMentor
    Created on : Oct 26, 2024, 1:16:17 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
                width: 800px;
                box-shadow: 0 0 10px #888;
                overflow: hidden;
                height: 590px;
                padding: 10px;
            }

            .applyCourse-form-left {
                margin: auto 10px;
                width: 100%;
            }

            .applyCourse-form-left h2 {
                font-size: 2rem;
                margin-bottom: 20px;
                text-align: center;
            }

            .applyCourse-form a {
                color: #452cbf;
                padding: 10px 20px 10px 0;
                border-radius: 5px;
                text-decoration: none;
                font-size: 1.2rem;
                font-weight: bold;
                margin-bottom: 20px;
                transition: background-color 0.3s ease;
            }

            .form {
                display: flex;
                flex-direction: column;
                gap: 5px;
            }
            .input {
                display: flex;
                align-items: center;
                justify-content: space-between;
                width: 100%;
                margin-bottom: 3%;
                padding-right: 25px;
            }

            label {
                display: flex;
                align-items: center;
                font-size: 1rem;
                font-weight: bold;
            }

            input, select.form-select {
                padding: 10px;
                font-size: 0.9rem;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
                width: 70%;
                margin-left: 10px;
                margin-right: 10px;
                height: 42px;
            }

            .button-applyCourse {
                background-color: #5d3fd3;
                color: #fff;
                padding: 10px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
                font-size: 1rem;
                width: 30%;
                font-weight: bold;
                transition: all 0.3s ease;
                margin: 0 auto;
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
        <c:if test="${empty sessionScope.user}">
            <%
            response.sendRedirect("login.jsp");
            %>
        </c:if>
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
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- CONTENT MIDDLE -->
        <div class="middle">
            <div class="applyCourse-form">
                <a href="listRequestForMentor?userId=${sessionScope.user.id}">
                    <i class="fas fa-arrow-left"></i> 
                </a>
                <div class="applyCourse-form-left">
                    <c:if test="${not empty sessionScope.req}">
                        <c:set var="req" value="${sessionScope.req}"/>
                        <h2>View Request Detail</h2>
                        <div class="form">
                            <div class="input">
                                <label>Full Name</label>
                                <input type="text" name="name" value="${user.lastName} ${user.firstName}" disabled>
                            </div>
                            <div class="input">
                                <label>Username</label>
                                <input type="text" name="name" value="${user.username}" disabled>
                            </div>
                            <div class="input">
                                <label>Course</label>
                                <c:forEach var="c" items="${sessionScope.courses}">
                                    <c:if test="${c.courseId == req.courseId}">
                                        <c:set var="courseName" value="${c.courseName}"/>
                                    </c:if>
                                </c:forEach>
                                <input type="text" name="course" value="${courseName}" disabled>
                            </div>
                            <div class="input">
                                <label>Request Reason</label>
                                <input type="text" name="requestReason" value="${req.requestReason}" disabled>
                            </div>
                            <div class="input">
                                <label>Request Time</label>
                                <input type="text" name="requestTime" value="<fmt:formatDate value="${req.requestTime}" pattern="dd-MM-yyyy" />" disabled>
                            </div>
                            <div class="input">
                                <label>Status</label>
                                <c:set var="statusName" value="Unknown"/>
                                <c:forEach var="s" items="${sessionScope.status}">
                                    <c:if test="${s.statusId == req.requestStatus}">
                                        <c:set var="statusName" value="${s.statusName}"/>
                                    </c:if>
                                </c:forEach>
                                <input type="text" name="requestStatus" value="${statusName}" style="text-transform: capitalize" disabled>
                            </div>
                            <c:if test="${req.requestStatus != 0}">
                                <button type="button" class="button-applyCourse" style="background-color: #ccc; color: white; width: 400px; cursor: not-allowed" disabled>This Request Can Not Change Anymore</button>
                            </c:if>
                            <c:if test="${req.requestStatus == 0}">
                                <button type="button" class="button-applyCourse" onclick="window.location.href = 'editRequestForMentor.jsp'">EDIT REQUEST</button>
                            </c:if>
                        </div>
                    </c:if>
                </div>

            </div>
        </div>
    </body>
</html>
