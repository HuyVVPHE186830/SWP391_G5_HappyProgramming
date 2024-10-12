<%-- 
    Document   : viewcourse
    Created on : Sep 21, 2024, 3:40:46 PM
    Author     : ThuanNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="CSS/bootstrap.min.css" rel="stylesheet">
<link href="CSS/home.css" rel="stylesheet">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>viewcourse</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/viewcourse.css" rel="stylesheet">
    </head>
    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- DESCRIPTION -->
        <div class="description">
            <c:if test="${not empty sessionScope.courseDetail}">
                <c:set var="cD" value="${sessionScope.courseDetail}"/>
                <h6><a href="home" class="link">Home</a> <span>></span> ${cD.courseName}</h6>
                <c:set var="count" value="${0}"/>
                <h2>${cD.courseName}</h2>
                <h3>Description:</h3>
                <p>${cD.courseDescription}</p>
                <c:if test="${not empty sessionScope.mentorThisCourse}">
                    <c:forEach items="${sessionScope.mentorThisCourse}" var="m">
                        <c:set var="count" value="${count + 1}"/>
                    </c:forEach>
                </c:if>
                <h3>Number of Mentor</h3>
                <h4 class="stat-number">${count}</h4>
                <a href="viewCourseMentor?courseId=${cD.courseId}&orderby=${"default"}" class="button-enroll">View</a>
            </c:if>
        </div>

        <!-- SAME COURSE -->
        <c:if test="${not empty sessionScope.sameCourse}">
            <h2 class="list-mentor">
                <c:set var="hasPrinted" value="false" />
                Same
                <c:forEach items="${sessionScope.category}" var="ca">
                    <c:forEach items="${sessionScope.sameCateId}" var="cId">
                        <c:if test="${cId == ca.categoryId}">
                            <c:if test="${hasPrinted}">
                                , 
                            </c:if>
                            ${ca.categoryName}
                            <c:set var="hasPrinted" value="true" />
                        </c:if>
                    </c:forEach>
                </c:forEach>
                Course
            </h2>
            <div class="same-course-cards-wrapper">
                <div class="same-course-cards">
                    <c:forEach items="${sessionScope.sameCourse}" var="sC">
                        <a href="viewcourse?courseId=${sC.courseId}" class="same-course-card">
                            <h3>${sC.courseName}</h3>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <!-- OTHER COURSE -->
        <c:if test="${not empty sessionScope.otherCourse}">
            <h2 class="list-mentor">Other Courses</h2>
            <div class="same-course-cards-wrapper">
                <div class="same-course-cards">
                    <c:forEach items="${sessionScope.otherCourse}" var="oC">
                        <a href="viewcourse?courseId=${oC.courseId}" class="same-course-card">
                            <h3>${oC.courseName}</h3>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <!-- OTHER CATEGORY -->
        <c:if test="${not empty sessionScope.category}">
            <h2 class="list-category">Other Category You Can Discover</h2>
            <div class="category-cards-wrapper">
                <div class="category-cards">
                    <c:forEach items="${sessionScope.othercategory}" var="c" varStatus="status">
                        <a href="allCourse?search=category&categoryId=${c.categoryId}" class="category-card" style="display: ${status.index < 4 ? 'block' : 'none'};">
                            <h3>${c.categoryName}</h3>
                        </a>
                    </c:forEach>
                    <a href="#" class="more-categories-button" id="more-categories-button" onclick="showMoreCourses(event)">More Categories</a>
                </div>
            </div>


            <script>
                let categoriesShown = 4;

                function showMoreCourses(event) {
                    event.preventDefault();
                    const allCategories = document.querySelectorAll('.category-card');
                    const totalCategories = allCategories.length;
                    const moreCategoriesButton = document.getElementById('more-categories-button');

                    for (let i = categoriesShown; i < categoriesShown + 4 && i < totalCategories; i++) {
                        allCategories[i].style.display = 'block';
                    }
                    categoriesShown += 4;

                    if (categoriesShown >= totalCategories) {
                        moreCategoriesButton.style.display = 'none';
                    }
                }
            </script>
        </c:if>

        <!-- MENTOR LIST -->
        <h2 class="list-mentor">Some Of Our Best Mentors For This Course</h2>
        <div class="mentor-cards">
            <c:if test="${not empty sessionScope.mentorThisCourse}">
                <c:forEach items="${sessionScope.mentorThisCourse}" var="m">
                    <div class="mentor-card">
                        <img class="mentor-image-icon" alt="" src="data:image/jpeg;base64, ${m.avatarPath}">
                        <div class="mentor-body">
                            <div class="mentor-text">
                                <div style="color: black">${m.lastName} ${m.firstName}</div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${empty sessionScope.mentorThisCourse}">
                <h4>This Course Does Not Have Mentor Yet!</h4>
            </c:if>
        </div>

        <!-- FOOTER -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>
