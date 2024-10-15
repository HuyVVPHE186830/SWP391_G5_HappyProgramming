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
<%@ page import="java.util.List" %>
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

            .post {
                cursor: pointer;
            }
            .icon-link {
                font-size: 1.2rem; /* Tăng kích thước văn bản */
                margin-right: 20px; /* Khoảng cách giữa các thẻ */
            }

            .icon {
                font-size: 1.5rem; /* Tăng kích thước biểu tượng */
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
                int rmember = (int) session.getAttribute("rmember");
                List<User> listMentee = (List<User>) session.getAttribute("listMentee");
                List<User> listRequest = (List<User>) session.getAttribute("listRequest");
            }
        %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                document.getElementById('addType').addEventListener('change', function () {
                    var selectedType = this.value;
                    var deadlineContainer = document.getElementById('deadlineContainer');
                    if (selectedType === 'Exercise' || selectedType === 'Test') {
                        deadlineContainer.style.display = 'block';
                    } else {
                        deadlineContainer.style.display = 'none';
                    }
                });

                document.querySelectorAll('[id^="editType_"]').forEach(function (select) {
                    select.addEventListener('change', function () {
                        var postId = this.id.split('_')[1];
                        var selectedType = this.value;
                        var deadlineContainer = document.getElementById('deadlineContainer_' + postId);
                        var deadlineInput = document.getElementById('editDeadline_' + postId);

                        // Lưu giá trị deadline trước đó để khôi phục nếu cần
                        var previousDeadline = deadlineInput.value;

                        if (selectedType === 'Exercise' || selectedType === 'Test') {
                            deadlineContainer.style.display = 'block'; // Hiện deadline
                            // Khôi phục giá trị deadline nếu có
                            if (previousDeadline) {
                                deadlineInput.value = previousDeadline; // Khôi phục giá trị
                            }
                        } else {
                            deadlineContainer.style.display = 'none'; // Ẩn deadline
                            deadlineInput.value = ''; // Xóa giá trị của deadline
                        }
                    });
                    // Gọi sự kiện change ngay để khởi tạo trạng thái ban đầu
                    select.dispatchEvent(new Event('change'));
                });

            });

            function confirmDelete(postId, courseId) {
                if (confirm("Do you want to delete this post?")) {
                    window.location.href = "deleteMentorPost?postId=" + postId + "&courseId=" + courseId;
                }
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
                            <div class="post" data-bs-toggle="modal" data-bs-target="#postDetailModal_${post.postId}">
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

                            <!-- Modal xem chi tiết bài viết -->
                            <div class="modal fade" id="postDetailModal_${post.postId}" tabindex="-1" aria-labelledby="postDetailModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <strong><h3 class="modal-title">${post.postTitle}</h3></strong>
                                            <div class="dropdown">
                                                <button class="btn btn-link" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <i class="fas fa-ellipsis-v"></i>
                                                </button>
                                                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                    <a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#editPostModal_${post.postId}">Edit</a>
                                                    <a href="javascript:void(0);" class="dropdown-item delete" onclick="confirmDelete('${post.postId}', '${course.courseId}')">Delete</a>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="modal-body">
                                            <p>${post.postContent}</p>
                                            <p><strong>Type:</strong>
                                                <c:choose>
                                                    <c:when test="${post.postTypeId == 1}">Lecture</c:when>
                                                    <c:when test="${post.postTypeId == 2}">Exercise</c:when>
                                                    <c:when test="${post.postTypeId == 3}">Test</c:when>
                                                    <c:otherwise>Unknown Type</c:otherwise>
                                                </c:choose>
                                            </p>
                                            <c:if test="${not empty post.deadline}">
                                                <p><strong>Deadline:</strong> 
                                                    <fmt:formatDate value="${post.deadline}" pattern="dd/MM/yyyy, HH:mm" />
                                                </p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Modal chỉnh sửa bài viết -->
                            <div class="modal fade" id="editPostModal_${post.postId}" tabindex="-1" aria-labelledby="editPostModalLabel_${post.postId}" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="editPostModalLabel_${post.postId}">Edit Post</h5>
                                        </div>
                                        <div class="modal-body">
                                            <form id="editPostForm_${post.postId}" action="editMentorPost" method="POST">
                                                <input type="hidden" name="postId" value="${post.postId}">
                                                <input type="hidden" name="courseId" value="${course.courseId}">
                                                <div class="form-group">
                                                    <label for="postTitle">Title</label>
                                                    <input type="text" class="form-control" id="editTitle_${post.postId}" name="editTitle" value="${post.postTitle}" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="postContent">Content</label>
                                                    <textarea class="form-control" id="editContent_${post.postId}" name="editContent" rows="3" required>${post.postContent}</textarea>
                                                </div>
                                                <div class="form-group">
                                                    <label for="postType">Type</label>
                                                    <select class="form-control" id="editType_${post.postId}" name="editType" required>
                                                        <option value="Lecture" ${post.postTypeId == 1 ? 'selected' : ''}>Lecture</option>
                                                        <option value="Exercise" ${post.postTypeId == 2 ? 'selected' : ''}>Exercise</option>
                                                        <option value="Test" ${post.postTypeId == 3 ? 'selected' : ''}>Test</option>
                                                    </select>
                                                </div>
                                                <div class="form-group" id="deadlineContainer_${post.postId}">
                                                    <label for="deadline">Deadline</label>
                                                    <input type="datetime-local" class="form-control" id="editDeadline_${post.postId}" name="editDeadline" value="${post.deadline}">
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#postDetailModal_${post.postId}">Back</button>
                                                    <button type="submit" class="btn btn-primary">Save changes</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
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
                            <a href="#" data-bs-toggle="modal" data-bs-target="#memberListModal" class="text-decoration-none icon-link">
                                <i class="fas fa-users icon"></i>&nbsp; 
                                <strong>${member}</strong>
                            </a>
                            <a href="#" data-bs-toggle="modal" data-bs-target="#requestListModal" class="text-decoration-none icon-link ms-3">
                                <i class="fas fa-bell icon"></i>
                                <strong>${rmember}</strong>
                            </a>

                        </div>
                        <!-- Nút để mở modal -->
                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newPostModal">New Post</button>
                        <button class="btn btn-secondary">To Course Manage</button>
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
                                        <option value="Lecture">Lecture</option>
                                        <option value="Exercise">Exercise</option>
                                        <option value="Test">Test</option>
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

            <div class="modal fade" id="memberListModal" tabindex="-1" aria-labelledby="memberListModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" style="max-width: 800px;"> <!-- Giới hạn chiều rộng tối đa -->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 class="modal-title" id="memberListModalLabel">List Mentee: ${member}</h3>
                        </div>
                        <p class="modal-title" style="margin: 10px 0 0 20px">Course: ${course.courseName}</p>
                        <div class="modal-body">
                            <!-- Bảng hiển thị thông tin mentee -->
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Avatar</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Date of Birth</th>
                                        <th>Action</th> <!-- Nút Ban -->
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${not empty listMentee}">
                                        <c:forEach var="user" items="${listMentee}">
                                            <tr>
                                                <td>
                                                    <img src="data:image/jpeg;base64,${user.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%;object-fit: cover;">
                                                </td>
                                                <td>${user.lastName} ${user.firstName}</td>
                                                <td>${user.mail}</td>
                                                <td>${user.dob}</td>
                                                <td>
                                                    <form action="manageMentee" method="post">
                                                        <input type="hidden" name="courseId" value="${course.courseId}">
                                                        <input type="hidden" name="username" value="${user.username}">
                                                        <button type="submit" class="btn btn-danger btn-sm">Ban</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${empty listMentee}">
                                        <tr>
                                            <td colspan="5" class="text-center">No mentees found.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>



            <div class="modal fade" id="requestListModal" tabindex="-1" aria-labelledby="requestListModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" style="max-width: 800px;"> <!-- Giới hạn chiều rộng tối đa -->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 class="modal-title" id="requestListModalLabel">List Request: ${rmember}</h3>
                        </div>
                        <p class="modal-title" style="margin: 10px 0 0 20px">Course: ${course.courseName}</p>
                        <div class="modal-body">
                            <!-- Bảng hiển thị thông tin yêu cầu -->
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Avatar</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Date of Birth</th>
                                        <th>Action</th> <!-- Nút Accept/Decline -->
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${not empty listRequest}">
                                        <c:forEach var="user" items="${listRequest}">
                                            <tr>
                                                <td>
                                                    <img src="data:image/jpeg;base64,${user.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%;object-fit: cover;">
                                                </td>
                                                <td>${user.lastName} ${user.firstName}</td>
                                                <td>${user.mail}</td> <!-- Thêm Email -->
                                                <td>${user.dob}</td> <!-- Thêm Ngày sinh -->
                                                <td>
                                                    <form action="manageMentee" method="get">
                                                        <input type="hidden" name="courseId" value="${course.courseId}">
                                                        <input type="hidden" name="username" value="${user.username}">
                                                        <button type="submit" class="btn btn-success btn-sm me-2" name="action" value="accept">
                                                            <i class="fas fa-check"></i> <!-- Icon dấu tick -->
                                                        </button>
                                                        <button type="submit" class="btn btn-danger btn-sm" name="action" value="decline">
                                                            <i class="fas fa-times"></i> <!-- Icon dấu X -->
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${empty listRequest}">
                                        <tr>
                                            <td colspan="5" class="text-center">No request found.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>


    </body>
</html>
