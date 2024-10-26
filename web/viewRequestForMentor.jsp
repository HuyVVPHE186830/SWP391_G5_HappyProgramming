<%-- 
    Document   : viewRequestForMentor
    Created on : Oct 26, 2024, 1:16:17 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="profile-form">
            <form action="userProfile" method="post">
                <input type="text" name="courseId" value="">
                <input type="text" name="requestReason" value="">
                <input type="text" name="usernameHidden" value="">
                <div class="button-container">
                    <button type="button" class="button-save" id="editButton" onclick="window.location.href = 'editUser.jsp'">Edit Profile</button>
                </div>
            </form>
        </div>
    </body>
</html>
