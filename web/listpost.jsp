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

        

        <div class="container mt-5">
            <div class="d-flex justify-content-between align-items-center">
                <h1 class="mb-4">Latest Blogs</h1>
                <!-- Add Blog Button -->
                <a href="submitBlog" class="btn btn-success">Write Something!</a>
            </div>

            <!-- Search Form -->
            <form action="searchBlogs" method="get" class="mb-4">
                <div class="input-group">
                    <input type="text" class="form-control" name="query" placeholder="Search by title, content, or tags" aria-label="Search Blogs">
                    <button class="btn btn-outline-secondary" type="submit">Search</button>
                </div>
            </form>

            <!-- Blog list placeholder -->
            <div class="row">
                <%
                    List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");
                    if (blogs != null && !blogs.isEmpty()) {
                        for (Blog blog : blogs) {
                %>
                <div class="col-md-4">
                    <div class="card mb-4 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title"><%= blog.getTitle() %></h5>
                            <p class="card-text"><%= blog.getContent().substring(0, Math.min(100, blog.getContent().length())) %>...</p>
                            <a href="viewBlogDetail?id=<%= blog.getBlogId() %>" class="btn" style="background-color: #452cbf; color: #f8f9fa">Read More</a>
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
        <jsp:include page="footer.jsp"/>

        <jsp:include page="chat.jsp"></jsp:include>


        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
