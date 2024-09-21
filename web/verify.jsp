<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .changepass-form {
                background-color: #fff;
                border-radius: 25px;
                display: flex;
                width: 500px;
                box-shadow: 0 0 10px #888;
                justify-content: space-between;
                overflow: hidden;
                height: 60vh;
            }

            .changepass-form-left {
                margin: auto 10px;
                width: 100%;
            }

            .changepass-form-left h2 {
                font-size: 2rem;
                margin-bottom: 20px;
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
            }

            .button-changepass {
                background-color: #5d3fd3;
                color: #fff;
                padding: 10px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
                font-size: 1rem;
                width: 70%;
                margin: 0 auto;
                font-weight: bold;
                transition: all 0.3s ease;
            }

            .button-changepass:hover {
                background-color: #452cbf;
            }

            .forgot {
                font-size: 0.9rem;
                font-weight: 500;
                color: #888;
                text-align: center;
                text-decoration: none;
                width: 150px;
                margin: 0 auto;
            }

            .success-message {
                color: green;
                font-weight: bold;
                text-align: center;
                font-size: 14px;
            }

            .error-message {
                color: red;
                font-weight: bold;
                text-align: center;
                font-size: 14px;
            }

            .success{
                color: #4CAF50; /* Màu xanh lục cho thông báo */
                font-size: 0.9rem;
                text-align: center; /* Căn giữa chữ */
                margin: 10px 0; /* Khoảng cách trên và dưới */
            }

            .error {
                color: red; /* Màu cho lỗi */
                font-size: 0.9rem;
                text-align: center; /* Căn giữa chữ */
                margin: 10px 0; /* Khoảng cách trên và dưới */
            }
        </style>
        <script>
            function validateForm() {
                var curPass = document.getElementById("curPass").value;
                var newPassword = document.getElementById("newPass").value;
                var confirmPassword = document.getElementById("confirmPass").value;
                if (curPass == newPassword) {
                    alert("New password is the same with current password.");
                    return false;
                }
                if (newPassword !== confirmPassword) {
                    alert("New passwords do not match.");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <div class="changepass-form">
            <div class="changepass-form-left">
                <h2>Change Password</h2>
                <form action="verify" method="post">
                    <input type="password" placeholder="Enter verification code" id="verificationCode" name="verificationCode" required>
                    <input type="password" placeholder="New password" id="newPass" name="newPass" required>
                    <input type="password" placeholder="Confirm new password" id="confirmPass" name="confirmPass" required>
                    <button type="submit" class="button-changepass">CHANGE PASSWORD</button>
                    <c:if test="${not empty success}">
                        <div class="message">${success}</div>
                    </c:if>

                    <!-- Hiển thị lỗi nếu có -->
                    <c:if test="${not empty error}">
                        <div class="error">${error}</div>
                    </c:if>
                </form>
            </div>

        </div>
    </body>
</html>
