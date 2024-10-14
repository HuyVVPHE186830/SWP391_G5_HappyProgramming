<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Blog"%>
<%@page import="model.Tag"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Blog Details</title>
    <link href="CSS/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <!-- HEADER -->
    <jsp:include page="header.jsp"/>

    <div class="container mt-5">
        <%
            Blog blog = (Blog) request.getAttribute("blog");
            if (blog != null) {
        %>
            <h1><%= blog.getTitle() %></h1>
            <p><strong>By:</strong> <%= blog.getUserName() %></p>
            <p><%= blog.getContent() %></p>

            <h3>Images:</h3>
            <div class="row">
                <%
                    for (String imageUrl : blog.getImageUrls()) {
                %>
                <div class="col-md-4">
                    <img src="<%= imageUrl %>" class="img-fluid" alt="Blog Image">
                </div>
                <% } %>
            </div>

            <h3>Tags:</h3>
            <ul>
                <%
                    for (Tag tag : blog.getTags()) {
                %>
                <li><%= tag.getTagName() %></li>
                <% } %>
            </ul>
        <%
            } else {
        %>
            <p>Blog not found.</p>
        <%
            }
        %>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
</body>
</html>
