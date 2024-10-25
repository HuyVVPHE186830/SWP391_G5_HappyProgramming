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
        <title>JSP Page</title>
        <style>
            .course-form {
                max-width: 600px;
                margin: 70px auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
                background-color: #f9f9f9;
            }

            .form-label {
                display: block;
                margin-bottom: 10px;
                font-weight: bold;
            }

            .form-input, .form-select {
                width: 100%;
                padding: 10px;
                margin-bottom: 20px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
            }

            .submit-button {
                background-color: #5e3fd3;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .submit-button:hover {
                background-color: #541371;
            }
        </style>
    </head>
    <body>
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>
        
        <!-- FORM -->
        <h1>APPLY COURSE</h1>
        <c:set var="user" value="${sessionScope.user}"/>
        <form action="action" class="course-form">
            <label for="name" class="form-label">Name: </label>
            <input type="text" id="name" name="name" value="${user.lastName} ${user.firstName}" class="form-input" readonly>

            <label for="id" class="form-label">Select Course:</label>
            <select id="id" class="form-select">
                <c:forEach items="${requestScope.otherCourse}" var="oC">
                    <option value="${oC.courseId}">${oC.courseName}</option>
                </c:forEach>
            </select>

            <button type="submit" class="submit-button">Submit</button>
        </form>
    </body>
</html>
