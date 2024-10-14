<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Blog"%>
<%@page import="model.Tag"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Blog Details</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/home.css" rel="stylesheet"> <!-- Include additional CSS for styling -->
        <style>
            .blog-detail {
                width: 90%; /* Increase the width of the blog detail container */
                max-width: 1400px; /* Max width to maintain good layout */
                margin: 0 auto; /* Center the blog detail section */
                background-color: #f8f9fa; /* Light background color */
                padding: 30px; /* Add more padding for a spacious feel */
                border-radius: 8px; /* Slightly rounded corners */
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15); /* Enhanced shadow */
                margin-bottom: 30px; /* Space below the blog detail */
            }

            .blog-title {
                margin-bottom: 20px;
                text-transform: capitalize;
                font-size: 2.5em; /* Larger title font */
                color: #333;
            }

            .author-info {
                font-style: italic;
                margin-bottom: 15px;
                color: #555;
            }

            .image-container {
                display: flex;
                flex-wrap: wrap;
                gap: 15px; /* More spacing between images */
                margin-bottom: 20px;
            }

            .blog-image {
                flex: 1 1 calc(33.333% - 15px); /* Three images per row with spacing */
                max-width: calc(33.333% - 15px);
                cursor: pointer; /* Indicate clickable */
                border-radius: 5px; /* Slightly rounded corners for images */
                overflow: hidden; /* Ensure no overflow */
            }

            .blog-image img {
                width: 100%; /* Make images responsive */
                height: auto; /* Maintain aspect ratio */
                transition: transform 0.2s; /* Add transition for hover effect */
            }

            .blog-image:hover img {
                transform: scale(1.05); /* Zoom effect on hover */
            }

            .tag-list {
                list-style-type: none;
                padding: 0;
            }

            .tag-list li {
                display: inline-block;
                margin-right: 10px;
                background-color: #007bff; /* Bootstrap primary color */
                color: white;
                padding: 5px 10px;
                border-radius: 3px;
            }

            .back-button {
                margin-top: 20px;
            }
        </style>
    </head>
    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <div class="container mt-5"> <!-- Keep container for responsiveness -->
            <div class="blog-detail">
                <%
                    Blog blog = (Blog) request.getAttribute("blog");
                    if (blog != null) {
                %>
                <h1 class="blog-title"><%= blog.getTitle() %></h1>
                <p class="author-info"><strong>By:</strong> <%= blog.getUserName() %></p>
                <p><%= blog.getContent() %></p>

                <h3>Images:</h3>
                <div class="image-container">
                    <%
                        // Loop through each image URL in the blog
                        for (String imageUrl : blog.getImageUrls()) {
                    %>
                    <div class="blog-image" data-bs-toggle="modal" data-bs-target="#imageModal" data-bs-img="<%= imageUrl %>">
                        <img src="<%= imageUrl %>" class="img-fluid" alt="Blog Image">
                    </div>
                    <% } %>
                </div>

                <h3>Tags:</h3>
                <ul class="tag-list">
                    <%
                        for (Tag tag : blog.getTags()) {
                    %>
                    <li><%= tag.getTagName() %></li>
                        <% } %>
                </ul>

                <!-- Back Button -->
                <a href="<%= request.getContextPath() %>/viewblogs" class="btn btn-secondary back-button">Back to Blog List</a>

                <%
                    } else {
                %>
                <p>Blog not found.</p>
                <%
                    }
                %>
            </div>
        </div>

        <!-- Image Modal -->
        <div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="imageModalLabel">Image Preview</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <img id="modal-image" src="" class="img-fluid" alt="Blog Image">
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
        <script>
            // Set the clicked image in the modal
            const imageModal = document.getElementById('imageModal');
            imageModal.addEventListener('show.bs.modal', event => {
                const button = event.relatedTarget; // Button that triggered the modal
                const imageUrl = button.getAttribute('data-bs-img'); // Extract info from data-* attributes
                const modalImage = document.getElementById('modal-image'); // Find the modal image element
                modalImage.src = imageUrl; // Update the modal's image source
            });
        </script>
    </body>
</html>
