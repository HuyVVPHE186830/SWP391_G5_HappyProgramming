<%-- 
   Document   : homementor
   Created on : Sep 20, 2024, 3:35:01 PM
   Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/home.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- MIDDLE -->
        <div class="content-middle">

            <!-- HEAD CONTENT MIDDLE -->
            <div class="header-content-middle">
                <div class="text-overlay">
                    <!-- TEXT -->
                    <h1>Empower Your Journey</h1> 
                    <h1 style="margin-bottom: 20px; text-transform: capitalize">Learning Made Accessible</h1>
                    <!-- SEARCH BAR -->
                    <form action="allCourse" method="get" class="search-bar">
                        <input type="hidden" name="search" value="searchByName"/>
                        <input type="text" class="input-submit" placeholder="Search a course" name="keyword">
                        <input type="submit" class="button-submit" value="Search">
                    </form>
                </div>
                <img src="img/banner.jpg" alt="alt"/>
            </div>

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

            <!-- COURSES SLIDE -->
            <div class="course-content">
                <div class="course-content-heading">
                    <div class="course-heading">SOME COURSES YOU SHOULD TAKE</div>
                </div>
                <c:if test="${not empty sessionScope.listCourse}">
                    <div class="row course-cards" id="course-container">
                        <c:forEach items="${sessionScope.listCourse}" var="c" varStatus="status">
                            <a href="viewcourse?courseId=${c.courseId}" class="col-md-5 course-card"
                               style="display: ${status.index < 4 ? 'block' : 'none'};">
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
                        <c:forEach begin="1" end="${pageControl.totalPage - 2}" var="pageNumber">
                            <li class="${pageNumber == pageControl.page ? 'active' : ''}">
                                <a href="${pageControl.urlPattern}page=${pageNumber}#course-section">${pageNumber}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <a href="allCourse" class="more-course-button">View All Courses</a>
            </div>

            <!-- SPLIT -->
            <div style="height: 100px; background-color: #edf2fa"></div>

            <!-- MY COURSES SLIDE -->
            <div class="mycourse-content">
                <div class="mycourse-content-heading">
                    <div class="mycourse-heading">YOUR CURRENT COURSES</div>
                </div>
                <c:set var="u" value="${sessionScope.user}"/>
                <div class="row mycourse-cards">
                    <c:forEach items="${sessionScope.participate}" var="p">
                        <c:if test="${p.username == u.username}">
                            <c:forEach items="${sessionScope.course}" var="c">
                                <c:if test="${c.courseId == p.courseId}">
                                    <a href="viewcourse?courseId=${c.courseId}" class="col-md-5 mycourse-card">
                                        <div class="mycourse-body">
                                            <div class="mycourse-text">
                                                <div class="course-name">${c.courseName}</div>
                                                <div class="mycourse-body-text">${fn:substring(c.courseDescription, 0, 120)}<c:if test="${fn:length(c.courseDescription) > 120}">...</c:if></div>
                                                </div>
                                            </div>
                                        </a>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                </div>

                <div class="col-md-12">
                    <ul class="listPage"></ul>
                </div>
            </div>
            <script>
                let thisPage = 1;
                let limit = 4;
                let list = document.querySelectorAll('.mycourse-card');

                function loadItem() {
                    let beginGet = limit * (thisPage - 1);
                    let endGet = limit * thisPage - 1;
                    list.forEach((item, key) => {
                        item.style.display = (key >= beginGet && key <= endGet) ? 'block' : 'none';
                    });
                    listPage();
                }

                function listPage() {
                    let count = Math.ceil(list.length / limit);
                    document.querySelector('.listPage').innerHTML = '';
                    for (let i = 1; i <= count; i++) {
                        let newPage = document.createElement('li');
                        newPage.innerText = i;
                        if (i === thisPage) {
                            newPage.classList.add('active');
                        }
                        newPage.setAttribute('onclick', "changePage(" + i + ")");
                        document.querySelector('.listPage').appendChild(newPage);
                    }
                }

                function changePage(i) {
                    thisPage = i;
                    loadItem();
                }

                document.addEventListener('DOMContentLoaded', function () {
                    loadItem();
                });
            </script>

            <!-- FOOTER -->
            <jsp:include page="footer.jsp"/>
    </body>
</html>
