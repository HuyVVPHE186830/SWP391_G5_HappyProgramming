<%-- 
    Document   : viewCourseMentor
    Created on : Oct 8, 2024, 9:12:48 PM
    Author     : ThuanNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/viewcourse.css" rel="stylesheet">
        <style>
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

            #orderbyForm {
                margin-bottom: 20px;
                margin-left: 30px;
                margin-top: 30px;
            }

            #orderby {
                width: 300px;
                padding: 8px;
                font-size: 14px;
                border: 1px solid #ccc;
                background-color: #fff;
                margin-right: 10px;
                display: inline-block;
            }

            #orderby:focus {
                border-color: #007bff;
                outline: none;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
            }

            #orderbyForm input[type="submit"] {
                padding: 8px 16px;
                background-color: #007bff;
                color: #fff;
                border: none;
                cursor: pointer;
            }

            #orderbyForm input[type="submit"]:hover {
                background-color: #0056b3;
            }

            #orderbyForm {
                display: inline-block;
                vertical-align: top;
            }

            .search-bar {
                display: flex;
                align-items: center;
                width: 100%;
                max-width: 300px;
                border-radius: 10px;
                overflow: hidden;
                margin-left: 30px;
                margin-bottom: 20px;
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

        </style>
    </head>
    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- MENTOR LIST -->
        <div  class="description">
            <c:set var="cM" value="${requestScope.courseOfMentor}"/>
            <h6><a href="home" class="link">Home</a> <span>></span> <a href="viewcourse?courseId=${cM.courseId}" class="link">${cM.courseName}</a> <span>></span> List Mentors of ${cM.courseName}</h6>



            <c:if test="${not empty requestScope.mentorThisCourse}">

                <!-- SORT -->
                <form id="orderbyForm" action="viewCourseMentor">
                    <select id="orderby" name="orderby">                                
                        <option value="default" ${requestScope.order == "default" ? 'selected' : ''}>Default</option>
                        <option value="name" ${requestScope.order == "name" ? 'selected' : ''}>Sort By Name</option>
                    </select>
                    <input type="hidden" name="courseId" value="${cM.courseId}">
                </form>

                <!-- SEARCH -->
                <form action="viewCourseMentor" method="post" class="search-bar">
                    <input type="text" class="input-submit" placeholder="Search a mentor" name="keyword" id="keyword" oninput="checkInput()">
                    <input type="hidden" name="courseId" value="${cM.courseId}">
                    <input type="submit" class="button-submit" id="submit-btn" disabled value="Search">
                </form>

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

                <script>
                    document.getElementById('orderby').addEventListener('change', function () {
                        document.getElementById('orderbyForm').submit();
                    });
                </script>
                <h2 class="list-mentor">Mentor Of This Course</h2>

                <!-- CONTENT MENTOR -->
                <div class="mentor-cards row">
                    <c:forEach items="${requestScope.mentorThisCourse}" var="m">
                        <a href="viewMentor?userId=${m.id}&courseId=${cM.courseId}" class="mentor-card">
                            <img class="mentor-image-icon" alt="" src="data:image/jpeg;base64, ${m.avatarPath}">
                            <div class="mentor-body">
                                <div class="mentor-text">
                                    <div style="color: black">${m.lastName} ${m.firstName}</div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>


            </c:if>

            <c:if test="${not empty requestScope.keyword}">
                <c:if test="${empty requestScope.mentorThisCourse}">
                    <!-- SORT -->
                    <form id="orderbyForm" action="viewCourseMentor">
                        <select id="orderby" name="orderby">                                
                            <option value="default" ${requestScope.order == "default" ? 'selected' : ''}>Default</option>
                            <option value="name" ${requestScope.order == "name" ? 'selected' : ''}>Sort By Name</option>
                        </select>
                        <input type="hidden" name="courseId" value="${cM.courseId}">
                    </form>

                    <!-- SEARCH -->
                    <form action="viewCourseMentor" method="post" class="search-bar">
                        <input type="text" class="input-submit" placeholder="Search a course" name="keyword" id="keyword" oninput="checkInput()">
                        <input type="hidden" name="courseId" value="${cM.courseId}">
                        <input type="submit" class="button-submit" id="submit-btn" disabled value="Search">
                    </form>

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

                    <script>
                        document.getElementById('orderby').addEventListener('change', function () {
                            document.getElementById('orderbyForm').submit();
                        });
                    </script>
                    <h2 class="list-mentor">There are no mentors with a name like "${requestScope.keyword}".</h2>
                </c:if>
            </c:if>

            <c:if test="${empty requestScope.mentorThisCourse && empty requestScope.keyword}">
                <h2 class="list-mentor">This Course Does Not Have Any Mentor Yet</h2>
            </c:if>


            <!-- OTHER COURSES -->
            <c:if test="${not empty requestScope.otherCourseExO}">
                <h2 class="list-mentor">Other Courses You Can Explore</h2>
                <div class="same-course-cards-wrapper">
                    <div class="same-course-cards">
                        <c:forEach items="${requestScope.otherCourseExO}" var="oC">
                            <a href="viewcourse?courseId=${oC.courseId}" class="same-course-card">
                                <h3>${oC.courseName}</h3>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>

        <!-- CHAT -->
        <jsp:include page="chat.jsp"/>

        <!-- FOOTER -->
        <jsp:include page="footer.jsp"/>

    </body>
</html>
