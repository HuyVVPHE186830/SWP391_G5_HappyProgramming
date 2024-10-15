<%-- 
    Document   : viewmentor
    Created on : Oct 8, 2024, 9:43:29 PM
    Author     : ThuanNV
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

            .detailMentor {
                margin-left: 30px;
                font-style: italic;
                margin-top: 30px;
                font-weight: 400;
                border-left: 1px solid black;
                padding: 5px;
            }

            .mentor-card:hover {
                text-decoration: none;
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
                        <form action="requestScreen" method="post">
                            <input type="hidden" name="courseId" value="${cM.courseId}">
                            <input type="hidden" name="username" value="${sessionScope.user.username}">
                            <button type="submit" class="button-enroll">Enroll</button>
                        </form>
                    </div>
                </div>
            </c:if>

            <!-- MORE DETAILS -->
            <c:if test="${not empty sessionScope.otherCourseMentor}">
                <h5 class="detailMentor">Also This Mentor Has Other Courses Like 
                    <c:forEach var="oC" items="${sessionScope.otherCourseMentor}" varStatus="status">
                        <a href="viewcourse?courseId=${oC.courseId}" class="link">${oC.courseName}</a>
                        <c:if test="${status.index == sessionScope.otherCourseMentor.size() - 2}"> and </c:if>
                        <c:if test="${!status.last && status.index != sessionScope.otherCourseMentor.size() - 2}">, </c:if>
                    </c:forEach>
                </h5>
            </c:if>

            <!-- OTHER MENTOR OF THIS COURSE -->
            <c:if test="${not empty sessionScope.otherMentor}">
                <c:set var="cM" value="${sessionScope.courseOfMentor}"/>
                <h2 class="list-mentor">Other Mentor Of This Course</h2>
                <div class="mentor-cards">
                    <c:forEach items="${sessionScope.otherMentor}" var="oM">
                        <a href="viewMentor?userId=${oM.id}&courseId=${cM.courseId}" class="mentor-card"> 
                            <img class="mentor-image-icon" alt="" src="data:image/jpeg;base64, ${oM.avatarPath}">
                            <div class="mentor-body">
                                <div class="mentor-text">
                                    <div style="color: black">${oM.lastName} ${oM.firstName}</div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </c:if>
        </div>

        <!-- FOOTERS -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>
