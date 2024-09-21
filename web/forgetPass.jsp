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

        input[type="text"] {
            padding: 10px;
            font-size: 0.8rem;
            border: none;
            border-radius: 5px;
            background-color: #eeeded;
            width: 70%;
            margin: 0 auto;
        }

        .input-group {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        input[type="password"] {
            padding: 10px;
            font-size: 0.8rem;
            border: none;
            border-radius: 5px;
            background-color: #eeeded;
            width: 48%;
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

        .countdown {
            color: blue; /* Màu xanh cho dòng chữ đếm ngược */
            font-size: 0.8rem;
            text-align: center;
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
                <input type="text" placeholder="Your email" id="email" name="email" required>
                <div class="input-group">
                    <!--<input type="password" placeholder="Your verification code" id="verificationCode" name="verificationCode" required>-->
                    <button type="submit" class="button-changepass">SEND</button>
                </div>
            </form>
            </br>
            <!--<div id="countdown" class="countdown">Wait few second ...</div>-->
            <c:if test="${not empty message}">
                <div class="message">${message}</div>
            </c:if>
            
            <!-- Hiển thị lỗi nếu có -->
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
        </div>
    </div>
</body>
</html>