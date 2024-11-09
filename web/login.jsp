<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign In</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
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

            .signin-form-left h2 {
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

            .social-signin {
                display: flex;
                justify-content: center;
                gap: 20px;
                margin-top: 10px;
            }

            .social-signin i {
                font-size: 2rem;
                color: #5d3fd3;
                cursor: pointer;
                transition: transform 0.3s ease, color 0.3s ease;
            }

            .social-signin i:hover {
                transform: scale(1.1);
                color: #452cbf;
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
                font-size: 1.6rem;
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

            /* Notify */
            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: red;
                color: white;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                font-size: 16px;
                font-family: Arial, sans-serif;
                z-index: 1000;
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
        <!-- Notification Container -->
        <div id="notification" class="notification hidden"></div>
        <script>
            const sessionMessage = '<%= session.getAttribute("error") != null ? session.getAttribute("error") : "" %>';
            if (sessionMessage) {
                showNotification(sessionMessage);
            <% session.removeAttribute("error"); %>
            }
            function showNotification(message) {
                const notification = document.getElementById('notification');
                notification.textContent = message;
                notification.classList.remove('hidden');

                // Make the notification visible
                setTimeout(() => {
                    notification.style.opacity = '1';
                    notification.style.transform = 'translateY(0)';
                }, 100); // Small delay for smooth animation

                // Automatically hide the notification after 3 seconds
                setTimeout(() => {
                    notification.style.opacity = '0';
                    notification.style.transform = 'scale(0.8) translateY(20px)';
                    setTimeout(() => {
                        notification.classList.add('hidden');
                    }, 500); // Allow animation to finish before hiding
                }, 3000);
            }
        </script>
        <div class="signin-form">
            <div class="signin-form-left">
                <h2>Sign In</h2>
                <form action="login" method="post">
                    <input type="text" placeholder="Username" name="username" required>
                    <input type="password" placeholder="Password" name="password" required>
                    <a href="forgetPass.jsp" class="forgot">Forgot Your Password?</a>


                    <% session.removeAttribute("error"); %>
                    <button type="submit" class="button-signin">SIGN IN</button>

                    <!-- Social Sign-in Icons -->
                    <div class="social-signin">
                        <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:9999/HappyProgramming/login&response_type=code&client_id=281001469062-sdlqscanrn8urvdd8at2540bq0u04mv2.apps.googleusercontent.com&approval_prompt=force">
                            <i class="fa-brands fa-google"></i>
                        </a>
                        <!--                        <i class="fa-brands fa-facebook"></i>
                                                <i class="fa-brands fa-apple"></i>-->
                    </div>
                </form>
            </div>

            <div class="signin-form-right">
                <a href="homeguest.jsp">
                    <img src="img/logowhite.png" style="margin-left:10px;" width="120px" alt="Back to Home"/>
                </a>
                <p style="font-weight: bolder; width: 700px;">Want to use all of features?</p>
                <a href="register.jsp" class="button-signup">SIGN UP</a>
            </div>
        </div>
    </body>
</html>
