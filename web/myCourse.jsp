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

            .search-bar {
                display: flex;
                align-items: start;
                width: 100%;
                max-width: 600px;
                border-radius: 10px;
                overflow: hidden;
            }

            .input-submit {
                border: none;
                padding: 10px;
                flex: 1;
                border-radius: 10px 0 0 10px;
                font-size: 16px;
                outline: none;
            }

            .button-submit {
                background-color: #5e3fd3;
                color: white;
                border: none;
                padding: 10px 20px;
                cursor: pointer;
                border-radius: 0 10px 10px 0;
                font-size: 16px;
                transition: all 1s ease;
                font-weight: bold;
            }

            .button-submit:hover {
                background-color: #541371;
            }

            .banner {
                height: 200px;
                background: linear-gradient(90deg, #6a11cb, #5d3fd3);
                color: white;
                display: flex;
                justify-content: flex-start;
                align-items: center;
                position: relative;
                padding-left: 30px;
                padding-top: 30px;
            }

            .dropdown {
                position: relative;
                display: inline-block;
                margin-left: 15px;
                color: white;
                cursor: pointer;
                font-weight: bold;
                font-size: 18px;
                transition: 0.3s all ease;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: white;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }

            .dropdown:hover {
                color: white;
                text-decoration: none;
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
                background-color: #5e3fd3;
                color: white;
                text-decoration: none;
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
                box-shadow: 0px 0px 5px #5d3fd3;
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
                background: white;
                border-radius: 10px;
                padding: 15px;
                text-align: center;
                width: 40%;
                color: black;
                box-shadow: 4px 4px 10px #ccc;
            }

            .mentor-course:hover {
                text-decoration: none;
            }

            .mentor-course h3{
                font-size: 22px;
                margin-bottom: 20px;
                height: 25%;
                color: black;
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
                background-color: white;
                color: #5d3fd3;
                border-radius: 5px;
                text-decoration: none;
                transition: background-color 0.3s;
            }

            .pagination a:hover {
                background-color: #edf2fa;
                text-decoration: none;
            }

            .pagination .active a {
                background-color: #5d3fd3;
                font-weight: bold;
                color: white;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <header class="banner"> 
            <form action="viewMyCourses" class="search-bar">
                <input type="hidden" name="search" value="searchByName"/>
                <input type="text" class="input-submit" placeholder="Search a course" name="keyword" id="search-input" required>
                <input type="submit" title="Search" class="button-submit" id="submit-btn" value="Search">
            </form>
            <div>
                <a href="viewMyCourses" class="dropdown">All</a>
            </div>
            <div class="dropdown">
                Category
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
        <!-- Mentoring Section -->
        <c:if test="${sessionScope.user.roleId == 2}">
            <section class="featured-product2" style="margin: 10px 40px; border-radius: 16px; background: #dedede; padding: 20px 0; box-shadow: 0px 0px 10px #5d3fd3;">
                <h2 style="margin-bottom: 20px; font-weight: bold; font-size: 36px;">Mentoring</h2>
                <div class="product-list">
                    <c:if test="${not empty listCoursesMentoring}">
                        <c:forEach items="${listCoursesMentoring}" var="cou">
                            <div class="product-item">
                                <a href="manageCourse?courseId=${cou.courseId}&mentorName=${cou.mentorName}" class="mentor-course">
                                    <h3>${cou.courseName}</h3>
                                </a>
                                <p style="font-weight: 500">Categories: 
                                    <c:forEach items="${cou.categories}" var="cat" varStatus="status">
                                        <span style="font-style: italic; font-weight: 400">${cat.categoryName}<c:if test="${not status.last}">, </c:if></span>
                                    </c:forEach>

                                </p>
                                <p class="short-description">${cou.courseDescription}</p>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty listCoursesMentoring}">
                        <p style="font-size: 20px;">No mentoring courses available.</p>
                    </c:if>

                </div>

                <!-- Pagination for Mentoring -->
                <div class="pagination" id="course-section">
                    <ul>
                        <c:forEach begin="1" end="${mentoringPageControl.totalPage}" var="pageNumber">
                            <li class="${pageNumber == mentoringPageControl.page ? 'active' : ''}">
                                <a href="viewMyCourses?mentoringPage=${pageNumber}&search=${param.search}&keyword=${param.keyword}#course-section">${pageNumber}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </section>
        </c:if>

        <!-- Studying Section -->
        <div class="applyCourse">
            <a href="listRequestForMentee?userId=${sessionScope.user.id}">
                <i class="fa fa-list"></i>
            </a>
        </div>
        <section class="featured-product2" style="margin: 10px 40px 20px; border-radius: 16px; background: #dedede; padding: 20px 0; box-shadow: 0px 0px 10px #5d3fd3">
            <h2 style="margin-bottom: 20px; font-weight: bold; font-size: 36px;">Studying</h2>   
            <div class="product-list">
                <c:if test="${not empty listCoursesStudying}">
                    <c:forEach items="${listCoursesStudying}" var="cou">
                        <div class="product-item">
                            <a href="manageCourse?courseId=${cou.courseId}&mentorName=${cou.mentorName}" class="mentor-course">
                                <h3>${cou.courseName}</h3>
                            </a>
                            <p style="font-weight: 500">Mentor: <span style="font-style: italic; font-weight: 400">${cou.mentorName}</span></p>
                            <p style="font-weight: 500">Categories: 
                                <c:forEach items="${cou.categories}" var="cat" varStatus="status">
                                    <span style="font-style: italic; font-weight: 400">${cat.categoryName}<c:if test="${not status.last}">, </c:if></span>
                                </c:forEach>
                            </p>
                            <p class="short-description">${cou.courseDescription}</p>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty listCoursesStudying}">
                    <p style="font-size: 20px;">No studying courses available.</p>
                </c:if>

            </div>

            <!-- Pagination for Studying -->
            <div class="pagination" id="course-section">
                <ul>
                    <c:forEach begin="1" end="${studyingPageControl.totalPage}" var="pageNumber">
                        <li class="${pageNumber == studyingPageControl.page ? 'active' : ''}">
                            <a href="viewMyCourses?studyingPage=${pageNumber}&search=${param.search}&keyword=${param.keyword}#course-section">${pageNumber}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </section>

        <jsp:include page="footer.jsp" />

        <script>
            // Giới hạn mô tả xuống 80 ký tự
            document.querySelectorAll('.short-description').forEach(function (desc) {
                if (desc.innerText.length > 80) {
                    desc.innerText = desc.innerText.substring(0, 80) + '...';
                }
            });
        </script>
    </body>
</html>
