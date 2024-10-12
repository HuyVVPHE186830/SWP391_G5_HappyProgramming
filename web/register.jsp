<%-- 
    Document   : register
    Created on : Sep 20, 2024, 8:31:11 AM
    Author     : Huy Võ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .signup-form {
                background-color: #fff;
                border-radius: 25px;
                display: flex;
                width: 800px;
                box-shadow: 0 0 10px #888;
                justify-content: space-between;
                overflow: hidden;
                height: 85vh;
            }
            .signup-form-left {
                margin: auto 10px;
                width: 50%;
            }
            .signup-form-left h2 {
                font-size: 2rem;
                margin: 0 0 20px 0;
                text-align: center;
            }
            form {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }
            input {
                padding: 10px;
                font-size: 0.8rem;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
                width: 70%;
                margin: 0 auto;
                font-family: Arial, sans-serif;
            }

            .nameInput {
                padding: 10px;
                font-size: 0.8rem;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
                width: 46%;
            }

            .name-container {
                display: flex;
                justify-content: space-between;
                width: 73%;
                margin: 0 auto;

            }

            .role-container {
                display: flex;
                justify-content: space-around;
                width: 78%;
                margin: 0 auto;
            }

            .role-box {
                display: flex;
                flex-direction: row;
                align-items: center;
                justify-content: center;
                width: 46%;
                height: 70px;
                border-radius: 10px;
                cursor: pointer;
                background-color: #eeeded;
                border: 2px solid transparent;
                transition: border 0.3s ease, background-color 0.3s ease;
            }
            .role-box .icon {
                font-size: 2rem;
                margin-bottom: 10px;
            }

            .role-box p {
                font-size: 0.8rem;
                margin: 0;
            }

            .icon-mentee::before {
                content: "\1F468\200D\1F393";
            }

            .icon-mentor::before {
                content: "\1F9D1\200D\1F3EB";
            }

            .role-box.shrink {
                width: 30%;
                height: 100px;
                transition: all 0.3s ease;
            }

            #mentor-info {
                margin-top: 20px;
                transition: all 0.3s ease;
            }

            .button-container {
                display: flex;
                justify-content: space-between;
                width: 70%;
                margin: 0 auto;
            }


            .button-signup {
                background-color: #5d3fd3;
                color: #fff;
                padding: 10px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
                font-size: 1rem;
                width: 80%;
                font-weight: bold;
                transition: all 0.3s ease;
            }
            .button-signup:hover {
                background-color: #452cbf;
            }

            #backButton {
                background-color: #fff;
                color: #5d3fd3;
                padding: 10px;
                border: solid 2px #5d3fd3;
                cursor: pointer;
                border-radius: 5px;
                font-size: 1rem;
                width: 16%;
                font-weight: bold;
                transition: all 0.3s ease;
            }
            .button-signup:hover {
                background-color: #452cbf;
            }

            .signup-form-right {
                background-color: #5d3fd3;
                color: #fff;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                width: 50%;
                border-bottom-left-radius: 70px;
                border-top-left-radius: 70px;
            }
            .signup-form-right h2 {
                font-size: 2rem;
                margin-bottom: 0px;
            }
            .signup-form-right p {
                margin: 20px 0;
                text-align: center;
                max-width: 250px;
            }


            .button-signin {
                background-color: #fff;
                color: #5d3fd3;
                padding: 10px 20px;
                border-radius: 5px;
                border: 2px solid #fff;
                transition: all 0.3s ease;
                font-weight: bold;
                text-decoration: none;
            }
            .button-signin:hover {
                background-color: #5d3fd3;
                color: #fff;
            }
            .hidden {
                display: none;
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
        </style>

    </head>
    <body>
        <script>
            function selectRole(role) {
                document.querySelector('.role-container').classList.add('hidden');
                const cvInput = document.getElementById('cvContainer');
                const registerForm = document.getElementById('registerForm');
                if (role === 'mentor') {
                    registerForm.enctype = 'multipart/form-data';
                    cvInput.classList.remove('hidden');
                    cvInput.required = true;
                } else {
                    registerForm.enctype = '';
                    cvInput.classList.add('hidden');
                    cvInput.required = false;
                }

                document.getElementById('selectedRole').value = role;
                document.getElementById('registrationForm').classList.remove('hidden');
            }

            function goBack() {
                document.querySelector('.role-container').classList.remove('hidden');
                document.getElementById('registrationForm').classList.add('hidden');
                document.getElementById('selectedRole').value = '';
            }

            document.addEventListener("DOMContentLoaded", function () {
                const dobInput = document.querySelector('input[name="dob"]');

                dobInput.addEventListener('focus', function () {
                    this.type = 'date';
                });

                dobInput.addEventListener('blur', function () {
                    if (!this.value) {
                        this.type = 'text';
                    }
                });

                const registerForm = document.getElementById('registerForm');
                if (registerForm) {
                    registerForm.addEventListener('submit', function (event) {
                        if (!validateNoSpacesOnly() || !validateFileSize()) {
                            event.preventDefault();
                        }
                    });
                }
            });
//
//            function validateNoSpacesOnly() {
//                const inputs = document.querySelectorAll('input[type="text"], input[type="password"]');
//                let valid = true;
//
//                inputs.forEach(input => {
//                    const trimmedValue = input.value.trim();
//                    if (trimmedValue === "") {
//                        valid = false;
//                        input.value = "";
//                    } else {
//                        input.value = trimmedValue;
//                    }
//                });
//
//                if (!valid) {
//                    alert("Fields cannot be empty or contain only spaces.");
//                }
//
//                return valid;
//            }

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

        <c:set var="showRegistrationForm" value="${empty sessionScope.error and empty sessionScope.note}" />

        <div class="signup-form">
            <div class="signup-form-left">
                <h2>Sign Up</h2>
                <div class="role-container <c:if test="${!showRegistrationForm}">hidden</c:if>">
                        <div class="role-box" id="menteeBox" onclick="selectRole('mentee')">
                            <div class="icon icon-mentee"></div>
                            <p>Mentee</p>
                        </div>
                        <div class="role-box" id="mentorBox" onclick="selectRole('mentor')">
                            <div class="icon icon-mentor"></div>
                            <p>Mentor</p>
                        </div>
                    </div>
                    <div id="registrationForm" class="<c:if test="${showRegistrationForm}">hidden</c:if>">
                        <form id="registerForm" action="register" method="post">
                            <div class="name-container">
                                <input class="nameInput" type="text" placeholder="First Name" name="firstName" required>
                                <input class="nameInput" type="text" placeholder="Last Name" name="lastName" required>
                            </div>
                            <input type="text" placeholder="Username" name="username" required>
                            <input type="password" placeholder="Password" name="password" required>
                            <input type="password" placeholder="Re-Enter Password" name="repassword" required>
                            <input type="text" name="dob" placeholder="Date Of Birth" required onfocus="(this.type = 'date')" max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                        <input type="email" placeholder="Email" name="email" required>
                        <input type="hidden" name="role" id="selectedRole" value="" required>
                        <input type="text" id="cvContainer" class="hidden" name="cv" accept=".pdf, image/*" onfocus="(this.type = 'file')" placeholder="Your CV (<5MB)" onchange="validateFileSize()">

                        <c:if test="${not empty sessionScope.error}">
                            <div class="error-message">
                                ${sessionScope.error}
                            </div>
                        </c:if>
                        <% session.removeAttribute("error"); %>

                        <c:if test="${not empty sessionScope.note}">
                            <div class="success-message">
                                ${sessionScope.note}
                            </div>
                        </c:if>
                        <% session.removeAttribute("note"); %>

                        <div class="button-container">
                            <button id="backButton" onclick="goBack()">
                                <i class="fas fa-arrow-left"></i>
                            </button>
                            <button type="submit" class="button-signup" id="signUpButton">SIGN UP</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="signup-form-right">
                <h2>Walah!</h2>
                <p>Already have an account</p>
                <a href="login.jsp" class="button-signin">SIGN IN</a>
            </div>
        </div>
    </body>
</html>
