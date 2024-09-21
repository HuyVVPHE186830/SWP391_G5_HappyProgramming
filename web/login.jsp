<%-- 
    Document   : signin
    Created on : Sep 16, 2024, 3:32:12 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }


            .signin-form {
                background-color: #fff;
                border-radius: 25px;
                display: flex;
                width: 800px;
                box-shadow: 0 0 10px #888;
                justify-content: space-between;
                overflow: hidden;
                height: 70vh;
            }

            .signin-form-left {
                margin: auto 10px;
                width: 50%;
            }

            .signin-form-left  h2 {
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

            input:active {
                border: none;
            }

            .button-signin {
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

            .button-signin:hover {
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

            .signin-form-right {
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

            .signin-form-right h2 {
                font-size: 2rem;
                margin-bottom: 0px;
            }

            .signin-form-right p {
                margin: 20px 0;
                text-align: center;
                max-width: 250px;
            }

            .button-signup {
                background-color: #fff;
                color: #5d3fd3;
                padding: 10px 20px;
                border-radius: 5px;
                border: 2px solid #fff;
                transition: all 0.3s ease;
                font-weight: bold;
                text-decoration: none;
            }

            .button-signup:hover {
                background-color: #5d3fd3;
                color: #fff;
            }

            .error-message {
                color: red;
                font-weight: bold;
                text-align: center;
                font-size: 12px;
            }
        </style>
    </head>
    <body>
        <div class="signin-form">
            <div class="signin-form-left">
                <h2>Sign In</h2>
                <form action="login" method="post">
                    <input type="text" placeholder="Username" name="username" required>
                    <input type="password" placeholder="Password" name="password" required>
                    <a href="forgetPass.jsp" class="forgot">Forgot Your Password?</a>
                    <c:if test="${sessionScope.error != null}">
                        <div class="error-message">
                            ${error}
                        </div>
                    </c:if>
                    <% session.removeAttribute("error"); %>
                    <button type="submit" class="button-signin">SIGN IN</button>
                </form>
            </div>

            <div class="signin-form-right">
                <h2>Hello, New Member!</h2>
                <p>Want to join us to use all of site features</p>
                <a href="signup.jsp" class="button-signup">SIGN UP</a>
            </div>
        </div>
    </body>
</html>
