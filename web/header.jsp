<%-- 
    Document   : header
    Created on : Sep 18, 2024, 11:32:17 PM
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
            /* HEADER */
            .content-header {
                background-color: transparent;
                position: fixed;
                transition: all 0.3s ease;
                z-index: 100000;
                width: 100%;
                top: 0;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 5px;
                background-color: #edf2fa;
                gap: 30%;
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

            .menu li a {
                text-decoration: none;
                color: black;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .menu li a:hover {
                color: #007bff;
            }

            .button-signup {
                background-color: #fff;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 14px;
                transition: all 0.3s ease;
            }

            .button-signup:hover {
                background-color: #edf2fa;
            }

            .button-signin {
                background-color: #007bb5;
                border: 1px solid #ccc;
                border-radius: 8px;
                color: white;
                font-size: 14px;
                transition: all 0.3s ease;
            }

            .button-signin:hover {
                background-color: #002752;
            }
        </style>
    </head>
    <body>
        <!-- HEADER -->
        <div class="content-header">

            <!-- LOGO -->
            <div class="logo">
                <span>???</span>
            </div>

            <!-- MIDDLE -->
            <ul class="menu">
                <li><a href="#">Courses</a></li>
                    <c:if test="${sessionScope.user != null}">
                    <li><a href="#">My Courses</a></li>
                    <li><a href="#">Chat</a></li>
                    </c:if>
            </ul>

            <!-- USER AVATAR -->   
            <div>
                <c:if test="${sessionScope.user != null}">
                    <c:set var="u" value="${sessionScope.user}"/>
                    <h2>${u.username}</h2>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <input type="submit" value="Sign Up" class="button-signup">
                    <input type="submit" value="Sign In" class="button-signin">
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