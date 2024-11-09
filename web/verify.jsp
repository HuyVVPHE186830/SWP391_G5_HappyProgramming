<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <title>Change Password</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0; /* Bỏ margin mặc định */
            }

            .changepass-form {
                background-color: #fff;
                border-radius: 25px;
                width: 500px;
                box-shadow: 0 0 10px #888;
                overflow: hidden;
                height: auto; /* Điều chỉnh chiều cao tự động */
                padding: 20px; /* Thêm padding để tạo khoảng cách */
            }

            .changepass-form-left {
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
                align-items: center; /* Căn giữa tất cả các phần tử */
                gap: 15px;
            }

            input {
                padding: 10px;
                font-size: 0.8rem;
                border: none;
                border-radius: 5px;
                background-color: #eeeded;
                width: 70%;
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
                font-weight: bold;
                transition: all 0.3s ease;
            }

            .button-changepass:hover {
                background-color: #452cbf;
            }

            .notification {
                background-color: #4caf50; /* Màu xanh lá cho thông báo thành công */
                color: white;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                font-size: 16px;
                margin: 20px auto; /* Căn giữa thông báo */
                width: 70%; /* Chiều rộng thông báo */
                opacity: 0;
                transform: scale(0.8) translateY(20px);
                transition: opacity 0.3s ease, transform 0.5s ease;
            }

            .notification.hidden {
                display: none; /* Ẩn thông báo khi không cần thiết */
            }

            .forgot {
                font-size: 0.9rem;
                font-weight: 500;
                color: #888;
                text-decoration: none;
                margin-top: 20px;
                display: inline-block;
                text-align: center; /* Căn giữa liên kết */
            }

            .forgot:hover {
                color: #5d3fd3; /* Màu hover */
            }
        </style>
        <script>
            function validateForm() {
                var curPass = document.getElementById("curPass").value;
                var newPassword = document.getElementById("newPass").value;
                var confirmPassword = document.getElementById("confirmPass").value;
                if (curPass === newPassword) {
                    alert("New password is the same as current password.");
                    return false;
                }
                if (newPassword !== confirmPassword) {
                    alert("New passwords do not match.");
                    return false;
                }
                return true;
            }

            function showNotification(message, type) {
                const notification = document.getElementById('notification');
                notification.textContent = message;
                notification.classList.remove('hidden');
                notification.style.backgroundColor = type === "error" ? "#f44336" : "#4caf50"; // Đổi màu thông báo theo loại

                // Hiển thị thông báo
                setTimeout(() => {
                    notification.style.opacity = '1';
                    notification.style.transform = 'translateY(0)';
                }, 100);

                // Tự động ẩn thông báo sau 3 giây
                setTimeout(() => {
                    notification.style.opacity = '0';
                    notification.style.transform = 'scale(0.8) translateY(20px)';
                    setTimeout(() => {
                        notification.classList.add('hidden');
                    }, 500);
                }, 3000);
            }

            document.addEventListener("DOMContentLoaded", function () {
                const sessionMessage = '<%= request.getAttribute("success") != null ? request.getAttribute("success") : "" %>';
                const sessionError = '<%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>';

                if (sessionMessage) {
                    showNotification(sessionMessage, "success");
                } else if (sessionError) {
                    showNotification(sessionError, "error");
                }
            });
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="changepass-form">
            <div class="changepass-form-left">
                <h2>Change Password</h2>
                <form action="verify" method="post" onsubmit="return validateForm();">
                    <input type="password" placeholder="Enter password you got from email" id="verificationCode" name="passWord" required>
                    <input type="password" placeholder="New password" id="newPass" name="newPass" required>
                    <input type="password" placeholder="Confirm new password" id="confirmPass" name="confirmPass" required>
                    <button type="submit" class="button-changepass">CHANGE PASSWORD</button>
                </form>

                <!-- Notification Container -->
                <div id="notification" class="notification hidden"></div>

                <!-- Thêm ô Change Later -->
                <div style="text-align: center; margin-top: 20px;">
                    <a href="login.jsp" class="forgot">Change Later</a>
                </div>
            </div>
        </div>
    </body>
</html>