<%-- 
    Document   : manageCourse
    Created on : Oct 11, 2024, 4:19:13 PM
    Author     : Huy Võ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Course" %>
<%@ page import="model.MentorPost" %>
<%@ page import="model.User" %>
<%@ page import="java.sql.Timestamp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <title>Course</title>
        <style>
            .container{
                max-width: 95%;
            }
            .course-banner {
                background: linear-gradient(90deg, #7b4397, #dc2430);
                color: white;
                padding: 20px;
                border-radius: 10px;
                margin-bottom: 20px;
            }

            .course-feed {
                padding: 15px;
                background-color: white;
                border-radius: 10px;
                margin-bottom: 10px;
            }

            .side-panel {
                background-color: #f7f7f7;
                border-radius: 10px;
                padding: 20px;
            }

            .side-panel button {
                width: 100%;
                margin-bottom: 10px;
            }

            .modal-body {
                max-height: 70vh; /* Đặt chiều cao tối đa cho modal */
                overflow-y: auto; /* Cho phép cuộn dọc nếu nội dung quá dài */
            }

            .post {
                padding: 15px;
                background-color: #f1f1f1;
                border-radius: 10px;
                margin-bottom: 15px;
            }

            .dropdown {
                position: relative;
                display: inline-block;
                float: left;
                margin-top: 5px;
            }

            .btn btn-link {
                padding: 0;
                border: none;
                background: transparent;
            }

        </style>
    </head>
    <body>
        <%
            User user = (User) session.getAttribute("user");
            if (user == null) {
               response.sendRedirect("login.jsp");
               return;
            } else {
                Course course = (Course) session.getAttribute("course");
                int member = (int) session.getAttribute("member");
            }
        %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {

                document.getElementById('addType').addEventListener('change', function () {
                    var selectedType = this.value;
                    var deadlineContainer = document.getElementById('deadlineContainer');
                    // Kiểm tra nếu loại bài viết là "exercise" hoặc "test"
                    if (selectedType === 'exercise' || selectedType === 'test') {
                        deadlineContainer.style.display = 'block'; // Hiện trường deadline
                    } else {
                        deadlineContainer.style.display = 'none'; // Ẩn trường deadline
                    }
                });
            });
            function handlePostClick(title, content, createdAt, type, deadline, createdBy) {
                console.log("Post clicked");
                showPostDetails(title, content, createdAt, type, deadline, createdBy);
            }


        </script>
        <jsp:include page="header.jsp"/>
        <div class="container mt-5">
            <div class="course-banner text-center">
                <h2>${course.courseName}</h2>
            </div>

            <div class="row">
                <!-- Left side: Posts -->
                <div class="col-md-8">
                    <c:if test="${not empty sessionScope.posts}">
                        <c:forEach var="post" items="${sessionScope.posts}">
                            <input type="hidden" name="postId_${post.postId}" value="${post.postId}">
                            <div class="post" data-bs-toggle="modal" data-bs-target="#postDetailModal"
                                 data-title="${fn:escapeXml(post.postTitle)}"
                                 data-content="${fn:escapeXml(post.postContent)}"
                                 data-created-at="${fn:escapeXml(post.createdAt)}"
                                 data-type="${fn:escapeXml(post.postType)}"
                                 data-deadline="${fn:escapeXml(post.deadline)}"
                                 data-created-by="${fn:escapeXml(post.createdBy)}"
                                 data-post-id="${post.postId}"
                                 onclick="showPostDetails(this, '${post.postId}')"> 
                                <script>
                                    function showPostDetails(postElement, id) {
                                        // Lấy giá trị từ các thuộc tính data- của phần tử được nhấn
                                        const title = postElement.getAttribute('data-title');
                                        const content = postElement.getAttribute('data-content');
                                        const createdAt = postElement.getAttribute('data-created-at');
                                        const type = postElement.getAttribute('data-type');
                                        const deadline = postElement.getAttribute('data-deadline');
                                        const createdBy = postElement.getAttribute('data-created-by');
                                        const postId = postElement.getAttribute('data-post-id').value;  // Lấy postId từ thuộc tính data-post-id

                                        // Cập nhật thông tin hiển thị
                                        document.getElementById('postDetailTitle').innerText = title;
                                        document.getElementById('postDetailContent').innerText = content;
                                        document.getElementById('postDetailCreatedAt').innerText = createdAt ? new Date(createdAt).toLocaleDateString('en-GB') : 'N/A';
                                        document.getElementById('postDetailType').innerText = type;
                                        document.getElementById('postDetailDeadline').innerText = deadline ? new Date(deadline).toLocaleString('en-GB', {dateStyle: 'short', timeStyle: 'short'}) : 'N/A';
                                        document.getElementById('postDetailCreatedBy').innerText = createdBy;

                                        const deleteButton = document.querySelector('.dropdown-menu .btn-danger');
                                        let link = "deleteMentorPost?postId=" + id + "&courseId=${course.courseId}";
                                        deleteButton.onclick = function (event) {
                                            event.preventDefault();
                                            if (confirm("Do you want to delete this post?")) {
                                                deleteButton.href = link;
                                                window.location.href = deleteButton.href;
                                            }
                                        };


                                    }
                                </script>
                                <div class="d-flex justify-content-between">
                                    <div><strong>${post.postTitle}</strong></div>
                                    <c:choose>
                                        <c:when test="${not empty post.createdAt}">
                                            <fmt:formatDate value="${post.createdAt}" pattern="dd-MM-yyyy" />
                                        </c:when>
                                        <c:otherwise>
                                            N/A
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <c:set var="maxLength" value="100" />
                                <c:choose>
                                    <c:when test="${fn:length(post.postContent) > maxLength}">
                                        <p>${fn:substring(post.postContent, 0, maxLength)}...</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>${post.postContent}</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </c:if>

                    <c:if test="${empty sessionScope.posts}">
                        <p>No posts available.</p>
                    </c:if>
                </div>

                <!-- Right side: Buttons and member info -->
                <div class="col-md-4">
                    <div class="side-panel">
                        <div class="d-flex align-items-center mb-4">
                            <i class="fas fa-users"></i>&nbsp; 
                            <strong>${member}</strong>
                        </div>
                        <!-- Nút để mở modal -->
                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newPostModal">New Post</button>
                        <button class="btn btn-secondary">To Course Manage</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal tạo post -->
        <!-- Modal tạo post -->
        <div class="modal fade" id="newPostModal" tabindex="-1" aria-labelledby="newPostModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered"> <!-- Thêm lớp modal-dialog-centered -->
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="newPostModalLabel">Create New Post</h5>
                    </div>
                    <div class="modal-body">
                        <form id="mentorPostForm" action="addMentorPost" method="POST">
                            <input type="hidden" name="courseId" value="${course.courseId}">
                            <input type="hidden" name="username" value="${user.username}">
                            <div class="form-group">
                                <label for="postTitle">Title</label>
                                <input type="text" class="form-control" id="addTitle" name="addTitle" required>
                            </div>
                            <div class="form-group">
                                <label for="postContent">Content</label>
                                <textarea class="form-control" id="addContent" name="addContent" rows="3" required></textarea>
                            </div>
                            <div class="form-group">
                                <label for="postType">Type</label>
                                <select class="form-control" id="addType" name="addType" required>
                                    <option value="lecture">Lecture</option>
                                    <option value="exercise">Exercise</option>
                                    <option value="test">Test</option>
                                </select>
                            </div>
                            <div class="form-group" id="deadlineContainer" style="display: none;">
                                <label for="deadline">Deadline</label>
                                <input type="datetime-local" class="form-control" id="deadline" name="deadline">
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Create</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="postDetailModal" tabindex="-1" aria-labelledby="postDetailModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <strong><h3 class="modal-title" id="postDetailTitle"></h3></strong>
                        <div class="dropdown">
                            <button class="btn btn-link" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-ellipsis-v"></i>
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <li><a class="dropdown-item" href="#" onclick="editPost()">Edit</a></li>
                                <a href="#" class="btn btn-danger" confirm="Do you want to delete this post?">Delete</a>
                            </ul>
                        </div>
                    </div>
                    <div class="modal-body">
                        <p id="postDetailContent"></p>
                        <p><strong>Created At:</strong> <span id="postDetailCreatedAt"></span></p>
                        <p><strong>Type:</strong> <span id="postDetailType"></span></p>
                        <p><strong>Deadline:</strong> <span id="postDetailDeadline"></span></p>
                        <p><strong>Created By:</strong> <span id="postDetailCreatedBy"></span></p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
