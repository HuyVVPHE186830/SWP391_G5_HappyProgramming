<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Courses</title>
        <style>
            body {
                margin: 0;
                padding: 0;
                background-color: #f9f9f9;
                background-image: url('img/7657047.jpg');
                background-size: cover;
                background-position: center;
            }
            .banner {
                height: 200px;
                color: white;
                display: flex;
                justify-content: flex-start;
                align-items: center;
                position: relative;
                padding-left: 20px;
            }
            .search-bar {
                display: flex;
                align-items: start;
                width: 400px;
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

            .dropdown {
                position: relative;
                display: inline-block;
                margin-left: 15px;
                color: white;
                cursor: pointer;
                font-weight: bold;
                font-size: 18px;
                transition: 0.3s all ease;
                z-index: 10;
            }
            .dropdown-content {
                display: none;
                position: absolute;
                background-color: white;
                min-width: 160px;
                box-shadow: 0px 8px 16px rgba(0,0,0,0.2);
                z-index: 20;
                border-radius: 5px;
            }
            .dropdown:hover .dropdown-content {
                display: block;
            }
            .dropdown-content a {
                color: black;
                padding: 10px;
                display: block;
                font-size: 18px;
                text-decoration: none; /* Remove underline for dropdown links */
            }
            .dropdown-content a:hover {
                background-color: #f1f1f1;
            }
            h2 {
                text-align: center;
                margin: 20px 0;
            }
            .product-list {
                display: flex;
                flex-wrap: wrap;
                max-width: 100%;
                justify-content: center;
            }
            .product-item {
                flex: 0 0 30%;
                margin: 10px;
                border-radius: 10px;
                padding: 15px;
                text-align: center;
                color: black;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                background-color: #f9f9f9;
                display: flex; /* Added */
                flex-direction: column; /* Added */
                justify-content: space-between; /* Added to distribute space */
                transition: 0.3s all ease;
            }

            .content {
                position: relative;
                z-index: 1;
                padding: 10px;
                border-radius: 8px;
                flex: 1; /* Allow content to grow and fill space */
                min-height: 200px; /* Set a minimum height for uniformity */
            }
            .product-item:hover {
                transform: translateY(-5px);
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
            .all-products {
                padding: 20px;
                border-radius: 10px;
                color: black;
                margin-top: 20px;
            }
            a {
                text-decoration: none; /* Remove underline for all links */
                color: #5e3fd3; /* Link color */
                transition: color 0.3s;
            }
            .product-item a,
            .view-all a {
                color: inherit; /* Inherit color from parent */
            }
            .product-item a:hover,
            .view-all a:hover {
                color: #ff9900; /* Change color on hover */
            }
            button a {
                text-decoration: none; /* Remove underline for button links */
                color: white; /* Button link color */
            }
            .alo {
                color: #5e3fd3;
                font-weight: bold;
            }
            .lv3Text {
                color: grey;
            }
            .view-all {
                text-align: center;
                margin-top: 20px;
            }
            .more-course-button {
                padding: 12px 24px;
                font-size: 20px;
                font-weight: bold;
                text-align: center;
                text-decoration: none;
                color: white;
                border-radius: 20px;
                transition: background-color 0.3s ease, transform 0.3s ease;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            .more-course-button:hover {
                background-color: #5d3fd3;
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
                color: white;
                text-decoration: none;
            }
        </style>
    </head>
    <body>

        <jsp:include page="header.jsp"/>

        <header class="banner"> 
            <div class="search-container">
                <form action="allCourse" class="search-bar">
                    <input type="hidden" name="search" value="searchByName"/>
                    <input type="text" class="input-submit" placeholder="Search a course" name="keyword" id="search-input" required>
                    <input type="submit" title="Search" class="button-submit" id="submit-btn" value="Search">
                </form>

            </div>
        
            <div class="dropdown">
                Category ▼
                <div class="dropdown-content">
                    <c:forEach items="${listCategory}" var="cate">
                        <a href="allCourse?search=category&categoryId=${cate.categoryId}">${cate.categoryName}</a>
                    </c:forEach>
                </div>
            </div>
            <div class="dropdown">
                Mentor ▼
                <div class="dropdown-content">
                    <c:forEach items="${listMentor}" var="mentor">
                        <a href="allCourse?search=username&username=${mentor.username}">${mentor.username}</a>
                    </c:forEach>
                </div>
            </div>
        </header>

        <section class="featured-product2" style="margin: 10px 40px; border-radius: 16px; background: white; padding: 20px 0; box-shadow: 0px 0px 10px #5d3fd3;">
            <h2 class="alo"style="color: #5d3fd3; text-decoration: none;">LATEST COURSE</h2>
            <div class="product-list">
                <c:forEach items="${listByDate}" var="cou" end="2">
                    <div class="product-item">
                        <div class="content">
                            <a href="viewcourse?courseId=${cou.courseId}" class="mentor-course" style="color: inherit; text-decoration: none;">
                                <h3>${cou.courseName}</h3>
                            </a>
                            <c:if test="${not empty cou.categories}">
                                <h6 style="font-weight: 500">Category: 
                                    <c:forEach items="${cou.categories}" var="cat" varStatus="status">
                                        <span style="font-style: italic; font-weight: 400">${cat.categoryName}<c:if test="${not status.last}">, </c:if></span>
                                    </c:forEach>
                                </h6>
                            </c:if>  
                            <p class="short-description">${cou.courseDescription}</p>
                            <p>Created at: ${cou.createdAt}</p>
                            <h6 class="lv3Text">Mentees in this course: ${cou.countMentee}</h6>

                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>      

        <section class="all-products">
            <h2 class="alo"style="color: whitesmoke; text-decoration: none;">COURSES</h2>
            <div class="product-list" id="product-list">
                <c:forEach items="${listCourse}" var="cou">
                    <div class="product-item">
                        <div class="content">
                            <a href="viewcourse?courseId=${cou.courseId}" class="mentor-course" style="color: inherit; text-decoration: none;">
                                <h3>${cou.courseName}</h3>
                            </a>
                            <c:if test="${not empty cou.categories}">
                                <h6 style="font-weight: 500">Category: 
                                    <c:forEach items="${cou.categories}" var="cat" varStatus="status">
                                        <span style="font-style: italic; font-weight: 400">${cat.categoryName}<c:if test="${not status.last}">, </c:if></span>
                                    </c:forEach>
                                </h6>
                            </c:if>
                            <p class="short-description">${cou.courseDescription}</p>
                            <h6>Created at: ${cou.createdAt}</h6>
                            <h6 class="lv3Text">Mentees in this course: ${cou.countMentee}</h6>

                        </div>
                    </div>
                </c:forEach>
            </div>



            <div class="pagination" id="course-section">
                <ul>
                    <c:forEach begin="1" end="${pageControl.totalPage}" var="pageNumber">
                        <li class="${pageNumber == pageControl.page ? 'active' : ''}">
                            <a href="${pageControl.urlPattern}page=${pageNumber}#course-section">${pageNumber}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div style="display: flex; justify-content: center;">
                <a href="allCourse" class="more-course-button">View All Courses</a>
            </div>
        </section>

        <!-- CHAT -->
        <jsp:include page="chat.jsp"/>

        <jsp:include page="footer.jsp"/>

        <script>
            document.querySelectorAll('.short-description').forEach(function (desc) {
                if (desc.innerText.length > 100) {
                    desc.innerText = desc.innerText.substring(0, 100) + '...';
                }
            });
        </script>

    </body>
</html>