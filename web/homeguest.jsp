<%-- 
    Document   : homeguest
    Created on : Sep 20, 2024, 1:06:46 AM
    Author     : ThuanNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/home.css" rel="stylesheet">
    </head>
    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- MIDDLE -->
        <div class="content-middle">

            <!-- HEAD CONTENT MIDDLE -->
            <div class="header-content-middle">
                <!-- TEXT -->
                <div class="text-overlay">
                    <!-- TEXT -->
                    <h1 style="text-transform: capitalize">First time here with us?</h1>
                    <h1 style="margin-bottom: 20px; text-transform: capitalize">Explore our courses right away</h1>
                    <form action="allCourse" method="get" class="search-bar">
                        <input type="hidden" name="search" value="searchByName"/>
                        <input type="text" class="input-submit" placeholder="Search a course" name="keyword" id="keyword" oninput="checkInput()">
                        <input type="submit" class="button-submit" id="submit-btn" disabled value="Search">
                    </form>
                </div>
                <img src="img/banner.jpg" alt="alt"/>
            </div>
            <script>
                function checkInput() {
                    var keyword = document.getElementById('keyword').value.trim();
                    var submitBtn = document.getElementById('submit-btn');

                    if (keyword !== "") {
                        submitBtn.disabled = false;
                    } else {
                        submitBtn.disabled = true;
                    }
                }
            </script>

            <!-- CATEGORY -->
            <div style="background-color: #edf2fa">
                <div class="category-list row">
                    <c:forEach items="${sessionScope.category}" var="cate" begin="0" end="5">
                        <a href="allCourse?search=category&categoryId=${cate.categoryId}" class="col-md-5 category-card">
                            ${cate.categoryName}
                        </a>
                    </c:forEach>
                </div>
            </div>

            <!-- MOST PARTICIPANTS COURSE SLIDE -->
            <div class="best-course-list">
                <div class="best-course-heading">MOST PARTICIPANTS COURSE</div>
                <c:forEach items="${sortedCourses}" var="c" varStatus="status" begin="0" end="3">
                    <a href="viewcourse?courseId=${c.courseId}" class="best-course-card">
                        <h3 class="best-course-title">${c.courseName}</h3> 
                        <div class="ranking">
                            <c:choose>
                                <c:when test="${status.index == 0}">
                                    <span class="gold-rank">1</span>
                                </c:when>
                                <c:when test="${status.index == 1}">
                                    <span class="silver-rank">2</span>
                                </c:when>
                                <c:when test="${status.index == 2}">
                                    <span class="bronze-rank">3</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="default-rank">${status.index + 1}</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </a>
                </c:forEach>
            </div>

            <!-- DASHBOARD -->
            <c:set var="countMentor" value="${sessionScope.countMentor}" />
            <c:set var="countMentee" value="${sessionScope.countMentee}" />
            <c:set var="countCourse" value="${sessionScope.countCourse}" />
            <div class="statistics">
                <h2 class="statistics-title">SOME STATISTICS ABOUT US</h2>
                <div class="statistics-cards">
                    <div class="stat-card">
                        <i class="icon fa fa-users"></i>
                        <p class="stat-number">${countMentee}</p>
                        <p class="stat-description">NUMBER OF MENTEES</p>
                    </div>
                    <div class="stat-card">
                        <i class="icon fa fa-book"></i>
                        <p class="stat-number">${countCourse}</p>
                        <p class="stat-description">NUMBER OF COURSES</p>
                    </div>
                    <div class="stat-card">
                        <i class="icon fa fa-chalkboard-teacher"></i>
                        <p class="stat-number">${countMentor}</p>
                        <p class="stat-description">NUMBER OF MENTORS</p>
                    </div>
                </div>
            </div>

            <!-- MENTOR SLIDE -->
            <div class="mentor-content">
                <div class="mentor-content-heading">
                    <div class="mentor-heading">SEE OUR MENTORS</div>
                </div>
                <c:if test="${not empty sessionScope.choosedMentor}">
                    <div class="mentor-cards">
                        <c:forEach items="${sessionScope.choosedMentor}" var="m" begin="0" end="3">
                            <div class="mentor-card">
                                <img class="mentor-image-icon" alt="" src="data:image/jpeg;base64, ${m.avatarPath}">
                                <div class="mentor-body">
                                    <div class="mentor-text">
                                        <div style="color: black">${m.lastName} ${m.firstName}</div>
                                        <c:set var="courseCount" value="0" />
                                        <c:forEach items="${sessionScope.participate}" var="p">
                                            <c:if test="${p.username == m.username}">
                                                <c:forEach items="${sessionScope.course}" var="c">
                                                    <c:if test="${c.courseId == p.courseId}">
                                                        <c:if test="${courseCount < 2}">
                                                            <a href="viewcourse?courseId=${c.courseId}" class="mentor-course">
                                                                <p>${c.courseName}</p>
                                                            </a>
                                                            <c:set var="courseCount" value="${courseCount + 1}"/>
                                                        </c:if>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>

            <!-- COURSES SLIDE -->
            <div class="course-content">
                <div class="course-content-heading">
                    <div class="course-heading">SOME COURSES YOU SHOULD TAKE</div>
                </div>
                <c:if test="${not empty sessionScope.listCourse}">
                    <div class="row course-cards" id="course-container">
                        <c:forEach items="${sessionScope.listCourse}" var="c" varStatus="status">
                            <a href="viewcourse?courseId=${c.courseId}" class="col-md-5 course-card">
                                <div class="course-body">
                                    <div class="course-text">
                                        <div class="course-name">${c.courseName}</div>
                                        <div class="course-body-text">${fn:substring(c.courseDescription, 0, 140)}
                                            <c:if test="${fn:length(c.courseDescription) > 140}">...
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="pagination" id="course-section">
                    <ul>
                        <c:forEach begin="1" end="${pageControl.totalPage}" var="pageNumber">
                            <li class="${pageNumber == pageControl.page ? 'active' : ''}">
                                <a href="${pageControl.urlPattern}page=${pageNumber}#course-section">${pageNumber}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <a href="allCourse" class="more-course-button">View All Courses</a>
            </div>

            <!-- FOOTER -->
            <jsp:include page="footer.jsp"/>
    </body>
</html>
