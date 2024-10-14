<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Blog"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/home.css" rel="stylesheet">
    </head>
    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">MyForum</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Blogs</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Contact</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-5">
            <div class="d-flex justify-content-between align-items-center">
                <h1 class="mb-4">Latest Blogs</h1>
                <!-- Add Blog Button -->
                <a href="addblog.jsp" class="btn btn-success">Add Blog</a>
            </div>

            <!-- Blog list placeholder -->
            <div class="row">
                <%
                    List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");
                    if (blogs != null) {
                        for (Blog blog : blogs) {
                %>
                <div class="col-md-4">
                    <div class="card mb-4 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title"><%= blog.getTitle() %></h5>
                            <p class="card-text"><%= blog.getContent().substring(0, Math.min(100, blog.getContent().length())) %>...</p>
                            <a href="viewblogdetails?blogId=<%= blog.getBlogId() %>" class="btn btn-primary">Read More</a>
                        </div>
                    </div>
                </div>
                <% 
                        } 
                    } else {
                %>
                <p>No blogs available.</p>
                <% } %>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
