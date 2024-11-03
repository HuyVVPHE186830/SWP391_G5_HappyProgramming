<%-- 
    Document   : ListRequestForMentor
    Created on : Oct 23, 2024, 9:54:14 PM
    Author     : Thuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <title>JSP Page</title>
        <style>
            .content {
                text-align: center;
                margin-top: 80px;
                font-family: Arial, sans-serif;

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

        <!-- TABLE -->
        <div class="content">
            <h3 class="title">List Request Of ${sessionScope.user.lastName} ${sessionScope.user.firstName}</h3>
            <form action="#" method="post" class="search-bar">
                <input type="hidden" name="search" value="searchByName"/>
                <input type="text" class="input-submit" placeholder="Search a course" name="keyword" id="keyword" oninput="checkInput()">
                <input type="submit" class="button-submit" value="Search">
            </form>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Course</th>
                        <th>Request Time</th>
                        <th>Status</th>
                        <th>Request Reason</th>
                        <th>View</th>
                        <th>Cancel</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty requestScope.requests}">
                        <c:forEach var="req" items="${requestScope.requests}">
                            <tr>
                                <td>
                                    <c:forEach var="c" items="${requestScope.courses}">
                                        <c:if test="${c.courseId == req.courseId}">
                                            ${c.courseName}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <fmt:formatDate value="${req.requestTime}" pattern="dd-MM-yyyy" />
                                </td>
                                <td style="text-transform: capitalize"><c:forEach var="s" items="${requestScope.status}">
                                        <c:if test="${s.statusId == req.requestStatus}">
                                            ${s.statusName}
                                        </c:if>
                                    </c:forEach></td>
                                <td style="word-wrap: break-word; white-space: normal; max-width: 300px;">${req.requestReason}</td>
                                <td>
                                    <a href="editRequestForMentor?username=${req.username}&courseId=${req.courseId}">                                  
                                        <i class="fas fa-eye">
                                           </i>
                                        </a>
                                    </td>
                                    <th>
                                        <a href="deleteRequestForMentor?username=${req.username}&courseId=${req.courseId}" onclick="confirm('Are you sure to cancel this request!')"
                                       <c:if test="${req.requestStatus != 0}">
                                           style="color: gray; pointer-events: none;" 
                                       </c:if>>                                  
                                        <i class="fas fa-trash" 
                                           style="<c:if test='${req.requestStatus != 0}'>color: black;</c:if><c:if test='${req.requestStatus == 0}'>color: red;</c:if>">
                                           </i>
                                        </a>
                                        </a>
                                    </th>
                                </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty requestScope.requests}">
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
