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
                height: 50vh;
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
        </style>
    </head>
    <body>
        <c:if test="${empty sessionScope.user}">
            <%
            response.sendRedirect("login.jsp");
            %>
        </c:if>

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
                                <label>Name</label>
                                <input type="text" name="name" value="${user.lastName} ${user.firstName}" disabled>
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
                            <c:if test="${not empty sessionScope.message}">
                                <div class="success-message">
                                    ${sessionScope.message}
                                </div>
                            </c:if>
                            <% session.removeAttribute("message"); %>
                            <button type="button" class="button-applyCourse" onclick="window.location.href = 'editRequestForMentor.jsp'">EDIT REQUEST</button>
                        </div>
                    </c:if>
                </div>

            </div>
        </div>
    </body>
</html>
