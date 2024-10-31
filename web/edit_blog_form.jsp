<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Blog" %>
<%@ page import="model.Tag" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.stream.Collectors" %> <!-- Import Collectors for handling tags -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Edit Blog</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/form-style.css" rel="stylesheet">
    </head>
    <body>

        <!-- Header -->
        <jsp:include page="header.jsp"/>

        <div class="container mt-5">
            <h2>Edit Blog</h2>
            <form action="updateBlog" method="POST" enctype="multipart/form-data">

                <!-- Blog ID (hidden) -->
                <input type="hidden" name="blogId" value="<%= ((Blog) request.getAttribute("blog")).getBlogId() %>">

                <!-- Title -->
                <div class="form-group">
                    <label for="title" class="form-label">Title</label>
                    <input type="text" name="title" id="title" class="form-control" 
                           value="<%= ((Blog) request.getAttribute("blog")).getTitle() %>" required>
                </div>

                <!-- Content -->
                <div class="form-group">
                    <label for="content" class="form-label">Content</label>
                    <textarea name="content" id="content" class="form-control" rows="5" required><%= ((Blog) request.getAttribute("blog")).getContent() %></textarea>
                </div>

                <!-- Tags -->
                <div class="form-group">
                    <label for="tags" class="form-label">Tags</label>
                    <input type="text" name="tags" id="tags" class="form-control" placeholder="Enter tags separated by commas" 
                           value="<%= ((Blog) request.getAttribute("blog")).getTags().stream().map(Tag::getTagName).collect(Collectors.joining(", ")) %>">
                </div>

                <!-- Existing Images -->
                <div class="form-group">
                    <label class="form-label">Current Images</label>
                    <div class="row">
                        <c:forEach var="imageUrl" items="${blog.imageUrls}">
                            <div class="col-md-3 mb-2">
                                <img src="${imageUrl}" class="img-thumbnail" alt="Current Blog Image">
                                <div class="form-check">
                                    <input type="checkbox" name="deleteImages" value="${imageUrl}" class="form-check-input">
                                    <label class="form-check-label">Delete</label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <!-- New Images -->
                <div class="form-group">
                    <label for="images" class="form-label">Upload New Images</label>
                    <input type="file" name="images" id="images" class="form-control" multiple>
                </div>

                <!-- Submit Button -->
                <button type="submit" class="btn btn-primary mt-3">Update Blog</button>

                <!-- Cancel Button -->
                <a href="viewBlogDetail?id=<%= ((Blog) request.getAttribute("blog")).getBlogId() %>" class="btn btn-secondary mt-3">Cancel</a>
            </form>
        </div>

        <!-- Footer -->
        <jsp:include page="footer.jsp"/>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
