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
                width: 500px;
                box-shadow: 0 0 10px #888;
                justify-content: space-between;
                overflow: hidden;
                height: 60vh;
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

            form {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }

            input {
                padding: 10px;
                font-size: 0.8rem;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
                width: 70%;
                margin: 0 auto;
            }

            select.form-select {
                padding: 10px;
                font-size: 0.8rem;
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
        </style>
    </head>
    <body>
        <c:if test="${empty user}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <div class="middle">
            <div class="applyCourse-form">
                <div class="applyCourse-form-left">
                    <h2>Apply Course</h2>
                    <form action="applyCourse" method="post">
                        <input type="hidden" value="${user.username}" name="username">
                        <input type="text" name="name" value="${user.lastName} ${user.firstName}" readonly>
                        <select id="id" name="courseId" class="form-select">
                            <c:forEach items="${requestScope.otherCourse}" var="oC">
                                <option value="${oC.courseId}">${oC.courseName}</option>
                            </c:forEach>
                        </select>
                        <input type="text" name="requestReason" placeholder="Request reason">
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
