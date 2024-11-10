<%-- 
    Document   : listRequestForMentee
    Created on : Nov 5, 2024, 2:33:47 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">

        <title>List Request For Mentee</title>
        <style>
            .content {
                text-align: center;
                margin-top: 80px;

            }

            .search-bar {
                display: flex;
                align-items: center;
                margin: 0 auto;
                width: 100%;
                max-width: 40%;
                border-radius: 10px;
                overflow: hidden;
                margin-bottom: 10px;
            }

            .input-submit {
                border: none;
                padding: 10px;
                flex: 1;
                border-radius: 10px 0 0 10px;
                font-size: 16px;
                outline: none;
                background-color: #f2f2f2;
                opacity: 2;
            }

            .button-submit {
                background-color: #5e3fd3;
                color: white;
                border: none;
                padding: 10px 20px;
                cursor: pointer;
                border-radius: 0 10px 10px 0;
                font-size: 16px;
                transition: all 1s ease;
                font-weight: bold;
            }

            .button-submit:hover {
                background-color: #541371;
            }

            .title {
                font-size: 2rem;
                text-align: center;
                margin-bottom: 20px;
                font-weight: 500;
                text-transform: uppercase;
                padding-bottom: 10px;
            }

            table {
                margin: 0 auto;
                width: 80%;
                max-width: 1200px;
                background-color: #ffffff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-collapse: collapse;
                border-radius: 8px;
                overflow: hidden;
            }

            .table th, .table td {
                padding: 15px;
                text-align: center;
                font-size: 1rem;
            }

            .table thead th {
                background-color: #5d3fd3;
                color: #ffffff;
                font-weight: bold;
                text-transform: uppercase;
            }

            .table tbody tr:nth-child(even) {
                background-color: #f8f8f8;
            }

            .table tbody tr:nth-child(odd) {
                background-color: #ffffff;
            }

            .table tbody td {
                border-bottom: 1px solid #dddddd;
            }

            .table .text-center {
                text-align: center;
                font-size: 1rem;
                color: #888888;
            }

            a i.fa-eye {
                color: #5d3fd3;
                font-size: 1.2rem;
                transition: color 0.3s ease;
            }

            a i.fa-eye:hover {
                color: #452cbf;
            }
            .link {
                color: black;
                text-align: start;
            }

            .link:hover {
                text-decoration: none;
            }
            /* Notify */
            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: green;
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


        <!-- TABLE -->
        <div class="content">
            <h6 style="text-align: start">
                <a href="home" class="link" style="margin-left: 30px">Home</a> <span>></span> <a href="viewMyCourses" class="link">My Courses</a> <span>></span> List Request Studying
            </h6>
            <h3 class="title">List Request Studying Of ${sessionScope.user.lastName} ${sessionScope.user.firstName}</h3>
            <form action="listRequestForMentee" method="post" class="search-bar">
                <input type="hidden" name="search" value="searchByName"/>
                <input type="text" class="input-submit" placeholder="Search" name="keyword" id="keyword" oninput="checkInput()">
                <input type="submit" class="button-submit" value="Search">
            </form>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Course</th>
                        <th>Mentor</th>
                        <th>Status</th>
                        <th>Cancel</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty requestScope.participates}">
                        <c:forEach var="p" items="${requestScope.participates}">
                            <tr>
                                <td>
                                    <c:forEach var="c" items="${requestScope.courses}">
                                        <c:if test="${c.courseId == p.courseId}">
                                            ${c.courseName}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    ${p.mentorUsername}
                                </td>
                                <td style="text-transform: capitalize"><c:forEach var="s" items="${requestScope.status}">
                                        <c:if test="${s.statusId == p.statusId}">
                                            ${s.statusName}
                                        </c:if>
                                    </c:forEach></td>
                                <th>
                                    <a href="deleteRequestForMentee?username=${p.username}&courseId=${p.courseId}"
                                       onclick="return confirm('Are you sure to cancel this request!') && ${req.requestStatus == 0 ? 'true' : 'false'}"
                                       <c:if test="${req.requestStatus != 0}">
                                           style="color: gray; pointer-events: none;"
                                       </c:if>>
                                        <i class="fas fa-trash"
                                           style="<c:if test='${p.statusId != 0}'>color: black;</c:if><c:if test='${p.statusId == 0}'>color: red;</c:if>">
                                           </i>
                                        </a>
                                    </th>
                                </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty requestScope.participates}">
                        <tr>
                            <td colspan="5" class="text-center">No request found.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <!-- CHAT -->
        <jsp:include page="chat.jsp"/>
    </body>
</html>
