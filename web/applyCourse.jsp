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
                width: 550px;
                box-shadow: 0 0 10px #888;
                justify-content: space-between;
                overflow: hidden;
                height: 430px;
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
                <a href="listRequestForMentor?userId=${sessionScope.user.id}">
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
