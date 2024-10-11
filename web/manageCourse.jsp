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

            .post {
                padding: 15px;
                background-color: #f1f1f1;
                border-radius: 10px;
                margin-bottom: 15px;
            }

            .close-btn {
                border: none;
                background: none;
                font-size: 1.5rem;
                color: #000;
            }

            .close-btn:hover {
                color: #dc3545; /* Màu khi hover */
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
                            <div class="post">
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
                                <p>${post.postContent}</p>
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
                        <button type="button" class="close-btn" data-bs-dismiss="modal" aria-label="Close">
                            <i class="fas fa-times"></i>
                        </button>
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
    </body>
</html>
