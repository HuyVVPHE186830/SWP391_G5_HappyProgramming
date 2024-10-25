<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
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

            .comment, .reply {
                padding: 15px;
                background-color: #f8f9fa;
                margin-bottom: 15px;
                border-radius: 8px;
                border-left: 5px solid #007bff;
                position: relative;
            }

            .comment-body, .reply-body {
                flex-grow: 1;
                margin-left: 5px;
            }

            .comment-actions button {
                font-size: 14px;
                padding: 0;
            }

            img.rounded-circle {
                object-fit: cover;
            }

            .comment-form, .comment-section {
                padding-left: 50px;
                padding-right: 50px;
            }

            .comment-section {
                margin-top: 10px;
                position: relative;
            }

            .btn_submit {
                margin-top: -10px;
            }

            .comment-actions .dropdown {
                position: absolute;
                top: 10px;
                right: 10px;
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
                <p class="author-info"><strong>By:</strong> <%= blog.getCreatedBy() %></p>
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

        <!-- Comment Form -->
        <div class="comment-form">
            <h4>Leave a Comment:</h4>
            <form id="commentForm" action="addBlogComment" method="POST">
                <input type="hidden" name="blogId" value="<%= blog.getBlogId() %>">
                <div class="mb-3">
                    <textarea id="commentContent" name="commentContent" class="form-control" rows="3" placeholder="Write your comment"></textarea>
                </div>
                <button type="submit" class="btn btn-primary btn_submit">Submit</button>
            </form>
        </div>

        <div class="comment-section" id="commentSection">
            <%
                List<BlogComment> comments = (List<BlogComment>) request.getAttribute("comments");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                if (comments != null && !comments.isEmpty()) {
                    for (BlogComment comment : comments) {
                        User commenter = comment.getUser(); // Assuming each comment has a User object linked
            %>
            <div class="comment d-flex align-items-start">
                <!-- Displaying User's Profile Picture -->
                <img src="data:image/jpeg;base64, <%= commenter.getAvatarPath() %>" alt="Avatar" class="avatar-image-mini">
                <div class="comment-body ms-3">
                    <!-- Displaying Commenter's Name -->
                    <p><strong><%= commenter.getLastName() + " " + commenter.getFirstName() %></strong></p>
                    <p><%= comment.getCommentContent() %></p>
                    <p><small>Posted on: <%= sdf.format(comment.getCommentedAt()) %></small></p>

                    <div class="edit-comment-form" id="edit-form-<%= comment.getCommentId() %>" style="display: none;">
                        <form id="editCommentForm" method="POST" action="editBlogComment">
                            <input type="hidden" name="blogId" value="<%= blog.getBlogId() %>">
                            <input type="hidden" name="commentId" value="<%= comment.getCommentId() %>">
                            <div class="mb-3">
                                <textarea name="commentContent" class="form-control" rows="2"><%= comment.getCommentContent() %></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary btn_submit">Save</button>
                        </form>
                    </div>

                    <!-- Reply Buttons -->
                    <div class="comment-actions">
                        <button type="button" class="btn btn-link reply-btn" data-comment-id="<%= comment.getCommentId() %>">Reply</button>

                        <div class="dropdown">
                            <button class="btn btn-link dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-ellipsis-h"></i> <!-- Font Awesome icon for three dots -->
                            </button>
                            <% User u = (User)session.getAttribute("user");
                               if (commenter.getId() == u.getId()) { %>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                                <li><a class="dropdown-item edit-comment" href="#<%= comment.getCommentId() %>" data-comment-id="<%= comment.getCommentId() %>">Edit</a></li>
                                <li><a class="dropdown-item delete-comment" href="deleteBlogComment?id=<%= comment.getCommentId() %>" onclick="return confirm('Are you sure you want to delete this comment?')">Delete</a></li>
                            </ul>
                            <% } else { %>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                                <li><a class="dropdown-item report-comment" href="#<%= comment.getCommentId() %>" data-comment-id="<%= comment.getCommentId() %>">Report</a></li>
                            </ul>
                            <% } %>
                        </div>

                    </div>

                    <!-- Display Replies -->
                    <div class="replies" id="replies-<%= comment.getCommentId() %>">
                        <%
                            for (BlogComment reply : comment.getReplies()) {
                                User replier = reply.getUser(); // Assuming reply also has User object
                        %>
                        <div class="reply d-flex align-items-start">
                            <!-- Displaying Replier's Profile Picture -->
                            <img src="data:image/jpeg;base64, <%= replier.getAvatarPath() %>" alt="Avatar" class="avatar-image-mini">
                            <div class="reply-body ms-3">
                                <!-- Displaying Replier's Name -->
                                <p><strong><%= replier.getLastName() + " " + replier.getFirstName() %></strong></p>
                                <p><%= reply.getCommentContent() %></p>
                                <p><small>Posted on: <%= sdf.format(reply.getCommentedAt()) %></small></p>
                                <div class="comment-actions">

                                    <div class="dropdown">
                                        <button class="btn btn-link dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="fas fa-ellipsis-h"></i> <!-- Font Awesome icon for three dots -->
                                        </button>
                                        <% if (replier.getId() == u.getId()) { %>
                                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                                            <li><a class="dropdown-item edit-comment" href="#<%= reply.getCommentId() %>" data-comment-id="<%= reply.getCommentId() %>">Edit</a></li>
                                            <li><a class="dropdown-item delete-comment" href="deleteBlogComment?id=<%= reply.getCommentId() %>" onclick="return confirm('Are you sure you want to delete this comment?')">Delete</a></li>
                                        </ul>
                                        <% } else { %>
                                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                                            <li><a class="dropdown-item report-comment" href="#<%= reply.getCommentId() %>" data-comment-id="<%= reply.getCommentId() %>">Report</a></li>
                                        </ul>
                                        <% } %>
                                    </div>
                                </div>

                                <div class="edit-comment-form" id="edit-form-<%= reply.getCommentId() %>" style="display: none;">
                                    <form id="editCommentForm" method="POST" action="editBlogComment">
                                        <input type="hidden" name="blogId" value="<%= blog.getBlogId() %>">
                                        <input type="hidden" name="commentId" value="<%= reply.getCommentId() %>">
                                        <div class="mb-3">
                                            <textarea name="commentContent" class="form-control" rows="2"><%= reply.getCommentContent() %></textarea>
                                        </div>
                                        <button type="submit" class="btn btn-primary btn_submit">Save</button>
                                    </form>
                                </div>

                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>

                    <!-- Reply form -->
                    <div class="reply-form mt-2" id="reply-form-<%= comment.getCommentId() %>" style="display:none;">
                        <form id="replyForm" action="addBlogComment" method="POST">
                            <input type="hidden" name="parentId" value="<%= comment.getCommentId() %>">
                            <input type="hidden" name="blogId" value="<%= blog.getBlogId() %>">
                            <div class="mb-3">
                                <textarea name="commentContent" class="form-control" rows="2" placeholder="Reply to this comment"></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary btn_submit">Reply</button>
                        </form>
                    </div>
                </div>
            </div>
            <hr>
            <%
                    }
                } else {
            %>
            <p>No comments yet. Be the first to comment!</p>
            <%
                }
            %>
        </div>

        <!-- JavaScript to handle reply form display -->
        <script>
            document.querySelectorAll('.reply-btn').forEach(button => {
                button.addEventListener('click', function () {
                    const commentId = this.getAttribute('data-comment-id');
                    const replyForm = document.getElementById('reply-form-' + commentId);

                    // Toggle reply form visibility
                    if (replyForm.style.display === 'none') {
                        replyForm.style.display = 'block';
                    } else {
                        replyForm.style.display = 'none';
                    }
                });
            });

            // Toggle edit comment form
            document.querySelectorAll('.edit-comment').forEach(button => {
                button.addEventListener('click', function () {
                    const commentId = this.getAttribute('data-comment-id');
                    const editForm = document.getElementById('edit-form-' + commentId);
                    const commentContent = document.querySelector('.comment-body[data-comment-id="' + commentId + '"]');

                    // Toggle edit form visibility
                    if (editForm.style.display === 'none') {
                        editForm.style.display = 'block';
                    } else {
                        editForm.style.display = 'none';
                    }
                });
            });

            // AJAX function to edit a comment
            function editComment(commentId) {
                const form = document.getElementById('edit-form-' + commentId);
                const formData = new FormData(form);

                fetch('editBlogComment', {
                    method: 'POST',
                    body: formData
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                // Update the comment content on the page
                                const commentContent = document.querySelector('.comment-body[data-comment-id="' + commentId + '"]');
                                commentContent.querySelector('p').innerText = data.updatedContent;

                                // Hide the edit form
                                form.style.display = 'none';
                            } else {
                                alert('Failed to edit the comment.');
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });

                return false; // Prevent form submission
            }

            document.getElementById('commentForm').addEventListener('submit', function (e) {
                e.preventDefault(); // Prevent the default form submission

                const commentContent = document.getElementById('commentContent').value;
                const blogId = document.querySelector('input[name="blogId"]').value;

                fetch('addBlogComment', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: new URLSearchParams({
                        blogId: blogId,
                        commentContent: commentContent
                    })
                })
                        .then(response => response.text())
                        .then(data => {
                            // Clear the textarea after submission
                            document.getElementById('commentContent').value = '';

                            // Refresh the comments section with new comments
                            fetchComments(blogId);
                        })
                        .catch(error => console.error('Error:', error));
            });

            // Function to fetch and update comments without reloading the page
            function fetchComments(blogId) {
                fetch('viewBlogDetail?id=' + blogId)
                        .then(response => response.text())
                        .then(html => {
                            const parser = new DOMParser();
                            const doc = parser.parseFromString(html, 'text/html');
                            const newCommentsSection = doc.querySelector('#commentSection');
                            document.getElementById('commentSection').innerHTML = newCommentsSection.innerHTML;
                        })
                        .catch(error => console.error('Error fetching comments:', error));
            }

            document.querySelectorAll('form[id^="replyForm"]').forEach(form => {
                form.addEventListener('submit', function (e) {
                    e.preventDefault(); // Prevent the default form submission

                    const commentContent = form.querySelector('textarea[name="commentContent"]').value;
                    const blogId = form.querySelector('input[name="blogId"]').value;
                    const parentId = form.querySelector('input[name="parentId"]').value;

                    fetch('addBlogComment', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: new URLSearchParams({
                            blogId: blogId,
                            commentContent: commentContent,
                            parentId: parentId // Include the parent ID for replies
                        })
                    })
                            .then(response => response.text())
                            .then(data => {
                                // Clear the reply textarea after submission
                                form.querySelector('textarea[name="commentContent"]').value = '';

                                // Refresh the comments section with new comments
                                fetchReplies(blogId, parentId);
                            })
                            .catch(error => console.error('Error:', error));
                });
            });

            // Function to fetch and update comments without reloading the page
            function fetchReplies(blogId, parentId) {
                fetch('viewBlogDetail?id=' + blogId)
                        .then(response => response.text())
                        .then(html => {
                            const parser = new DOMParser();
                            const doc = parser.parseFromString(html, 'text/html');
                            const newCommentsSection = doc.querySelector('#replies-' + parentId);
                            document.getElementById('replies-' + parentId).innerHTML = newCommentsSection.innerHTML;
                        })
                        .catch(error => console.error('Error fetching comments:', error));
            }
        </script>

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

        <!-- FOOTER -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>
