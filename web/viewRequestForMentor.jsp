<%-- 
    Document   : viewRequestForMentor
    Created on : Oct 26, 2024, 1:16:17 PM
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
                width: 700px;
                box-shadow: 0 0 10px #888;
                justify-content: space-between;
                overflow: hidden;
                height: 70vh;
                text-align: center;
                padding: 0 10px;
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
            .input {
                display: flex;
                align-items: center;
                justify-content: space-between;
                width: 100%;
                margin-bottom: 3%;
            }
            
            label {
                display: flex;
                align-items: center;
                font-size: 1rem;
                font-weight: bold;
            }

            input, select.form-select {
                padding: 10px;
                font-size: 0.8rem;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
                width: 70%;
                margin-left: 10px;
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
        <div class="middle">
            <div class="applyCourse-form">
                <div class="applyCourse-form-left">
                    <h2>View Request Detail</h2>
                    <form action="#">
                        <input type="hidden" value="${user.username}" name="username">
                        <div class="input">
                            <label>Name</label>
                            <input type="text" name="name" value="${user.lastName} ${user.firstName}" disabled>
                        </div>
                        <div class="input">
                            <label>Course</label>
                            <select id="id" name="courseId" class="form-select" disabled>
                                <c:forEach items="${requestScope.otherCourse}" var="oC">
                                    <option value="${oC.courseId}">${oC.courseName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="input">
                            <label>Request Reason</label>
                            <input type="text" name="requestReason" value="" disabled>
                        </div>
                        <div class="input">
                            <label>Request Time</label>
                            <input type="text" name="requestTime" value="" disabled>
                        </div>
                        <div class="input"> 
                            <label>Status</label>
                            <input type="text" name="requestStatus" value="<c:forEach var="s" items="${requestScope.status}">
                                       <c:if test="${s.statusId == req.requestStatus}">
                                           ${s.statusName}
                                       </c:if>
                                   </c:forEach>" disabled>
                        </div>
                        <button type="submit" class="button-applyCourse" onclick="window.location.href = 'editRequestForMentor.jsp'">EDIT REQUEST</button>
                    </form>
                </div>

            </div>
        </div>
    </body>
</html>
