<%-- 
    Document   : header
    Created on : Sep 18, 2024, 11:32:17 PM
    Author     : ThuanNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>

            /* HEADER */
            .content-header {
                background-color: white;
                position: fixed;
                transition: all 0.3s ease;
                z-index: 100000;
                width: 100%;
                top: 0;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 5px;
                gap: 30%;
            }

            .content-header.scrolled {
                background-color: white;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }


            .menu {
                text-align: center;
                display: flex;
                justify-content: center;
                align-items: center;
                list-style: none;
                padding-left: 0;
                margin: auto 0;
                gap: 20px;

            }

            .icon {
                color: black;
                font-size: 14px;
                font-weight: bold;
                transition: all 0.3s ease;
                text-decoration: none;
            }

            .icon:hover {
                color: black;
                text-decoration: none;
            }

            .menu li a {
                text-decoration: none;
                color: black;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .menu li a:hover {
                color: #6a1b9a;
            }

            .button-signup {
                background-color: transparent;
                border: 2px solid black;
                border-radius: 8px;
                color: black;
                font-size: 14px;
                font-weight: bold;
                transition: all 0.3s ease;
                width: 70px;
            }

            .button-signup:hover {
                background-color: #6a1b9a;
                color: white;
                border: 2px solid white;
            }

            .button-signin {
                background-color: transparent;
                border: 2px solid black;
                border-radius: 8px;
                color: black;
                font-size: 14px;
                font-weight: bold;
                transition: all 0.3s ease;
                width: 70px;
            }

            .button-signin:hover {
                background-color: #6a1b9a;
                color: white;
                border: 2px solid #ffffff;
            }

            .button-logout {
                font-size: 14px;
                color: black;
                transition: color 0.3s;
                font-weight: 500;
            }

            .button-logout:hover {
                text-decoration: none;
            }

            .user-dropdown {
                position: relative;
                display: inline-block;
            }

            .user-logo {
                color: black;
                cursor: pointer;
                text-decoration: none;
                font-weight: bold;
            }

            .user-logo:hover {
                text-decoration: none;
                color: black;
            }

            .avatar-image-mini {
                width: 30px;
                height: 30px;
                border-radius: 50%;
                object-fit: cover;
                border: 2px solid #ccc;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: white;
                box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.1);
                z-index: 1;
                min-width: 200px;
                border-radius: 8px;
                right: 0;
                overflow: hidden;
                padding: 5px;
            }

            .dropdown-content h3 {
                color: black;
                font-size: 18px;
                font-weight: bold;
                margin: 0;
                padding: 10px;
                text-align: center;
                border-bottom: 1px solid #ccc;
            }

            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
                font-weight: bold;
            }

            .dropdown-content a:hover {
                background-color: #f1f1f1;
            }

            .show {
                display: block;
            }
        </style>
    </head>
    <body>
        <!-- HEADER -->
        <div class="content-header">

            <!-- LOGO -->
            <div class="logo">
                <a href="home" class="icon"><span>WALAH</span></a>
            </div>

            <!-- MIDDLE -->
            <ul class="menu">
                <li><a href="allCourse">Courses</a></li>
                    <c:if test="${sessionScope.user != null}">
                    <li><a href="viewMyCourses">My Courses</a></li>
                    <li><a href="#">Chat</a></li>
                    <li><a href="viewblogs">Blog</a></li>
                </c:if>
            </ul>

            <!-- USER AVATAR -->   
            <div>
                <c:if test="${sessionScope.user != null}">
                    <c:set var="u" value="${sessionScope.user}"/>

                    <div class="user-dropdown">
                        <c:if test="${u.avatarPath != null}">
                            <a href="javascript:void(0);" class="user-logo" onclick="toggleDropdown()">
                                <img src="data:image/jpeg;base64, ${u.avatarPath}" alt="Avatar" class="avatar-image-mini">
                            </a>
                        </c:if>
                        <div id="userDropdown" class="dropdown-content">
                            <h3>${u.lastName} ${u.firstName}</h3>
                            <a href="userProfile"><i class="fas fa-user"></i> User Profile</a>
                            <a href="changePass.jsp"><i class="fas fa-lock"></i> Change Password</a>
                            <a href="logout.jsp"><i class="fas fa-sign-out-alt"></i> Log Out</a>
                        </div>
                    </div>
                </c:if>

                <script>
                    function toggleDropdown() {
                        var dropdown = document.getElementById("userDropdown");
                        dropdown.classList.toggle("show");
                    }
                </script>

                <c:if test="${sessionScope.user == null}">
                    <a href="register.jsp"><input type="submit" value="Sign Up" class="button-signup"></a>
                    <a href="login.jsp"><input type="submit" value="Sign In" class="button-signin"></a>
                    </c:if>  
            </div>

            <!-- SCOLL BAR FUNCTION -->    
            <script>
                window.addEventListener("scroll", function () {
                    var header = document.querySelector(".content-header");
                    header.classList.toggle("scrolled", window.scrollY > 0);
                });
            </script>
        </div>
    </body>
</html>
