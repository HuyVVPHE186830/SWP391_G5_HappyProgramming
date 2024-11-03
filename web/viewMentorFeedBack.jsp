<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

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
            .btn-transparent {
                background-color: rgba(255, 255, 255, 0.2) !important; /* Nền trắng với opacity 20% */
                color: white !important; /* Màu chữ */
                border: 1px solid white !important; /* Đường viền */
            }

            .btn-transparent:hover {
                background-color: rgba(255, 255, 255, 0.4); /* Tăng độ mờ khi hover */
                color: black; /* Màu chữ khi hover */
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
                <img class="avatar-large" src="data:image/jpeg;base64,${ratedUser.avatarPath}" alt="Seller Avatar">
                <div class="profile-details">
                    <h3>[${ratedUser.username}]'s FEEDBACK 
                    </h3>


                    <p>RATING OVERALL: ${userRatedStar}★</p>
                    <p>TOP: ${rankStar}</p>
                    <button type="button" class="btn btn-transparent" data-toggle="modal" data-target="#feedbackModal">
                        Leave Feedback
                    </button>

                </div>
            </div>
            <div class="modal fade" id="feedbackModal" tabindex="-1" role="dialog" aria-labelledby="feedbackModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="feedbackModalLabel">Leave Your Feedback</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form action="rating?action=rate-this-guy" method="POST">
                                <div class="form-group">
                                    <label for="course">Select Course</label>
                                    <select class="form-control" name="couRseId" required>
                                        <option value="">Select a course</option>
                                        <c:forEach items="${listCourseOfRated}" var="coo">
                                            <option value="${coo.courseId}">${coo.courseName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="rating">rating</label>
                                    <select class="form-control" name="rating" required>
                                        <option value="">Select a rating</option>
                                        <option value="1">1 Star</option>
                                        <option value="2">2 Stars</option>
                                        <option value="3">3 Stars</option>
                                        <option value="4">4 Stars</option>
                                        <option value="5">5 Stars</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comment">Comment</label>
                                    <textarea class="form-control" name="comment" rows="3" required></textarea>
                                </div>
                                <input type="hidden" name="ratedId" value="${ratedUser.id}">
                                <input type="hidden" name="userN" value="${user.id}">
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary">Submit Feedback</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div class="review-container">
                <div class="review-header" style="display: flex; align-items: center;">
                    <h2 style="margin-right: 20px;">Product Reviews</h2> 
                    <div class="sort-by">
                        <span>Choose course:</span>
                        <select id="courseSelect" onchange="redirectToCourse(this)">
                            <option value="">Select a course</option>
                            <c:forEach items="${listCourseOfRated}" var="course">
                                <option value="rating?search=rate-by-course&ratedId=${userById.id}&courseid=${course.courseId}">
                                    ${course.courseName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <script>
                        function redirectToCourse(selectElement) {
                            const selectedValue = selectElement.value;
                            console.log("Redirecting to:", selectedValue); // In ra giá trị sẽ được chuyển hướng
                            if (selectedValue) {
                                window.location.href = selectedValue;
                            }
                        }
                    </script>


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
                    <a href="rating?ratedId=${userById.id}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>All (${turnStar})</span>
                    </a>
                    <a href="rating?search=search-by-noStar&noStar=1&ratedId=${ratedUser.id}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>1 Star (${turnStar1})</span>
                    </a>
                    <a href="rating?search=search-by-noStar&noStar=2&ratedId=${ratedUser.id}" class="review-summary-item">
                        <i class="fas fa-sta    r"></i>
                        <span>2 Star (${turnStar2})</span>
                    </a>
                    <a href="rating?search=search-by-noStar&noStar=3&ratedId=${ratedUser.id}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>3 Star (${turnStar3})</span>
                    </a>
                    <a href="rating?search=search-by-noStar&noStar=4&ratedId=${ratedUser.id}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>4 Star (${turnStar4})</span>
                    </a>
                    <a href="rating?search=search-by-noStar&noStar=5&ratedId=${ratedUser.id}" class="review-summary-item">
                        <i class="fas fa-star"></i>
                        <span>5 Star (${turnStar5})</span>
                    </a>
                </div>

              
            </div>
        </div>

        <script src="https://kit.fontawesome.com/your-font-awesome-kit.js"></script>
    </body>
</html>