<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Statistics</title>
</head>
<body>
    <h1>System Statistics</h1>

    <h2>User Roles Breakdown</h2>
    <table border="1">
        <tr>
            <th>Role Name</th>
            <th>Number of Users</th>
        </tr>
        <c:forEach var="entry" items="${userRolesStats}">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>

    <h2>Courses and Participants</h2>
    <table border="1">
        <tr>
            <th>Course Name</th>
            <th>Number of Participants</th>
        </tr>
        <c:forEach var="entry" items="${courseStats}">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>

    <h2>Blog Posts by User</h2>
    <table border="1">
        <tr>
            <th>Username</th>
            <th>Number of Blog Posts</th>
        </tr>
        <c:forEach var="entry" items="${userBlogStats}">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>

    <h2>Messages in Conversations</h2>
    <table border="1">
        <tr>
            <th>Conversation Name</th>
            <th>Number of Messages</th>
        </tr>
        <c:forEach var="entry" items="${messageStats}">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
