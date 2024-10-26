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
                font-size: 0.9rem;
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

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- TABLE -->
        <div class="content">
            <h3 class="title">List Request Of ${sessionScope.user.lastName} ${sessionScope.user.firstName}</h3>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Course</th>
                        <th>Request Time</th>
                        <th>Status</th>
                        <th>Request Reason</th>
                        <th>View</th>
                        <th>Delete</th>
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
                                <td>${req.requestReason}</td>
                                <td>
                                    <a href="viewRequest">
                                        <i class="fas fa-eye" style="color: black;"></i>
                                    </a>
                                </td>
                                <th>
                                    <a href="deleteRequest" 
                                       onclick="return confirm('Do you want to delete this request?');">
                                        <i class="fas fa-trash" style="color: red;"></i>
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
    </body>
</html>
