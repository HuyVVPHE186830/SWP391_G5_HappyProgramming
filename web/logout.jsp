<%-- 
    Document   : logout
    Created on : Sep 21, 2024, 2:17:00 PM
    Author     : ThuanNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            session.removeAttribute("user");
            response.sendRedirect("home");
        %>
    </body>
</html>
