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
            .mentor-card:hover {
                text-decoration: none;
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
            <c:set var="cM" value="${sessionScope.courseOfMentor}"/>
            <h6><a href="home" class="link">Home</a> <span>></span> <a href="viewcourse?courseId=${cM.courseId}" class="link">${cM.courseName}</a> <span>></span> List Mentors of ${cM.courseName}</h6>



            <c:if test="${not empty sessionScope.mentorThisCourse}">

                <!-- SORT -->
                <form id="orderbyForm" action="viewCourseMentor">
                    <select id="orderby" name="orderby">                                
                        <option value="default" ${sessionScope.order == "default" ? 'selected' : ''}>Default</option>
                        <option value="name" ${sessionScope.order == "name" ? 'selected' : ''}>Sort By Name</option>
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
                <h2 class="list-mentor">Mentor Of This Course</h2>

                <!-- CONTENT MENTOR -->
                <div class="mentor-cards row">
                    <c:forEach items="${sessionScope.mentorThisCourse}" var="m">
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

            <c:if test="${not empty sessionScope.keyword}">
                <c:if test="${empty sessionScope.mentorThisCourse}">
                    <!-- SORT -->
                    <form id="orderbyForm" action="viewCourseMentor">
                        <select id="orderby" name="orderby">                                
                            <option value="default" ${sessionScope.order == "default" ? 'selected' : ''}>Default</option>
                            <option value="name" ${sessionScope.order == "name" ? 'selected' : ''}>Sort By Name</option>
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
                    <h2 class="list-mentor">There are no mentors with a name like "${sessionScope.keyword}".</h2>
                </c:if>
            </c:if>

            <c:if test="${empty sessionScope.mentorThisCourse && empty sessionScope.keyword}">
                <h2 class="list-mentor">This Course Does Not Have Any Mentor Yet</h2>
            </c:if>


            <!-- OTHER COURSES -->
            <c:if test="${not empty sessionScope.otherCourseExO}">
                <h2 class="list-mentor">Other Courses You Can Explore</h2>
                <div class="same-course-cards-wrapper">
                    <div class="same-course-cards">
                        <c:forEach items="${sessionScope.otherCourseExO}" var="oC">
                            <a href="viewcourse?courseId=${oC.courseId}" class="same-course-card">
                                <h3>${oC.courseName}</h3>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>

        <!-- FOOTER -->
        <jsp:include page="footer.jsp"/>

    </body>
</html>
