<%-- 
    Document   : listpost
    Created on : Oct 8, 2024, 2:34:32 PM
    Author     : Sapphire
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <%-- Sample static blogs. Replace this section with dynamic content later. --%>
                <%
                    // This is where your backend logic will go later, to fetch the latest blogs
                    // For now, we simulate it with static content
                    String[] blogTitles = {
                        "How to Start with Java",
                        "Understanding JSP and Servlets",
                        "Introduction to Spring Boot",
                        "Guide to Web Development with Bootstrap",
                        "Top 5 Database Tips for Developers"
                    };
                    String[] blogSummaries = {
                        "Learn the basics of Java programming and start your coding journey.",
                        "A deep dive into JSP and Servlet architecture for dynamic web development.",
                        "Discover the powerful Spring Boot framework for building Java applications.",
                        "Explore Bootstrap to create responsive and mobile-first websites.",
                        "Optimize your database for better performance with these tips."
                    };
                    for (int i = 0; i < blogTitles.length; i++) {
                %>
                <div class="col-md-4">
                    <div class="card mb-4 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title"><%= blogTitles[i] %></h5>
                            <p class="card-text"><%= blogSummaries[i] %></p>
                            <a href="#" class="btn btn-primary">Read More</a>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
