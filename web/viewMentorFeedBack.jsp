<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <title>Product Reviews</title>
        <style>
            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
                font-family: Arial, sans-serif;
            }

            .seller-info {
                display: flex;
                align-items: center;
                margin-bottom: 20px;
                padding: 20px;
                background-image: url('img/banner.jpg');
                background-size: cover;
                background-position: center;
                border-radius: 10px;
                color: white;
            }

            .seller-info h3 {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 10px;
            }

            .seller-info p {
                font-size: 16px;
                margin-bottom: 5px;
            }

            .avatar-large {
                width: 320px;
                height: 320px;
                border-radius: 50%;
                margin-right: 20px;
                border: 2px solid white;
            }

            .profile-details {
                display: flex;
                flex-direction: column;
            }

            .review-container {
                padding-right: 20px;
            }

            .review-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .review-header h2 {
                font-size: 24px;
                font-weight: bold;
            }

            .sort-by {
                background-color: #5d3fd3;
                border-radius: 5px;
                padding: 10px 15px;
                color: white;
                display: flex;
                align-items: center;
            }

            .sort-by select {
                margin-left: 10px;
                border: none;
                border-radius: 5px;
                padding: 5px;
            }

            .review-rating {
                display: flex;
                align-items: center;
                font-size: 18px;
                color: white;
            }
            .review-rating2 {
                display: flex;
                align-items: center;
                font-size: 18px;
                color: #5d3fd3;
            }

            .review-rating i {
                margin-right: 5px;
            }

            .review-summary {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
            }

            .review-summary-item {
                display: flex;
                flex-direction: column;
                align-items: center;
                font-size: 14px;
                color: #666;
                background-color: #5d3fd3;
                padding: 10px;
                border-radius: 5px;
            }

            .review-summary-item i {
                margin-right: 5px;
                color: #fff;
            }

            .review-summary-item span {
                color: #fff;
                font-weight: bold;
            }

            .review-item {
                border-bottom: 1px solid #ccc;
                padding: 20px 0;
            }

            .review-item .user-info {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }

            .review-item .user-info img {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                margin-right: 10px;
            }

            .review-item .user-info .username {
                font-weight: bold;
            }

            .review-item .review-content {
                font-size: 16px;
                line-height: 1.5;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <br/>
        <br/>
        <br/>

        <div class="container">
            <div class="seller-info">
                <img class="avatar-large" src="data:image/jpeg;base64,${userById.avatarPath}" alt="Seller Avatar">
                <div class="profile-details">
                    <h3>${userById.username}'s FEEDBACK</h3>
                    <p>RATING OVERALL: 4.5</p>
                    <p>TOP: 4</p>
                </div>
            </div>

            <div class="review-container">
                <div class="review-header" style="display: flex; align-items: center;">
                    <h2 style="margin-right: 20px;">Product Reviews</h2> <!-- Added margin for spacing -->
                    <div class="sort-by">
                        <span>Sort By:</span>
                        <select>
                            <option value="latest">Latest</option>
                            <option value="highest-rated">Highest Rated</option>
                        </select>
                    </div>

                </div>
                <br/>
                <style>
                    .review-summary-item {
                        text-decoration: none; /* Không gạch chân */
                        color: inherit; /* Duy trì màu chữ của phần tử con */
                        display: flex; /* Để các phần tử con nằm ngang */
                        align-items: center; /* Căn giữa theo chiều dọc */
                        padding: 10px; /* Thêm khoảng cách bên trong */
                        border: 1px solid #ccc; /* Đường viền cho mỗi mục */
                        border-radius: 5px; /* Bo góc */
                        margin-bottom: 10px; /* Khoảng cách giữa các mục */
                        transition: background-color 0.3s; /* Hiệu ứng chuyển đổi màu nền */
                    }

                    .review-summary-item:hover {
                        background-color: purple; /* Thay đổi màu nền khi di chuột */
                    }

                    /* Đảm bảo không có gạch chân cho tất cả các liên kết trong review-summary-item */
                    .review-summary-item:hover {
                        text-decoration: none; /* Không gạch chân khi hover */
                        color: inherit; /* Giữ màu chữ không thay đổi khi hover */
                    }
                </style>
                <div class="review-summary">
                    <a href="Rating?ratedId=${userById.id}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>All (10000k)</span>
                    </a>
                    <a href="Rating?search=search-by-noStar&noStar=1&ratedId=${userById.id}&userN=${userById.username}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>1 Sao (75k)</span>
                    </a>
                    <a href="Rating?search=search-by-noStar&noStar=2&ratedId=${userById.id}&userN=${userById.username}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>2 Sao (75k)</span>
                    </a>
                    <a href="Rating?search=search-by-noStar&noStar=3&ratedId=${userById.id}&&userN=${userById.username}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>3 Sao (75k)</span>
                    </a>
                    <a href="Rating?search=search-by-noStar&noStar=4&ratedId=${userById.id}&&userN=${userById.username}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>4 Sao (75k)</span>
                    </a>
                    <a href="Rating?search=search-by-noStar&noStar=5&ratedId=${userById.id}&&userN=${userById.username}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>5 Sao (75k)</span>
                    </a>
                </div>

                <div class="review-item">
                    <c:forEach items = "${listFeedBack}" var = "f">
                        <div class="user-info">
                            <img src="https://via.placeholder.com/40" alt="User Avatar">
                            <span class="username">${f.ratedFromUser}</span>
                        </div>
                        <div class="review-content">
                            ${f.ratingComment}
                        </div>
                        <hr/>
                    </c:forEach>
                </div>
            </div>
        </div>

        <script src="https://kit.fontawesome.com/your-font-awesome-kit.js"></script>
    </body>
</html>