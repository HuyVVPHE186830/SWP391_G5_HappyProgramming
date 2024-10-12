<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <title>Change Password</title>
        <style>
            .middle {
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

            .forgot:hover {
                text-decoration: none;
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
        </style>
        <script>
            function validateChangePassword() {
                const formValid = validateForm();
                const noSpacesOnlyValid = validateNoSpacesOnly();

                if (formValid && noSpacesOnlyValid) {
                    return true; 
                } else {
                    return false; 
                }
            }

            function validateForm() {
                var curPass = document.getElementById("curPass").value;
                var newPassword = document.getElementById("newPass").value;
                var confirmPassword = document.getElementById("confirmPass").value;
                if (curPass === newPassword) {
                    alert("New password is the same with current password.");
                    return false;
                }
                if (newPassword !== confirmPassword) {
                    alert("New passwords do not match.");
                    return false;
                }
                return true;
            }

            function validateNoSpacesOnly() {
                const inputs = document.querySelectorAll('input[type="password"]');
                let valid = true;

                inputs.forEach(input => {
                    const trimmedValue = input.value.trim();
                    if (trimmedValue === "") {
                        valid = false;
                        input.value = "";
                    } else {
                        input.value = trimmedValue;
                    }
                });

                if (!valid) {
                    alert("Fields cannot be empty or contain only spaces.");
                }

                return valid;
            }
        </script>
    </head>
    <body>
        <c:if test="${empty user}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <div class="middle">
            <div class="changepass-form">
                <div class="changepass-form-left">
                    <h2>Change Password</h2>
                    <form action="changePass" method="post" onsubmit="return validateChangePassword()">
                        <input type="hidden" value="${user.id}" name="id">
                        <input type="password" placeholder="Current password" id="curPass" name="curPass" required>
                        <input type="password" placeholder="New password" id="newPass" name="newPass" required>
                        <input type="password" placeholder="Confirm new password" id="confirmPass" name="confirmPass" required>
                        <a href="forgetPass.jsp" class="forgot">Forgot Your Password?</a>

                        <c:if test="${not empty succMsg}">
                            <div class="success-message">
                                ${succMsg}
                            </div>
                            <c:remove var="succMsg" scope="session"/>
                        </c:if>

                        <c:if test="${not empty failedMsg}">
                            <div class="error-message">
                                ${failedMsg}
                            </div>
                            <c:remove var="failedMsg" scope="session"/>
                        </c:if>

                        <button type="submit" class="button-changepass">CHANGE PASSWORD</button>
                    </form>
                </div>

            </div>
        </div>

    </body>
</html>
