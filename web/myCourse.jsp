<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/bootstrap.css" rel="stylesheet">
        <title>My Courses</title>
        <style>
            body {
                margin: 0;
                padding: 0;
                background-color: #f9f9f9;
            }

            .banner {
                height: 200px;
                background-color: grey;
                color: white;
                display: flex;
                justify-content: flex-start;
                align-items: center;
                position: relative;
                padding-left: 200px;
            }

            .search-container {
                width: 100%;
                max-width: 600px;
            }

            #search-form {
                display: flex;
                justify-content: flex-start;
            }

            input[type="text"] {
                padding: 5px;
                width: 500px;
                height: 32.5px;
                border: none;
                border-radius: 5px;
            }

            button {
                padding: 5px 10px;
                background-color: #1da1f2;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-left: 5px;
            }

            button:hover {
                background-color: #ff9900;
            }

            .dropdown {
                position: relative;
                display: inline-block;
                margin-left: 15px;
                color: white;
                cursor: pointer;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: white;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }

            .dropdown:hover .dropdown-content {
                display: block;
            }

            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            .dropdown-content a:hover {
                background-color: #f1f1f1;
            }

            h2 {
                text-align: center;
                margin: 20px 0;
            }
            .applyCourse {
                display: flex;
                justify-content: flex-end;
                margin-top: 20px;
                margin-right: 20px;
            }
            .applyCourse a {
                color: white;
                font-size: 26px;
                background: #5e3fd3;
                width: 100px;
                text-align: center;
                border-radius: 15px;
                padding: 5px;
                font-weight: 500;
                transition: all 0.3s ease;
                margin: 0 5px;
            }
            .applyCourse a:hover {
                text-decoration: none;
                background: #541371;
                color: white;
            }

            .product-list {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 20px;
            }

            .product-item {
                background: gainsboro;
                border-radius: 10px;
                padding: 15px;
                text-align: center;
                width: 200px;
                color: black;
            }

            .mentor-course h3{
                font-size: 18px;
            }

            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 20px 0;
            }

            .pagination ul {
                list-style: none;
                display: flex;
                padding: 0;
                margin: 0;
            }

            .pagination li {
                margin: 0 5px;
            }

            .pagination a {
                padding: 10px 15px;
                background-color: #1da1f2;
                color: white;
                border-radius: 5px;
                text-decoration: none;
                transition: background-color 0.3s;
            }

            .pagination a:hover {
                background-color: #ff9900;
            }

            .pagination .active a {
                background-color: #ff9900;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <header class="banner"> 
            <div class="search-container">
                <form action="viewMyCourses" id="search-form">
                    <input type="hidden" name="search" value="searchByName"/>
                    <input type="text" placeholder="Search course" name="keyword" id="search-input" required/>
                    <button type="submit" title="Search"><i class="fa fa-search"></i> Search</button>
                </form>
            </div>
            <div>
                <a href="viewMyCourses" class="dropdown">All</a>
            </div>
            <div class="dropdown">
                Category ▼
                <div class="dropdown-content">
                    <c:forEach items="${listCategory}" var="cate">
                        <a href="viewMyCourses?search=category&categoryId=${cate.categoryId}&mentoringPage=${param.mentoringPage}&studyingPage=${param.studyingPage}">
                            ${cate.categoryName}
                        </a>
                    </c:forEach>
                </div>
            </div>
        </header>

        <!-- Enroll/List Course Request For Mentor -->
        <c:if test="${sessionScope.user.roleId == 2}">
            <div class="applyCourse">
                <a href="listRequestForMentor?userId=${sessionScope.user.id}">
                    <i class="fa fa-list"></i>
                </a>
                <a href="applyCourse?userId=${sessionScope.user.id}">
                    <i class="fa fa-book-medical"></i>
                </a>
            </div>
        </c:if>
        <c:if test="${sessionScope.user.roleId == 3}">
            <div class="applyCourse">
                <a href="listRequestForMentee?userId=${sessionScope.user.id}">
                    <i class="fa fa-list"></i>
                </a>
            </div>
        </c:if>
        <!-- Mentoring Section -->
        <c:if test="${sessionScope.user.roleId == 2}">
            <section class="featured-product2">
                <h2>Mentoring</h2>
                <div class="product-list">
                    <c:if test="${not empty listCoursesMentoring}">
                        <c:forEach items="${listCoursesMentoring}" var="cou">
                            <div class="product-item">
                                <a href="manageCourse?courseId=${cou.courseId}&mentorName=${cou.mentorName}" class="mentor-course">
                                    <h3>${cou.courseName}</h3>
                                </a>
                                <p>Categories: 
                                    <c:forEach items="${cou.categories}" var="cat" varStatus="status">
                                        ${cat.categoryName}<c:if test="${not status.last}">, </c:if>
                                    </c:forEach>

                                </p>
                                <p class="short-description">${cou.courseDescription}</p>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty listCoursesMentoring}">
                        <p>No mentoring courses available.</p>
                    </c:if>

                </div>

                <!-- Pagination for Mentoring -->
                <div class="pagination">
                    <ul>
                        <c:forEach begin="1" end="${mentoringPageControl.totalPage}" var="pageNumber">
                            <li class="${pageNumber == mentoringPageControl.page ? 'active' : ''}">
                                <a href="viewMyCourses?mentoringPage=${pageNumber}&search=${param.search}&keyword=${param.keyword}">${pageNumber}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </section>
        </c:if>

        <!-- Studying Section -->
        <section class="featured-product2">
            <h2>Studying</h2>
            <div class="product-list">
                <c:if test="${not empty listCoursesStudying}">
                    <c:forEach items="${listCoursesStudying}" var="cou">
                        <div class="product-item">
                            <a href="manageCourse?courseId=${cou.courseId}&mentorName=${cou.mentorName}" class="mentor-course">
                                <h3>${cou.courseName}</h3>
                            </a>
                            <p>Mentor: ${cou.mentorName}</p>
                            <p>Categories: 
                                <c:forEach items="${cou.categories}" var="cat" varStatus="status">
                                    ${cat.categoryName}<c:if test="${not status.last}">, </c:if>
                                </c:forEach>

                            </p>
                            <p class="short-description">${cou.courseDescription}</p>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty listCoursesStudying}">
                    <p>No studying courses available.</p>
                </c:if>

            </div>

            <!-- Pagination for Studying -->
            <div class="pagination">
                <ul>
                    <c:forEach begin="1" end="${studyingPageControl.totalPage}" var="pageNumber">
                        <li class="${pageNumber == studyingPageControl.page ? 'active' : ''}">
                            <a href="viewMyCourses?studyingPage=${pageNumber}&search=${param.search}&keyword=${param.keyword}">${pageNumber}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </section>

        <jsp:include page="footer.jsp" />

        <script>
            // Giới hạn mô tả xuống 45 ký tự
            document.querySelectorAll('.short-description').forEach(function (desc) {
                if (desc.innerText.length > 45) {
                    desc.innerText = desc.innerText.substring(0, 45) + '...';
                }
            });
        </script>
    </body>
</html>
