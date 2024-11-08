<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cửa Hàng Trực Tuyến</title>
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
            .search-container {
                width: 100%;
                max-width: 600px;
                background: rgba(255, 255, 255, 0.8);
                border-radius: 5px;
                padding: 10px;
                margin-bottom: 15px;
            }
            #search-form {
                display: flex;
                justify-content: flex-start;
            }
            input[type="text"] {
                padding: 5px;
                width: 500px;
                border: none;
                border-radius: 5px;
            }
            .content {
                display: flex; /* Added */
                flex-direction: column; /* Keep items stacked */
                justify-content: space-between; /* Distribute space */
                flex: 1; /* Allow content to grow */
                min-height: 200px; /* Maintain uniform height */
            }

            .button-container {
                display: flex; /* Added */
                justify-content: center; /* Center button */
                margin-top: auto; /* Push to the bottom of the content */
            }
            button:hover {
                background-color: plum;
            }
            .dropdown {
                position: relative;
                display: inline-block;
                margin-left: 15px;
                color: white;
                cursor: pointer;
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
                font-size: 12px;
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
                background-color: rgba(255, 182, 193, 0.5);
                display: flex; /* Added */
                flex-direction: column; /* Added */
                justify-content: space-between; /* Added to distribute space */
            }

            .content {
                position: relative;
                z-index: 1;
                background: rgba(255, 255, 255, 0.8);
                padding: 10px;
                border-radius: 8px;
                flex: 1; /* Allow content to grow and fill space */
                min-height: 200px; /* Set a minimum height for uniformity */
            }
            .product-item:hover {
                transform: translateY(-5px);
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
                background-color: #5e3fd3;
                color: white;
                border-radius: 5px;
                text-decoration: none; /* Remove underline for pagination links */
                transition: background-color 0.3s;
            }
            .pagination a:hover {
                background-color: #5a8dee;
            }
            .pagination .active a {
                background-color: #5a8dee;
                font-weight: bold;
                cursor: default;
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
                text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            }
            .lv3Text {
                color: grey;
            }
            .view-all {
                text-align: center;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>

        <jsp:include page="header.jsp"/>

        <header class="banner"> 
            <div class="search-container">
                <form action="allCourse" method="get" id="search-form">
                    <input type="hidden" name="search" value="searchByName"/>
                    <input type="text" placeholder="Search course" name="keyword" id="search-input" required/>
                    <button type="submit" title="Search">Search</button>
                </form>
            </div>
            <div class="dropdown">
                Sort by ▼
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/allCourse?search=price-dces">Decrease Sort</a>
                    <a href="${pageContext.request.contextPath}/allCourse?search=price-asc">Increase Sort</a>
                </div>
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

        <section class="featured-product2">
            <h2 class="alo"style="color: whitesmoke; text-decoration: none;">LATEST COURSE</h2>
            <div class="product-list">
                <c:forEach items="${listByDate}" var="cou" end="2">
                    <div class="product-item">
                        <div class="content">
                            <a href="viewcourse?courseId=${cou.courseId}" class="mentor-course"style="color: inherit; text-decoration: none;">
                                <h3>${cou.courseName}</h3>
                            </a>
                            <c:if test="${not empty cou.categories}">
                                <h6>Category: 
                                    <c:forEach items="${cou.categories}" var="cat" varStatus="status">
                                        ${cat.categoryName}<c:if test="${not status.last}">, </c:if>
                                    </c:forEach>
                                </h6>
                            </c:if>  
                            <p class="short-description">${cou.courseDescription}</p>
                            <p>Created at: ${cou.createdAt}</p>
                            <h6 class="lv3Text">Mentee in this course: ${cou.countMentee}</h6>
                            <button>
                                <a href="viewcourse?courseId=${cou.courseId}" class="mentor-course"style="color: whitesmoke; text-decoration: none;">View</a>
                            </button>
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
                            <h3>
                                <a href="viewcourse?courseId=${cou.courseId}" class="course-title-link"style="color: inherit; text-decoration: none;">${cou.courseName}</a>
                            </h3>
                            <c:if test="${not empty cou.categories}">
                                <h6>Category: 
                                    <c:forEach items="${cou.categories}" var="cat" varStatus="status">
                                        ${cat.categoryName}<c:if test="${not status.last}">, </c:if>
                                    </c:forEach>
                                </h6>
                            </c:if>
                            <p class="short-description">${cou.courseDescription}</p>
                            <h6>Created at: ${cou.createdAt}</h6>
                            <h6 class="lv3Text">Mentee in this course: ${cou.countMentee}</h6>
                            <button>
                                <a href="viewcourse?courseId=${cou.courseId}" class="mentor-course"style="color: whitesmoke; text-decoration: none;">View</a>
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="view-all">
                <a href="allCourse" class="nav-button"style="color: white; text-decoration: none;">View All Courses</a>
            </div>

            <div class="pagination">
                <ul>
                    <c:forEach begin="1" end="${pageControl.totalPage}" var="pageNumber">
                        <li class="${pageNumber == pageControl.page ? 'active' : ''}">
                            <a href="${pageControl.urlPattern}page=${pageNumber}">${pageNumber}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </section>

        <!-- CHAT -->
        <jsp:include page="chat.jsp"/>

        <jsp:include page="footer.jsp"/>

        <script>
            document.querySelectorAll('.short-description').forEach(function (desc) {
                if (desc.innerText.length > 45) {
                    desc.innerText = desc.innerText.substring(0, 45) + '...';
                }
            });
        </script>

    </body>
</html>