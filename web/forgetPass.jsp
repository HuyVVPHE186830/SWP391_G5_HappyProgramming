<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            justify-content: center;
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

        input[type="text"], input[type="password"] {
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
            width: 20%;
            font-weight: bold;
            transition: all 0.3s ease;
        }

        .button-changepass:hover {
            background-color: #452cbf;
        }

        .input-group {
            display: flex;
            justify-content: center; /* Căn giữa nút */
            width: 100%; /* Chiếm toàn bộ chiều rộng */
        }

        .notification {
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #4caf50; /* Màu xanh lá cây cho thông báo thành công */
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

        .notification.hidden {
            display: none; /* Ẩn thông báo khi không cần thiết */
        }

        .message {
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
</head>
<body>
    <div class="changepass-form">
        <div class="changepass-form-left">
            <h2>Forget Password</h2>
            <form action="resetPass" method="post">
                <input type="text" placeholder="Your Username" id="username" name="username" required>
                <input type="text" placeholder="Your Email" id="email" name="email" required>
                <div class="input-group">
                    <button type="submit" class="button-changepass">SEND</button>
                </div>
            </form>
            <br>
            <!-- Notification Container -->
            <div id="notification" class="notification hidden"></div>
            <script>
                const sessionMessage = '<%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>';
                const sessionError = '<%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>';
                
                if (sessionMessage) {
                    showNotification(sessionMessage, "success");
                } else if (sessionError) {
                    showNotification(sessionError, "error");
                }

                function showNotification(message, type) {
                    const notification = document.getElementById('notification');
                    notification.textContent = message;
                    notification.classList.remove('hidden');
                    notification.style.backgroundColor = type === "error" ? "#f44336" : "#4caf50";

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
            </script>
        </div>
    </div>
</body>
</html>