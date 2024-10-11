<%-- 
    Document   : viewCourseMentor
    Created on : Oct 8, 2024, 9:12:48 PM
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
            .mentor-card:hover {
                text-decoration: none;
            }
        </style>
    </head>
    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- MENTOR LIST -->
        <div  class="description">
            <c:if test="${not empty sessionScope.mentorThisCourse}">
                <c:set var="cM" value="${sessionScope.courseOfMentor}"/>
                <h6><a href="home" class="link">Home</a> <span>></span> <a href="viewcourse?courseId=${cM.courseId}" class="link">${cM.courseName}</a> <span>></span> List Mentors of ${cM.courseName}</h6>
                <h2 class="list-mentor">Mentor Of This Course</h2>

                <!-- SORT -->
                <form id="orderbyForm" action="viewCourseMentor" method="post">
                    <select id="orderby" name="orderby" class="form-control">                                
                        <option value="default" ${sessionScope.order == "default" ? 'selected' : ''}>Default</option>
                        <option value="name" ${sessionScope.order == "name" ? 'selected' : ''}>Sort By Name</option>
                    </select>
                </form>

                <script>
                    document.getElementById('orderby').addEventListener('change', function () {
                        document.getElementById('orderbyForm').submit();
                    });
                </script>


                <!-- RATING -->

                <!-- CONTENT MENTOR -->
                <div class="mentor-cards">
                    <c:forEach items="${sessionScope.mentorThisCourse}" var="m">
                        <a href="viewMentor?userId=${m.id}&courseId=${cM.courseId}" class="mentor-card">
                            <img class="mentor-image-icon" alt="" src="data:image/jpeg;base64, ${m.avatarPath}">
                            <div class="mentor-body">
                                <div class="mentor-text">
                                    <div style="color: black">${m.lastName} ${m.firstName}</div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${empty sessionScope.mentorThisCourse}">
                <h4>This Course Does Not Have Mentor Yet!</h4>
            </c:if>
        </div>

        <!-- OTHER COURSES -->

        <!-- FOOTER -->
        <jsp:include page="footer.jsp"/>

    </body>
</html>
