<%-- 
    Document   : userProfile
    Created on : Sep 21, 2024, 11:38:41 PM
    Author     : Huy Võ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="java.util.Date" %>
<%@ page import="service.ImageConverter" %>
<%@ page session="true" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="service.FileConverter" %>
<%@ page import="java.io.File" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Profile</title>
        <style>
            body {
                position: relative;
                background: url(img/banner.jpg) no-repeat center center fixed;
                background-size: cover;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            body::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0, 0, 0, 0.4);
                z-index: 1;
            }

            body > * {
                position: relative;
                z-index: 2;
            }

            .profile-form {
                background-color: #fff;
                border-radius: 25px;
                display: flex;
                width: 60%;
                box-shadow: 0 0 10px #888;
                flex-direction: column;
                padding: 20px 0 10px 30px;
            }

            .profile-form h2 {
                text-align: center;
                margin-bottom: 20px;
            }

            .profile-container {
                display: flex;
                align-items: center;
                width: 100%;
                margin-bottom: 3%;
            }

            .avatar-container {
                flex: 0 0 27%;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                margin-left: 3%;
            }

            .right-part {
                flex: 0 0 67%;
                margin-right: 3%;
                margin-top: 2%;
            }

            .avatar-image {
                width: 200px;
                height: 200px;
                border-radius: 50%;
                object-fit: cover;
                border: 2px solid #ccc;
                margin-bottom: 20px;
                margin-left: 5%;
            }

            .button-upload {
                width: 50%;
                height: 40px;
                background-color: #5d3fd3;
                color: #fff;
                padding: 5px 10px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-left: 20%;
            }

            .button-upload:hover {
                background-color: #452cbf;
            }

            .input-container {
                display: flex;
                align-items: center;
                margin-bottom: 15px;
                width: calc(100% - 10px);
            }

            .input-container label {
                width: 20%;
                text-align: right;
                margin-right: 10px;
                font-weight: bold;
            }

            .input-container input {
                width: calc(100% - 30% + 10px);
                padding: 10px;
                font-size: 0.9rem;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
            }

            .input-container input[disabled] {
                background-color: #f0f0f0;
            }

            .input-container i {
                cursor: pointer;
            }

            .input-container i:hover {
                color: #5d3fd3;
            }

            .button-container {
                text-align: center;
                margin-top: 20px;
            }

            .button-save {
                background-color: #5d3fd3;
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            .button-save:hover {
                background-color: #452cbf;
            }

            .name-container {
                display: flex;
                width: 100%;
                justify-content: space-between;
            }

            .name-input {
                display: flex;
                align-items: center;
                width: calc(100% - 30% + 10px);
                justify-content: space-between;
            }

            .name-input label {
                width: 30%;
                text-align: right;
                margin-right: 10px;
                font-weight: bold;
            }

            .name-input input {
                width: 40%;
                padding: 10px;
                font-size: 0.9rem;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
            }
            .error-message {
                color: red;
                font-weight: bold;
                text-align: center;
                font-size: 12px;
            }

            .success-message {
                color: green;
                font-weight: bold;
                text-align: center;
                font-size: 12px;
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <%
            User user = (User) session.getAttribute("user");
            if (user == null) {
               response.sendRedirect("login.jsp");
               return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dobFormatted = user.getDob() != null ? sdf.format(user.getDob()) : "";
            String base64Cv = user.getCvPath();
        %>
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
                notification.textContent = message;
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
                notification.textContent = message;
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
            let originalValues = {};
            let mimeType, fileName;

            function toggleEdit() {
                const inputs = document.querySelectorAll('.input-container input');
                const editButton = document.getElementById('editButton');
                const saveButton = document.getElementById('saveButton');
                const cancelButton = document.getElementById('cancelButton');
                const cvFileInput = document.getElementById('cvFileInput');
                const linkCv = document.getElementById('linkCv');
                inputs.forEach(input => {
                    if (input.name !== 'username') {
                        if (!originalValues[input.name]) {
                            originalValues[input.name] = input.value;
                        }
                        input.disabled = false;
                    }
                });
                editButton.style.display = 'none';
                saveButton.style.display = 'inline-block';
                cancelButton.style.display = 'inline-block';
                linkCv.style.display = 'none';
                cvFileInput.style.display = 'inline';
            }

            function saveChanges() {
                alert("Do you want to save changes?");
                toggleEdit();
            }

            document.addEventListener('DOMContentLoaded', function () {
                function base64ToFile(base64String, fileName) {
                    let mimeType;
                    if (base64String.startsWith('JVBERi0') || base64String.startsWith('0M8R4KGx')) {
                        mimeType = 'application/pdf';
                        fileName += '.pdf';
                    } else if (base64String.startsWith('/9j/') || base64String.startsWith('iVBORw0KGgo') || base64String.startsWith('R0lGODdh') || base64String.startsWith('Qk') || base64String.startsWith('II') || base64String.startsWith('MM') || base64String.startsWith('UklGR')) {
                        mimeType = 'image/jpeg';
                        fileName += '.jpg';
                    } else {
                        alert('Invalid file type. Only PDF and image files are allowed.');
                        return;
                    }

                    const base64Data = base64String.replace(/^data:.+;base64,/, '');
                    const byteCharacters = atob(base64Data);
                    const byteNumbers = new Array(byteCharacters.length);

                    for (let i = 0; i < byteCharacters.length; i++) {
                        byteNumbers[i] = byteCharacters.charCodeAt(i);
                    }

                    const byteArray = new Uint8Array(byteNumbers);
                    const blob = new Blob([byteArray], {type: mimeType});
                    const url = URL.createObjectURL(blob);

                    const link = document.createElement('a');
                    link.href = url;
                    link.download = fileName;
                    link.click();
                    URL.revokeObjectURL(url);
                }

                document.getElementById('download').addEventListener('click', () => {
                    let base64String = document.getElementById('cvFile').value;
                    console.log(base64String);
                    const fileName = "CV";
                    base64ToFile(base64String, fileName);
                });
            });
            document.addEventListener('DOMContentLoaded', function () {
                function selectRole() {
                    const role = document.getElementById('role').value;
                    const userProfile = document.getElementById('userProfileForm');
                    if (role === "2") {
                        userProfile.enctype = 'multipart/form-data';
                    } else {
                        userProfile.enctype = '';
                    }
                }

                const roleInput = document.getElementById('role');
                roleInput.addEventListener('change', selectRole);
                selectRole();
            });

            function validateFileSize() {
                const fileInput = document.getElementById("cvContainer");
                const file = fileInput.files[0];
                const maxSize = 5 * 1024 * 1024;

                if (file) {
                    if (file.size > maxSize) {
                        alert("CV must be less than 5MB!");
                        fileInput.value = "";
                        return false;
                    }
                }
                return true;
            }
        </script>
        <div class="profile-form">
            <div class="profile-container">
                <div class="avatar-container">
                    <form action="changeAvatar" method="post" enctype="multipart/form-data" id="avatarForm">
                        <a onclick="changeImage()">
                            <img src="data:image/jpeg;base64,<%= user.getAvatarPath() %>" alt="Avatar" class="avatar-image">
                        </a>

                    </form>
                </div>
                <div class="right-part">
                    <form action="userProfile" method="post" id="userProfileForm">
                        <input type="number" id="role" name="role" style="display: none;" value="<%= user.getRoleId() %>">
                        <h2>User Profile</h2>

                        <c:if test="${!user.isIsVerified()}">
                            <p style="text-align: center; margin-bottom: 20px; font-weight: 600; color: red;">
                                Your account is not verified! Please <a href="verifyEmail?send=true">click here</a> to verify!
                            </p>
                        </c:if>
                        <c:if test="${not empty sessionScope.verify}">
                            <p style="text-align: center; margin-bottom: 20px; font-weight: 600; color: green;">
                                ${sessionScope.verify}
                            </p>
                        </c:if>
                        <% session.removeAttribute("verify"); %>
                        <div class="input-container">
                            <label>First Name:</label>
                            <div class="name-input">
                                <input type="text" name="firstName" value="<%= user.getFirstName() %>" disabled>
                                <label>Last Name:</label>
                                <input type="text" name="lastName" value="<%= user.getLastName() %>" disabled>
                            </div>
                        </div>
                        <div class="input-container">
                            <label>Username:</label>
                            <input type="text" name="username" value="<%= user.getUsername() %>" disabled>
                            <input type="hidden" name="usernameHidden" value="<%= user.getUsername() %>">
                        </div>
                        <div class="input-container">
                            <label>Date of Birth:</label>
                            <input type="date" name="dob" value="<%= dobFormatted %>" disabled max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                        </div>
                        <div class="input-container">
                            <label>Email:</label>
                            <input type="email" name="email" value="<%= user.getMail() %>" disabled>
                            <input type="hidden" name="oldEmail" value="<%= user.getMail() %>">
                        </div>
                        <c:if test="${user.getRoleId() == 2}">                          
                            <div class="input-container">
                                <label>CV:</label>
                                <div style="margin-bottom: 8px;" id="linkCv">
                                    <a href="javascript:void(0);" id="download" style="text-decoration: none; color: #5d3fd3; font-weight: bold; padding-botton: 5px;" >Download CV</a>
                                </div>
                                <input type="file" name="cvFileInput" id="cvFileInput" style="display: none;" accept=".pdf, image/*" onchange="validateFileSize()">
                                <input type="hidden" name="cvFile" id="cvFile" style="display: none;" accept="*" value="<%= user.getCvPath() %>">
                            </div>
                        </c:if>
                        <div class="button-container">
                            <button type="button" class="button-save" id="editButton" onclick="window.location.href = 'editUser.jsp'">Edit Profile</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
