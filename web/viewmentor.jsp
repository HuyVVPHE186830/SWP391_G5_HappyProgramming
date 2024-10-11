<%-- 
    Document   : viewmentor
    Created on : Oct 8, 2024, 9:43:29 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/viewcourse.css" rel="stylesheet">
        <style>
            .content-middle {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin: 20px 0;
            }

            .content-left {
                flex: 0 0 30%;
                text-align: center;
            }

            .mentor-image-icon {
                width: 100%;
                height: auto;
                max-width: 200px;
                box-shadow: 1px 1px 5px black;
            }

            .content-right {
                flex: 1;
                padding-left: 20px;
            }
        </style>
    </head>
    <body>
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- DESCRIPTION -->
        <div class="description">
            <c:if test="${not empty sessionScope.mentorDetail}">
                <c:set var="mD" value="${sessionScope.mentorDetail}"/>
                <c:set var="cM" value="${sessionScope.courseOfMentor}"/>
                <h6><a href="home" class="link">Home</a> <span>></span> <a href="viewcourse?courseId=${cM.courseId}" class="link">${cM.courseName}</a> <span>></span> <a href="viewCourseMentor?courseId=${cM.courseId}" class="link">List Mentors of ${cM.courseName}</a> <span>></span> Mentor ${mD.lastName} ${mD.firstName}</h6>
                <div class="content-middle">
                    <div class="content-left">
                        <img class="mentor-image-icon" alt="" src="data:image/jpeg;base64, ${mD.avatarPath}">
                    </div>
                    <div class="content-right">
                        <h2>${mD.lastName} ${mD.firstName}</h2>
                        <h3>Course: ${cM.courseName}</h3>
                        <h3>Email: ${mD.mail}</h3>
                        <p>Date Of Birth: ${mD.dob}</p>
                    </div>
                </div>
                <form action="#">
                    <a href="#?courseId=${cM.courseId}" class="button-enroll">Enroll</a>
                </form>
            </c:if>
        </div>

        <!-- OTHER MENTOR OF THIS COURSE -->

        <!-- FOOTERS -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>
