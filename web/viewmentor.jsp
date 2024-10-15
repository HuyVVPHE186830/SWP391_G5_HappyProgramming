<%-- 
    Document   : viewmentor
    Created on : Oct 8, 2024, 9:43:29 PM
    Author     : ThuanNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
                margin: 20px 0;
            }

            .content-left {
                flex: 0 0 30%;
                text-align: center;
            }

            .mentor-image-icon1 {
                width: 100%;
                height: auto;
                max-width: 400px;
                box-shadow: 1px 1px 5px #ccc;
            }

            .detailMentor {
                margin-left: 30px;
                font-style: italic;
                margin-top: 30px;
                font-weight: 400;
                border-left: 1px solid black;
                padding: 5px;
            }

            .mentor-cards {
                gap: 20px;
                margin-bottom: 30px;
            }

            .mentor-card {
                width: 270px;
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
                text-align: center;
                height: 500px;
                overflow: hidden;
            }

            .mentor-card:hover {
                transform: translateY(-5px);
                text-decoration: none;
            }

            .mentor-image-icon {
                width: 100%;
                height: 300px;
                object-fit: cover;
                margin-bottom: 15px;
            }

            .content-right {
                flex: 1;
                padding-left: 20px;
            }
            
            .link-2 {
                color: #000;
                font-style: italic;
                font-weight: 400
            }
            
            .link-2:hover {
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- DESCRIPTION -->
        <div class="description">
            <c:if test="${not empty requestScope.mentorDetail}">
                <c:set var="mD" value="${requestScope.mentorDetail}"/>
                <c:set var="cM" value="${requestScope.courseOfMentor}"/>
                <c:set var="cT" value="${requestScope.thisCate}"/>
                <h6><a href="home" class="link">Home</a> <span>></span> <a href="viewcourse?courseId=${cM.courseId}" class="link">${cM.courseName}</a> <span>></span> <a href="viewCourseMentor?courseId=${cM.courseId}" class="link">List Mentors of ${cM.courseName}</a> <span>></span> Mentor ${mD.lastName} ${mD.firstName}</h6>
                <div class="content-middle">
                    <div class="content-left">
                        <img class="mentor-image-icon1" alt="" src="data:image/jpeg;base64, ${mD.avatarPath}">
                    </div>
                    <div class="content-right">
                        <h2>${mD.lastName} ${mD.firstName}</h2>
                        <h3>Course: ${cM.courseName} <a href="allCourse?search=category&categoryId=${cT.categoryId}" class="link-2">(${cT.categoryName})</a></h3>
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
            <c:if test="${not empty requestScope.otherCourseMentor}">
                <h5 class="detailMentor">
                    In addition to the courses mentioned, this mentor has also excelled in various other fields and has been an integral part of our platform. Here are some other courses this mentor also teaches:
                    <c:forEach var="oC" items="${requestScope.otherCourseMentor}" varStatus="status">
                        <a href="viewcourse?courseId=${oC.courseId}" class="link" style="font-weight: 500">${oC.courseName}</a>
                        <c:if test="${status.index == requestScope.otherCourseMentor.size() - 2}"> and </c:if>
                        <c:if test="${!status.last && status.index != requestScope.otherCourseMentor.size() - 2}">, </c:if>
                    </c:forEach>
                    <br>
                    ${mD.lastName} ${mD.firstName} has been our mentor since <span style="font-weight: 500"><fmt:formatDate value="${mD.createdDate}" pattern="dd/MM/yyyy"/></span>. Over the years, this mentor has contributed significantly to the growth and development of our community. With a passion for teaching and guiding, ${mD.lastName} has helped numerous learners achieve their goals. Their dedication and expertise are reflected in the wide range of courses they provide. 
                    <br>
                    Whether you're a beginner or an advanced learner, this mentor has something valuable to offer in each course.
                </h5>
            </c:if>

            <!-- OTHER MENTOR OF THIS COURSE -->
            <c:if test="${not empty requestScope.otherMentor}">
                <c:set var="cM" value="${requestScope.courseOfMentor}"/>
                <h2 class="list-mentor">Other Mentor Of This Course</h2>
                <div class="mentor-cards">
                    <c:forEach items="${requestScope.otherMentor}" var="oM">
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
