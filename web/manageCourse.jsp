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
                background:  linear-gradient(90deg, #6a11cb, #5d3fd3);
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
                max-height: 70vh;
                overflow-y: auto;
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
                font-size: 1.2rem;
                margin-right: 20px;
            }

            .icon {
                font-size: 1.5rem;
            }

            table {
                margin: 0 auto;
                width: 80%;
                max-width: 1200px;
                background-color: #ffffff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-collapse: collapse;
                border-radius: 8px;
                overflow: hidden;
            }

            .table th, .table td {
                padding: 15px;
                text-align: center;
                font-size: 1rem;
            }

            .table thead th {
                background-color: #5d3fd3;
                color: #ffffff;
                font-weight: bold;
                text-transform: uppercase;
            }

            .table tbody tr:nth-child(even) {
                background-color: #f8f8f8;
            }

            .table tbody tr:nth-child(odd) {
                background-color: #ffffff;
            }

            .table tbody td {
                border-bottom: 1px solid #dddddd;
            }

            .table .text-center {
                text-align: center;
                font-size: 1rem;
                color: #888888;
            }

            .btn-primary{
                background-color: #5d3fd3 !important;
                border-color: #5d3fd3 !important;
            }

            .btn-primary:focus {
                box-shadow: none !important;
            }

            .btn-primary:hover {
                background-color: #3d249e !important;
            }

            .notification {
                position: fixed;
                top: 50px;
                right: 20px;
                background-color: #4caf50;
                color: white;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                font-size: 16px;
                font-family: Arial, sans-serif;
                z-index: 9999;
                opacity: 0;
                transform: scale(0.8) translateY(20px);
                transition: opacity 0.3s ease, transform 0.5s ease;
            }
            .hidden {
                display: none;
            }
        </style>
    </head>
    <body>
        <%
                Course course = (Course) session.getAttribute("course");
                int member = (int) session.getAttribute("member");
                int rmember = (int) session.getAttribute("rmember");
                String mentorName = (String) session.getAttribute("mentorName");
                List<User> listMentee = (List<User>) session.getAttribute("listMentee");
                List<User> listRequest = (List<User>) session.getAttribute("listRequest");
        %>
        <c:set var="activePostId" value="${param.postId}" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <div id="notification" class="notification hidden"></div>
        <script>
            const successMessage = '<%= session.getAttribute("success") != null ? session.getAttribute("success") : "" %>';
            const errorMessage = '<%= session.getAttribute("error") != null ? session.getAttribute("error") : "" %>';
            if (successMessage) {
                showSuccess(successMessage);
            <% session.removeAttribute("success"); %>
            }
            if (errorMessage) {
                showError(errorMessage);
            <% session.removeAttribute("error"); %>
            }
            function showSuccess(message) {
                const notification = document.getElementById('notification');
                notification.innerHTML = message;
                notification.classList.remove('hidden');
                setTimeout(() => {
                    notification.style.opacity = '1';
                    notification.style.transform = 'translateY(0)';
                    notification.style.backgroundColor = '#4caf50';
                }, 100);
                setTimeout(() => {
                    notification.style.opacity = '0';
                    notification.style.transform = 'scale(0.8) translateY(20px)';
                    setTimeout(() => {
                        notification.classList.add('hidden');
                    }, 500);
                }, 3000);
            }
            function showError(message) {
                const notification = document.getElementById('notification');
                notification.innerHTML = message;
                notification.classList.remove('hidden');
                setTimeout(() => {
                    notification.style.opacity = '1';
                    notification.style.transform = 'translateY(0)';
                    notification.style.backgroundColor = '#dc133b';
                }, 100);
                setTimeout(() => {
                    notification.style.opacity = '0';
                    notification.style.transform = 'scale(0.8) translateY(20px)';
                    setTimeout(() => {
                        notification.classList.add('hidden');
                    }, 500);
                }, 3000);
            }
        </script>
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
                        var previousDeadline = deadlineInput.value;

                        if (selectedType === 'Exercise' || selectedType === 'Test') {
                            deadlineContainer.style.display = 'block';
                            if (previousDeadline) {
                                deadlineInput.value = previousDeadline;
                            }
                        } else {
                            deadlineContainer.style.display = 'none';
                            deadlineInput.value = '';
                        }
                    });
                    select.dispatchEvent(new Event('change'));
                });

            });

            function confirmDelete(postId, courseId, mentorName) {
                if (confirm("Do you want to delete this post?")) {
                    window.location.href = "deleteMentorPost?postId=" + postId + "&courseId=" + courseId + "&mentorName=" + mentorName;
                }
            }

            $(document).ready(function () {
                $('[id^=ajaxCommentForm]').on('submit', function (event) {
                    event.preventDefault();
                    var formData = $(this).serialize();
                    var form = $(this);
                    var commentsSection = form.closest('.add-comment').siblings('.comments-section');
                    $.ajax({
                        type: 'POST',
                        url: 'manageCourseComment',
                        data: formData,
                        success: function (response) {
                            commentsSection.prepend(response);
                            commentsSection.find('p:contains("No comment yet")').remove();
                            form.find('input[name="commentContent"]').val('');
                        },
                        error: function () {
                            alert('Error.');
                        }
                    });
                });
            });

            document.getElementById('editPostForm_${post.postId}').onsubmit = function (event) {
                var input = document.getElementById('editFile_${post.postId}');
                var oldFileContent = document.querySelector('input[name="oldFileContent"]').value;

                if (input.files.length === 0) {
                    var oldFileInput = document.createElement('input');
                    oldFileInput.type = 'hidden';
                    oldFileInput.name = 'addFile';
                    oldFileInput.value = oldFileContent;
                    this.appendChild(oldFileInput);
                }
            };
        </script>
        <c:set var="user" value="${sessionScope.user}"/>
        <jsp:include page="header.jsp"/>
        <div class="container mt-5">
            <div class="course-banner text-center">
                <h2>${course.courseName}</h2>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <c:if test="${not empty sessionScope.posts}">
                        <c:forEach var="post" items="${sessionScope.posts}">
                            <input type="hidden" name="postId_${post.postId}" value="${post.postId}">
                            <div class="post" data-bs-toggle="modal" data-bs-target="#postDetailModal_${post.postId}">
                                <div class="d-flex justify-content-between">
                                    <div><strong>${post.postTitle}</strong></div>
                                    <c:choose>
                                        <c:when test="${not empty post.createdAt}">
                                            <fmt:formatDate value="${post.createdAt}" pattern="MM/dd/yyyy" />
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
                            <div class="modal fade" id="postDetailModal_${post.postId}" tabindex="-1" aria-labelledby="postDetailModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered modal-lg" style="max-width: 1000px;">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <strong><h3 class="modal-title">${post.postTitle}</h3></strong>
                                                <c:if test="${user.username == mentorName}">
                                                <div class="dropdown">
                                                    <button class="btn btn-link" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" style="color: #5d3fd3" aria-expanded="false">
                                                        <i class="fas fa-ellipsis-v"></i>
                                                    </button>
                                                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                        <a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#editPostModal_${post.postId}">Edit</a>
                                                        <a href="javascript:void(0);" class="dropdown-item delete" onclick="confirmDelete('${post.postId}', '${course.courseId}', '${mentorName}')">Delete</a>
                                                    </ul>
                                                </div>
                                            </c:if>
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
                                            <c:if test="${not empty post.fileContent}">
                                                <p><strong>File:</strong> 
                                                    <a href="addMentorPost?postId=${post.postId}" download="${post.fileName}">
                                                        ${post.fileName}
                                                    </a>
                                                </p>
                                            </c:if>
                                            <c:if test="${not empty post.deadline}">
                                                <p><strong>Deadline:</strong> 
                                                    <fmt:formatDate value="${post.deadline}" pattern="MM/dd/yyyy, HH:mm" />
                                                </p>
                                                <c:choose>
                                                    <c:when test="${user.username == mentorName}">
                                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#viewSubmit_${post.postId}">
                                                            View Submit
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#submitFormModal_${post.postId}">
                                                            Submit
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <hr class="mt-3 mb-2">
                                            <div id="commentForm" class="add-comment mt-3" style="margin-bottom: 20px">
                                                <form id="ajaxCommentForm${post.postId}">
                                                    <input type="hidden" name="postId" value="${post.postId}">
                                                    <input type="hidden" name="username" value="${user.username}">
                                                    <input type="hidden" name="courseId" value="${course.courseId}">
                                                    <div class="input-group">
                                                        <input style="margin-right: 10px;" type="text" name="commentContent" class="form-control" placeholder="Add a comment..." required>
                                                        <button type="submit" class="btn btn-primary">
                                                            <i class="fas fa-paper-plane"></i>
                                                        </button>
                                                    </div>
                                                </form>
                                            </div>

                                            <div id="commentsSection${post.postId}" class="comments-section" style="max-height: 250px; overflow-y: auto;">
                                                <c:if test="${not empty sessionScope.postComments[post.postId]}">
                                                    <c:forEach var="comment" items="${sessionScope.postComments[post.postId]}">
                                                        <c:set var="commentUser" value="${sessionScope.userMap[comment.commentedBy]}" />
                                                        <div class="comment d-flex align-items-start mb-3" style="margin-bottom: 5px !important;">
                                                            <img src="data:image/jpeg;base64,${commentUser.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%; object-fit: cover;">
                                                            <div class="comment-body" style="background-color: #f1f1f1; margin-left:10px; padding: 10px; border-radius: 5px;">
                                                                <div class="comment-author-info d-flex justify-content-between align-items-center">
                                                                    <p class="comment-author fw-bold mb-1" style="font-weight: bold; margin-bottom: 0;">${commentUser.lastName} ${commentUser.firstName}</p>
                                                                </div>
                                                                <p class="comment-text" style="margin-bottom: 0">${comment.commentContent}</p>
                                                            </div>
                                                        </div>
                                                        <p style="font-size: 0.8em; color: gray; margin: 0 50px 10px;">
                                                            <fmt:formatDate value="${post.createdAt}" pattern="dd-MM-yyyy, HH:mm" />
                                                        </p>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${empty sessionScope.postComments[post.postId]}">
                                                    <p>No comment yet</p>
                                                </c:if>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="viewSubmit_${post.postId}" tabindex="-1" aria-labelledby="viewSubmit_${post.postId}" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" style="max-width: 1000px;">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="viewSubmitModalLabel_${post.postId}">Submission</h5>
                                        </div>
                                        <div class="modal-body">
                                            <table class="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th style="width: 6.25%">Avatar</th>
                                                        <th>Name</th>
                                                        <th style="width: 25%">Submitted At</th>
                                                        <th style="width: 6.25%">Status</th>
                                                        <th style="width: 6.25%">Download</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="submission" items="${menteeSubmission[post.postId]}">
                                                        <tr>
                                                            <td style="text-align: center; vertical-align: middle;">
                                                                <img src="data:image/jpeg;base64,${submission.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%; object-fit: cover;">
                                                            </td>
                                                            <td>${submission.fullName}</td>
                                                            <c:choose>
                                                                <c:when test="${submission.submittedAt == null}">
                                                                    <td>-</td>
                                                                    <td>-</td>
                                                                    <td>
                                                                        <a class="btn btn-secondary" disabled>
                                                                            <i class="fas fa-download"></i>
                                                                        </a>
                                                                    </td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <td><fmt:formatDate value="${submission.submittedAt}" pattern="MM/dd/yyyy, HH:mm" /></td>
                                                                    <td>
                                                                        <c:choose>
                                                                            <c:when test="${submission.isLate}">
                                                                                <span style="color: red; font-weight: bold;">LATE</span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <span style="color: green; font-weight: bold;">ON TIME</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                    <td>
                                                                        <a href="Submit?submissionId=${submission.submissionId}" class="btn btn-primary">
                                                                            <i class="fas fa-download"></i>
                                                                        </a>
                                                                    </td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </tr>
                                                    </c:forEach>
                                                    <c:if test="${empty menteeSubmission[post.postId]}">
                                                        <tr>
                                                            <td colspan="5" class="text-center">No submission found.</td>
                                                        </tr>
                                                    </c:if>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>




                            <div class="modal fade" id="submitFormModal_${post.postId}" tabindex="-1" aria-labelledby="submitFormModalLabel_${post.postId}" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" style="max-width: 800px;">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="submitFormModalLabel_${post.postId}">Submit</h5>
                                        </div>
                                        <div class="modal-body">
                                            <c:if test="${not empty postSubmissions[post.postId]}">
                                                <c:forEach var="submission1" items="${postSubmissions[post.postId]}">
                                                    <c:if test="${submission1.submittedBy == user.username}">
                                                        <div class="alert alert-info">
                                                            Lastest Submission: <strong>${submission1.fileName}</strong>
                                                            <a href="Submit?submissionId=${submission1.submissionId}" class="btn btn-primary"><i class="fas fa-download"></i></a>
                                                        </div>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                            <form id="submitAdditionalForm_${post.postId}" action="Submit" method="POST" enctype="multipart/form-data">
                                                <input type="hidden" name="postId" value="${post.postId}">
                                                <input type="hidden" name="deadline" value="${post.deadline}">
                                                <input type="hidden" name="courseId" value="${course.courseId}">
                                                <input type="hidden" name="username" value="${user.username}">
                                                <input type="hidden" name="mentorName" value="${mentorName}">
                                                <div class="form-group">
                                                    <label for="additionalInfo">Submission</label>
                                                    <input type="file" class="form-control" id="additionalInfo_${post.postId}" name="file" required>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#postDetailModal_${post.postId}">
                                                        <i class="fas fa-arrow-left"></i>
                                                    </button>
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="fas fa-save"></i>
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="modal fade" id="editPostModal_${post.postId}" tabindex="-1" aria-labelledby="editPostModalLabel_${post.postId}" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" style="max-width: 1000px;">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="editPostModalLabel_${post.postId}">Edit Post</h5>
                                        </div>
                                        <p class="modal-title" style="margin: 10px 0 0 20px">Last change: <fmt:formatDate value="${post.createdAt}" pattern="MM/dd/yyyy, HH:mm" /></p>
                                        <div class="modal-body">
                                            <form id="editPostForm_${post.postId}" action="editMentorPost" method="POST" enctype="multipart/form-data">
                                                <input type="hidden" name="postId" value="${post.postId}">
                                                <input type="hidden" name="courseId" value="${course.courseId}">
                                                <input type="hidden" name="mentorName" value="${mentorName}">
                                                <input type="hidden" name="oldFileContent" value="${post.fileContent}">
                                                <input type="hidden" name="oldFileName" value="${post.fileName}">
                                                <input type="hidden" name="oldFileType" value="${post.fileType}">
                                                <input type="file" style="display:none" name="oldFileContent" value="${post.fileContent}">
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
                                                <div class="form-group">
                                                    <label for="postTitle">File</label>
                                                    <c:choose>
                                                        <c:when test="${not empty post.fileName}">
                                                            <input text="text" class="form-control" id="editFile" onfocus="(this.type = 'file')" name="addFile" placeholder="${post.fileName}">
                                                            <input type="file" id="editFile_${post.postId}" name="addFile" style="display: none;" onchange="updateFileName(${post.postId})">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input text="text" class="form-control" id="editFile" onfocus="(this.type = 'file')" name="addFile" placeholder="No File Chosen">
                                                            <input type="file" id="editFile_${post.postId}" name="addFile" style="display: none;" onchange="updateFileName(${post.postId})">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="form-group" id="deadlineContainer_${post.postId}">
                                                    <label for="deadline">Deadline</label>
                                                    <input type="datetime-local" class="form-control" id="editDeadline_${post.postId}" name="editDeadline" value="${post.deadline}" min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new java.util.Date()) %>">
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#postDetailModal_${post.postId}">
                                                        <i class="fas fa-arrow-left"></i>
                                                    </button>
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="fas fa-save"></i>
                                                    </button>
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

                <div class="col-md-4">
                    <div class="side-panel">
                        <div class="d-flex align-items-center mb-4">
                            <a href="#" data-bs-toggle="modal" data-bs-target="#memberListModal" class="text-decoration-none icon-link">
                                <i class="fas fa-users icon" style="font-size: 20px;"></i>&nbsp; 
                                <strong style="color: #5d3fd3;font-size: 20px;">${member}</strong>
                            </a>
                            <c:choose>
                                <c:when test="${user.username == mentorName}">
                                    <a href="#" data-bs-toggle="modal" data-bs-target="#requestListModal" class="text-decoration-none icon-link ms-3">
                                        <i class="fas fa-bell icon" style="font-size: 20px;"></i>
                                        <strong style="color: #5d3fd3; font-size: 20px;">${rmember}</strong>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="sendMessage?username=${mentorName}" class="text-decoration-none">
                                        <button type="button" class="btn btn-primary" style="margin-left: 30px;">
                                            <i class="fas fa-comments"></i> Chat with Mentor
                                        </button>
                                    </a>
                                </c:otherwise>
                            </c:choose>

                        </div>
                        <c:if test="${user.username == mentorName}">
                            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newPostModal">
                                <i class="fas fa-plus"></i>
                                New Post
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>

            <!-- Modal tạo post -->
            <div class="modal fade" id="newPostModal" tabindex="-1" aria-labelledby="newPostModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" style="max-width: 1000px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="newPostModalLabel">Create New Post</h5>
                        </div>
                        <div class="modal-body">
                            <form id="mentorPostForm" action="addMentorPost" method="POST" enctype="multipart/form-data">
                                <input type="hidden" name="courseId" value="${course.courseId}">
                                <input type="hidden" name="mentorName" value="${mentorName}">
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
                                <div class="form-group">
                                    <label for="postTitle">File</label>
                                    <input type="file" class="form-control" id="addFile" name="addFile">
                                </div>
                                <div class="form-group" id="deadlineContainer" style="display: none;">
                                    <label for="deadline">Deadline</label>
                                    <input type="datetime-local" class="form-control" id="deadline" name="deadline" min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new java.util.Date()) %>">
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-check"></i>
                                    </button>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="memberListModal" tabindex="-1" aria-labelledby="memberListModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" style="max-width: 1000px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 class="modal-title" id="memberListModalLabel">List Mentee: ${member}</h3>
                        </div>
                        <p class="modal-title" style="margin: 10px 0 0 20px">Course: ${course.courseName}</p>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th style="width: 40px">Avatar</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                            <c:if test="${user.username == mentorName}">
                                            <th>Date of Birth</th>
                                            </c:if>
                                        <th>Chat</th>
                                            <c:if test="${user.username == mentorName}">
                                            <th>Ban</th>
                                            </c:if>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${not empty listMentee}">
                                        <c:forEach var="mentee" items="${listMentee}">
                                            <tr>
                                                <td style="text-align: center; vertical-align: middle;">
                                                    <img src="data:image/jpeg;base64,${mentee.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%;object-fit: cover;">
                                                </td>
                                                <td>${mentee.lastName} ${mentee.firstName}</td>
                                                <td>${mentee.mail}</td>
                                                <c:if test="${user.username == mentorName}">
                                                    <td><fmt:formatDate value="${mentee.dob}" pattern="MM/dd/yyyy"/></td>
                                                </c:if>
                                                <td>
                                                    <a href="sendMessage?username=${mentee.username}">
                                                        <button type="button" class="btn btn-primary btn-sm">
                                                            <i class="fas fa-comments"></i>
                                                        </button>
                                                    </a>
                                                </td>
                                                <c:if test="${user.username == mentorName}">
                                                    <td>
                                                        <form action="manageMentee" method="post">
                                                            <input type="hidden" name="courseId" value="${course.courseId}">
                                                            <input type="hidden" name="username" value="${mentee.username}">
                                                            <input type="hidden" name="mentorName" value="${mentorName}">
                                                            <button type="submit" class="btn btn-danger btn-sm">
                                                                <i class="fas fa-ban"></i>
                                                            </button>
                                                        </form>
                                                    </td>
                                                </c:if>
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
                <div class="modal-dialog modal-dialog-centered" style="max-width: 1000px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 class="modal-title" id="requestListModalLabel">List Request: ${rmember}</h3>
                        </div>
                        <p class="modal-title" style="margin: 10px 0 0 20px">Course: ${course.courseName}</p>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th style="width: 40px">Avatar</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Date of Birth</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${not empty listRequest}">
                                        <c:forEach var="requesto" items="${listRequest}">
                                            <tr>
                                                <td style="text-align: center; vertical-align: middle;">
                                                    <img src="data:image/jpeg;base64,${requesto.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%;object-fit: cover;">
                                                </td>
                                                <td>${requesto.lastName} ${requesto.firstName}</td>
                                                <td>${requesto.mail}</td>
                                                <td><fmt:formatDate value="${requesto.dob}" pattern="MM/dd/yyyy"/></td>                                             
                                                <td>
                                                    <form action="manageMentee" method="get">
                                                        <input type="hidden" name="courseId" value="${course.courseId}">
                                                        <input type="hidden" name="username" value="${requesto.username}">
                                                        <input type="hidden" name="mentorName" value="${mentorName}">
                                                        <button type="submit" class="btn btn-success btn-sm me-2" name="action" value="accept" style="width: 70px">
                                                            <i class="fas fa-check"></i>
                                                        </button>
                                                        <button type="submit" class="btn btn-danger btn-sm" name="action" value="decline" style="width: 35px">
                                                            <i class="fas fa-times"></i>
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
