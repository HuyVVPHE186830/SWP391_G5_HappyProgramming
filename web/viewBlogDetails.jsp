<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                border-left: 5px solid #452cbf;
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

            .comment-actions .dropdown-item {
                color: #452cbf;
            }

            .comment-actions .btn, .comment-actions .btn:focus,
            .comment-actions .btn:hover, .comment-actions .btn:active {
                background-color: transparent !important;
                color: #452cbf !important;
                text-decoration: none;
                box-shadow: none !important;
                transition: color 0.3s ease, transform 0.2s ease;
            }

            .comment-actions .btn:hover {
                color: #301ca0 !important;
                transform: scale(1.05);
            }

            .comment-actions .dropdown-item,
            .comment-actions .dropdown-item:focus,
            .comment-actions .dropdown-item:hover,
            .comment-actions .dropdown-item:active {
                color: #452cbf !important;
                background-color: transparent !important;
                text-decoration: none;
                transition: color 0.3s ease, transform 0.2s ease;
            }

            .comment-actions .dropdown-item:hover {
                color: #301ca0 !important;
                transform: scale(1.05);
            }

            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: red;
                color: white;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                font-size: 16px;
                font-family: Arial, sans-serif;
                z-index: 1000;
                opacity: 0;
                transform: scale(0.8) translateY(20px);
                transition: opacity 0.3s ease, transform 0.5s ease;
            }

            .notification.success {
                background-color: green;
            }

            .notification.error {
                background-color: red;
            }

            .hidden {
                display: none;
            }

            #dropdownMenuButton::after {
                display: none !important;
            }
        </style>
    </head>
    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>
        <div id="notification" class="notification hidden"></div>
        <script>
            const successMessage = '<c:out value="${succMsg}" />';
            const errorMessage = '<c:out value="${failedMsg}" />';

            if (successMessage) {
                showNotification(successMessage, 'success');
            <% session.removeAttribute("succMsg"); %>
            } else if (errorMessage) {
                showNotification(errorMessage, 'error');
            <% session.removeAttribute("failedMsg"); %>
            }

            function showNotification(message, type) {
                const notification = document.getElementById('notification');
                notification.textContent = message;
                notification.classList.remove('hidden');
                notification.classList.add(type === 'success' ? 'success' : 'error');

                setTimeout(() => {
                    notification.style.opacity = '1';
                    notification.style.transform = 'translateY(0)';
                }, 100);

                setTimeout(() => {
                    notification.style.opacity = '0';
                    notification.style.transform = 'scale(0.8) translateY(20px)';
                    setTimeout(() => {
                        notification.classList.add('hidden');
                        notification.classList.remove(type); // Clean up class for next use
                    }, 500);
                }, 3000);
            }
        </script>

        <div class="container mt-5"> <!-- Keep container for responsiveness -->
            <div class="blog-detail">
                <%
                    Blog blog = (Blog) request.getAttribute("blog");
                    if (blog != null) {
                %>
                <h1 class="blog-title"><%= blog.getTitle() %></h1>
                <p class="author-info"><strong>By:</strong> <%= blog.getCreatedBy() %></p>
                <p><%= blog.getContent() %></p>

                <!-- Edit and Delete Buttons for Author Only -->
                <div class="mb-3">
                    <c:if test="${sessionScope.user.getUsername() == blog.getCreatedBy()}">
                        <a href="editBlog?id=<%= blog.getBlogId() %>" class="btn btn-primary">
                            <i class="fas fa-edit"></i>
                        </a>
                        <a href="deleteBlog?id=<%= blog.getBlogId() %>" class="btn btn-danger" 
                           onclick="return confirm('Are you sure you want to delete this blog?');">
                            <i class="fas fa-trash"></i>
                        </a>
                    </c:if>
                </div>

                <h3>Images:</h3>
                <div class="image-container">
                    <%
                        for (String imageUrl : blog.getImageUrls()) {
                    %>
                    <div class="blog-image" data-bs-toggle="modal" data-bs-target="#imageModal" data-bs-img="<%= imageUrl %>">
                        <img src="<%= imageUrl %>" class="img-fluid" alt="Blog Image">
                    </div>
                    <% } %>
                </div>

                <h3>Tags:</h3>
                <ul class="tag-list">
                    <% for (Tag tag : blog.getTags()) { %>
                    <li>
                        <a href="searchBlogs?query=<%= tag.getTagName() %>" class="text-white"><%= tag.getTagName() %></a>
                    </li>
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
        <c:if test="${not empty sessionScope.user}">
            <div class="comment-form">
                <h4>Leave a Comment:</h4>
                <form id="commentForm" action="addBlogComment" method="POST">
                    <input type="hidden" name="blogId" value="<%= blog.getBlogId() %>">
                    <div class="mb-3">
                        <textarea id="commentContent" name="commentContent" class="form-control" rows="3" placeholder="Write your comment"></textarea>
                    </div>
                    <button type="submit" class="btn btn_submit" style="background-color: #452cbf; color: #f8f9fa">Submit</button>
                </form>
            </div>
        </c:if>
        <div class="comment-section" id="commentSection">
            <%
                List<BlogComment> comments = (List<BlogComment>) request.getAttribute("comments");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
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
                            <button type="submit" class="btn btn_submit" style="background-color: #452cbf; color: #f8f9fa">Save</button>
                        </form>
                    </div>

                    <!-- Reply Buttons -->
                    <div class="comment-actions">
                        <button type="button" class="btn btn-link reply-btn" style="color: #452cbf" data-comment-id="<%= comment.getCommentId() %>">Reply</button>

                        <div class="dropdown">
                            <button class="btn btn-link dropdown-toggle" style="color: #452cbf" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-ellipsis-h"></i> 
                            </button>
                            <% User u = (User) session.getAttribute("user");
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

                    <!-- Reply form -->
                    <div class="reply-form mt-2" id="reply-form-<%= comment.getCommentId() %>" style="display:none; margin-bottom: 5px">
                        <form id="replyForm" action="addBlogComment" method="POST">
                            <input type="hidden" name="parentId" value="<%= comment.getCommentId() %>">
                            <input type="hidden" name="blogId" value="<%= blog.getBlogId() %>">
                            <div class="mb-3">
                                <textarea name="commentContent" class="form-control" rows="2" placeholder="Reply to this comment"></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary btn_submit" style="background-color: #452cbf; color: #f8f9fa">Reply</button>
                        </form>
                    </div>

                    <!-- Display Replies -->
                    <div class="replies" id="replies-<%= comment.getCommentId() %>">
                        <%
                            for (BlogComment reply : comment.getReplies()) {
                                User replier = reply.getUser(); 
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
                                        <button class="btn btn-link dropdown-toggle" style="color: #452cbf" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="fas fa-ellipsis-h"></i> 
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
                                        <button type="submit" class="btn btn-primary btn_submit" style="background-color: #452cbf; color: #f8f9fa">Save</button>
                                    </form>
                                </div>

                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>

                    <!-- Report Modal -->
                    <div class="modal fade" id="reportModal" tabindex="-1" aria-labelledby="reportModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form id="reportForm" action="reportComment" method="POST">
                                    <input type="hidden" name="blogId" value="<%= blog.getBlogId() %>">
                                    <input type="hidden" id="commentId" name="commentId">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="reportModalLabel">Report Comment</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="reportType" class="form-label">Reason for Reporting</label>
                                            <select id="reportType" name="reportTypeId" class="form-select" onchange="displayReportDescription()">
                                                <%
                                                    List<ReportType> reportTypes = (List<ReportType>) request.getAttribute("reportTypes");
                                                    for (ReportType reportType : reportTypes) {
                                                %>
                                                <option value="<%= reportType.getReportTypeId() %>" data-description="<%= reportType.getReportDescription() %>">
                                                    <%= reportType.getReportName() %>
                                                </option>
                                                <% } %>
                                            </select>
                                        </div>
                                        <div id="reportDescription" class="mb-3 text-muted"></div>
                                        <div class="mb-3">
                                            <label for="reportContent" class="form-label">Additional Details (Optional)</label>
                                            <textarea id="reportContent" name="reportContent" class="form-control" rows="3"></textarea>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn" style="background-color: #452cbf; color: #f8f9fa">Report</button>
                                    </div>
                                </form>
                            </div>
                        </div>
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

        <script>
            // Toggle reply form
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

            // Edit comment submit using fetch
            document.querySelectorAll('form[id="editCommentForm"]').forEach(form => {
                form.addEventListener('submit', function (e) {
                    e.preventDefault();
                    const formData = new FormData(form);

                    fetch('editBlogComment', {
                        method: 'POST',
                        body: new URLSearchParams(formData)
                    })
                            .then(response => response.text())
                            .then(() => {
                                // Hide the edit form and refresh comments
                                const blogId = form.querySelector('input[name="blogId"]').value;
                                fetchComments(blogId);
                            })
                            .catch(error => console.error('Error:', error));
                });
            });

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

                            attachReplyButtonListeners();

                            attachEditButtonListeners();
                        })
                        .catch(error => console.error('Error fetching comments:', error));
            }

            function attachReplyButtonListeners() {
                document.querySelectorAll('.reply-btn').forEach(button => {
                    button.addEventListener('click', function () {
                        const commentId = this.getAttribute('data-comment-id');
                        const replyForm = document.getElementById('reply-form-' + commentId);

                        // Toggle reply form visibility
                        replyForm.style.display = replyForm.style.display === 'none' ? 'block' : 'none';

                        // Attach submit listener for the reply form if not already attached
                        const replyFormElement = replyForm.querySelector('form');
                        if (replyFormElement && !replyFormElement.dataset.listenerAttached) {
                            replyFormElement.dataset.listenerAttached = 'true'; // Mark this form as having a listener
                            replyFormElement.addEventListener('submit', function (e) {
                                e.preventDefault(); // Prevent the default form submission

                                const commentContent = replyFormElement.querySelector('textarea[name="commentContent"]').value;
                                const blogId = replyFormElement.querySelector('input[name="blogId"]').value;
                                const parentId = replyFormElement.querySelector('input[name="parentId"]').value;

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
                                            replyFormElement.querySelector('textarea[name="commentContent"]').value = '';

                                            // Refresh the replies for this comment
                                            fetchReplies(blogId, parentId);
                                            replyForm.style.display = 'none';
                                        })
                                        .catch(error => console.error('Error:', error));
                            });
                        }
                    });
                });
            }

            function attachEditButtonListeners() {
                document.querySelectorAll('.edit-comment').forEach(button => {
                    button.addEventListener('click', function () {
                        const commentId = this.getAttribute('data-comment-id');
                        const editForm = document.getElementById('edit-form-' + commentId);
                        const commentContent = document.querySelector('.comment-body[data-comment-id="' + commentId + '"]');

                        // Toggle edit form visibility
                        editForm.style.display = editForm.style.display === 'none' ? 'block' : 'none';

                        document.querySelectorAll('form[id="editCommentForm"]').forEach(form => {
                            form.addEventListener('submit', function (e) {
                                e.preventDefault();
                                const formData = new FormData(form);

                                fetch('editBlogComment', {
                                    method: 'POST',
                                    body: new URLSearchParams(formData)
                                })
                                        .then(response => response.text())
                                        .then(() => {
                                            // Hide the edit form and refresh comments
                                            const blogId = form.querySelector('input[name="blogId"]').value;
                                            fetchComments(blogId);
                                        })
                                        .catch(error => console.error('Error:', error));
                            });
                        });
                    });
                });
            }

            document.querySelectorAll('form[id^="replyForm"]').forEach(form => {
                form.addEventListener('submit', function (e) {
                    e.preventDefault(); // Prevent the default form submission

                    const commentContent = form.querySelector('textarea[name="commentContent"]').value;
                    const blogId = form.querySelector('input[name="blogId"]').value;
                    const parentId = form.querySelector('input[name="parentId"]').value;
                    const replyForm = document.getElementById('reply-form-' + parentId);

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
                                replyForm.style.display = 'none';
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

                            attachEditButtonListeners();
                        })
                        .catch(error => console.error('Error fetching comments:', error));
            }

            function displayReportDescription() {
                const reportTypeSelect = document.getElementById("reportType");
                const selectedOption = reportTypeSelect.options[reportTypeSelect.selectedIndex];
                const description = selectedOption.getAttribute("data-description");
                document.getElementById("reportDescription").textContent = description;
            }

            window.onload = displayReportDescription;

            document.querySelectorAll('.report-comment').forEach(item => {
                item.addEventListener('click', event => {
                    event.preventDefault();
                    const commentId = event.target.getAttribute('data-comment-id');
                    document.getElementById('commentId').value = commentId;
                    new bootstrap.Modal(document.getElementById('reportModal')).show();
                });
            });
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
        <jsp:include page="chat.jsp"></jsp:include>

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
