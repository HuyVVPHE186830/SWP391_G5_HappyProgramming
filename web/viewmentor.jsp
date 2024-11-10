<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mentor Information</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/viewcourse.css" rel="stylesheet">
        <style>
            .content-middle {
                display: flex;
                justify-content: space-between;
                margin: 20px 0;
                flex-wrap: wrap;
            }

            .content-left {
                text-align: center;
                margin: 0 30px 20px 30px;
                display: flex;
                justify-content: center;
                align-items: center;
                position: relative;
                overflow: hidden;
                width: 400px;
                height: 400px;
            }

            .mentor-image-icon1 {
                width: 100%;
                height: 100%;
                object-fit: cover;
                box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.2);
                border-radius: 10px;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }


            .detailMentor {
                margin-left: 30px;
                font-style: italic;
                margin-top: 30px;
                font-weight: 400;
                border-left: 1px solid black;
                padding: 5px;
            }

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

            .content-right {
                flex: 1;
                padding-left: 20px;
                margin-bottom: 20px;
            }

            .link-2 {
                color: #000;
                font-style: italic;
                font-weight: 400;
            }

            .link-2:hover {
                text-decoration: none;
            }


            .rating-container {
                background-color: #fff8e1;
                border: 2px solid #f9a825;
                border-radius: 10px;
                padding: 15px 60px;
                margin: 0 0 30px;
                max-width: 200px;
                text-align: center;
                color: #333;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transition: transform 0.2s, box-shadow 0.2s;
                text-decoration: none;
            }


            .rating-container:hover {
                transform: translateY(-3px);
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
                text-decoration: none;
            }
            .rate {
                display: inline-block;
                max-width: 200px;
            }

            .rate:hover {
                text-decoration: none;
            }

            .avg {
                color: #5e3fd3;
                font-weight: 500;
            }

            .star {
                color: gold; /* Yellow star color */
                font-size: 20px; /* Star size */
                margin-left: 5px; /* Space between number and star */
            }

            .form {
                display: inline; /* Keeps the form inline with the other button */
                margin: 0; /* Remove default form margin */
            }

            .modal {
                display: none; /* Hidden by default */
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                z-index: 1000;
            }

            /* Modal Buttons */
            .modal {
                display: none; /* Hidden by default */
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.7); /* Darker background */
                z-index: 1000;
                transition: opacity 0.3s ease-in-out; /* Smooth fade-in/out effect */
                overflow: hidden;
            }

            /* Modal Content */
            .modal-content {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background: linear-gradient(145deg, #ffffff, #f3f3f3); /* Subtle gradient */
                padding: 25px 35px;
                border-radius: 20px;
                text-align: center;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
                max-width: 400px;
                font-family: Arial, sans-serif;
                color: #333;
            }

            /* Modal Heading/Text */
            .modal-content p {
                font-size: 18px;
                font-weight: 500;
                margin: 15px 0;
                line-height: 1.5;
                color: #444;
            }

            /* Modal Buttons */
            .modal-buttons {
                display: flex;
                justify-content: space-evenly;
                margin-top: 20px;
            }

            .btn-confirm, .btn-cancel {
                font-size: 16px;
                padding: 10px 20px;
                border: none;
                border-radius: 10px;
                cursor: pointer;
                font-weight: 600;
                transition: background-color 0.2s ease, transform 0.2s ease;
            }

            /* Confirm Button */
            .btn-cancel {
                background-color: #f44336; /* Red */
                color: white;
            }

            .btn-cancel:hover {
                background-color: #d32f2f;
                transform: scale(1.05); /* Slight zoom effect */
            }

            /* Cancel Button */
            .btn-confirm {
                background-color: #4caf50; /* Green */
                color: white;
            }

            .btn-confirm:hover {
                background-color: #388e3c;
                transform: scale(1.05);
            }

            /* Add subtle focus styles */
            .btn-confirm:focus, .btn-cancel:focus {
                outline: 2px solid #ddd;
                outline-offset: 2px;
            }

            /* Notify */
            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: #4caf50;
                color: white;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                font-size: 16px;
                font-family: Arial, sans-serif;
                z-index: 9999;
                opacity: 0;
                transform: scale(0.8) translateY(20px);
                transition: opacity 0.3s ease, transform 0.5s ease;
            }

            .hidden {
                display: none;
            }
            .chat-button {
                display: inline-block;
                padding: 7px 15px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                margin-top: 10px;
                transition: all 1s ease;
            }

            .chat-button:hover {
                background-color: #0056b3;
                color: white;
                text-decoration: none;
            }


        </style>
    </head>
    <body>
        <!-- Notification Container -->
        <div id="notification" class="notification hidden"></div>
        <script>
            const sessionMessage = '<%= session.getAttribute("message") != null ? session.getAttribute("message") : "" %>';
            if (sessionMessage) {
                showNotification(sessionMessage);
            <% session.removeAttribute("message"); %>
            }
            function showNotification(message) {
                const notification = document.getElementById('notification');
                notification.textContent = message;
                notification.classList.remove('hidden');

                // Make the notification visible
                setTimeout(() => {
                    notification.style.opacity = '1';
                    notification.style.transform = 'translateY(0)';
                }, 100); // Small delay for smooth animation

                // Automatically hide the notification after 3 seconds
                setTimeout(() => {
                    notification.style.opacity = '0';
                    notification.style.transform = 'scale(0.8) translateY(20px)';
                    setTimeout(() => {
                        notification.classList.add('hidden');
                    }, 500); // Allow animation to finish before hiding
                }, 3000);
            }
        </script>
        <!-- HEADER -->
        <jsp:include page="header.jsp"/>
        <!-- DESCRIPTION -->

        <div class="description">
            <c:if test="${not empty requestScope.mentorDetail}">
                <c:set var="mD" value="${requestScope.mentorDetail}"/>
                <c:set var="cM" value="${requestScope.courseOfMentor}"/>
                <h6>
                    <a href="home" class="link">Home</a> <span>></span> 
                    <a href="viewcourse?courseId=${cM.courseId}" class="link">${cM.courseName}</a> <span>></span> 
                    <a href="viewCourseMentor?courseId=${cM.courseId}" class="link">List Mentors of ${cM.courseName}</a> <span>></span> 
                    Mentor ${mD.lastName} ${mD.firstName}
                </h6>
                <div class="content-middle">
                    <div class="content-left">
                        <img class="mentor-image-icon1" alt="" src="data:image/jpeg;base64, ${mD.avatarPath}">
                    </div>
                    <div class="content-right">
                        <h2>${mD.lastName} ${mD.firstName}</h2>
                        <h3>Course: ${cM.courseName} 
                            <c:forEach var="cT" items="${requestScope.categories}" varStatus="status">
                                <c:if test="${status.first}"><span>(</span></c:if><a href="rating" class="link-2"><span>${cT.categoryName}</span></a><c:if test="${not status.last}"><span>, </span></c:if><c:if test="${status.last}"><span>)</span></c:if>
                            </c:forEach>

                        </h3>

                        <!--<h3>rating for this course:</h3>-->

                        <h3>Email: ${mD.mail}</h3>
                        <p>Date Of Birth: ${mD.dob}</p>
                        <a href="rating?ratedId=${mentorDetail.id}" class="rate"> 
                            <div class="rating-container">
                                <span class="avg">${requestScope.avg}</span> 
                                <span class="star">★</span> 
                            </div>
                        </a>
                        <br>
                        <c:if test="${empty sessionScope.user}">
                            <h3 style="font-style: italic; color: #656565; font-weight: 400; margin-top: 25px;">You must sign in to enroll this course</h3>
                        </c:if>    
                        <c:if test="${not empty sessionScope.user}">
                            <c:set var="isEnrolled" value="false" />
                            <c:forEach items="${requestScope.participate}" var="p">
                                <c:if test="${p.username == sessionScope.user.username && mD.username == p.mentorUsername && cM.courseId == p.courseId}">
                                    <c:set var="isEnrolled" value="true" />
                                    <c:if test="${p.statusId == 1}">
                                        <a href="manageCourse?courseId=${cM.courseId}&mentorName=${mD.username}" class="button-enroll">
                                            Your Course
                                        </a>
                                        <c:if test="${not empty sessionScope.user}">
                                            <c:if test="${mD.username != sessionScope.user.username}">
                                                <a href="sendMessage?username=${m.username}" class="button-enroll" style="background: #5e3fd3; margin-left: 10px;">Chat</a>
                                            </c:if>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${p.statusId == 0}">
                                        <a class="button-enroll" style="background-color: #ccc; margin-right: 10px">
                                            Pending
                                        </a>

                                        <form action="deleteRequestForMentee" method="post" class="form" onsubmit="confirmCancel(event)">
                                            <input type="hidden" name="courseId" value="${cM.courseId}">
                                            <input type="hidden" name="mentorUsername" value="${mD.username}">
                                            <input type="hidden" name="username" value="${sessionScope.user.username}">
                                            <button type="submit" class="button-enroll">Cancel</button>                                        
                                        </form>
                                        <c:if test="${not empty sessionScope.user}">
                                            <c:if test="${mD.username != sessionScope.user.username}">
                                                <a href="sendMessage?username=${m.username}" class="button-enroll" style="background: #5e3fd3; margin-left: 10px;">Chat</a>
                                            </c:if>
                                        </c:if>

                                        <script>
                                            function confirmCancel(event) {
                                                event.preventDefault();

                                                document.getElementById('confirmationModal').style.display = 'block';
                                            }

                                            function closeModal() {
                                                document.getElementById('confirmationModal').style.display = 'none';
                                            }

                                            function confirmAction() {
                                                document.querySelector('.form').submit();
                                            }
                                        </script>
                                        <div id="confirmationModal" class="modal">
                                            <div class="modal-content">
                                                <p style="margin: 0 auto; font-weight: bold">Are you sure you want to cancel this request?</p>
                                                <div class="modal-buttons">
                                                    <button onclick="confirmAction()" class="btn-confirm">Yes</button>
                                                    <button onclick="closeModal()" class="btn-cancel">No</button>
                                                </div>
                                            </div>
                                        </div>


                                    </c:if>
                                </c:if>
                            </c:forEach>

                            <c:if test="${!isEnrolled}">
                                <form action="requestScreen" method="post">
                                    <input type="hidden" name="courseId" value="${cM.courseId}">
                                    <input type="hidden" name="mentorUsername" value="${mD.username}">
                                    <input type="hidden" name="username" value="${sessionScope.user.username}">
                                    <button type="submit" class="button-enroll">Enroll</button>
                                </form>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </div>

        <!-- MORE DETAILS -->
        <c:if test="${not empty requestScope.otherCourseMentor}">
            <h5 class="detailMentor">
                In addition to the courses mentioned, this mentor has also excelled in various other fields and has been an integral part of our platform. Here are some other courses this mentor also teaches:
                <c:forEach var="oC" items="${requestScope.otherCourseMentor}" varStatus="status">
                    <a href="viewcourse?courseId=${oC.courseId}" class="link" style="font-weight: 500">${oC.courseName}</a>
                    <c:if test="${status.index == requestScope.otherCourseMentor.size() - 2}"> and </c:if>
                    <c:if test="${!status.last && status.index != requestScope.otherCourseMentor.size() - 2}">, </c:if>
                </c:forEach>
                <br>
                ${mD.lastName} ${mD.firstName} has been our mentor since <span style="font-weight: 500"><fmt:formatDate value="${mD.createdDate}" pattern="dd/MM/yyyy"/></span>. Over the years, this mentor has contributed significantly to the growth and development of our community. With a passion for teaching and guiding, ${mD.lastName} ${mD.firstName} 
                has helped numerous learners achieve their goals. Their dedication and expertise are reflected in the wide range of courses they provide. 
                <br>
                Whether you're a beginner or an advanced learner, this mentor has something valuable to offer in each course.
            </h5>
        </c:if>

        <!-- OTHER MENTOR OF THIS COURSE -->
        <c:if test="${not empty requestScope.otherMentor}">
            <c:set var="cM" value="${requestScope.courseOfMentor}"/>
            <h2 class="list-mentor">Other Mentor Of This Course</h2>
            <div class="mentor-cards">
                <c:forEach items="${requestScope.otherMentor}" var="oM">
                    <div class="mentor-card" style="height: 400px;"> 
                        <a href="viewMentor?userId=${oM.id}&courseId=${cM.courseId}"><img class="mentor-image-icon" alt="" src="data:image/jpeg;base64, ${oM.avatarPath}"></a>
                        <div class="mentor-body">
                            <div class="mentor-text">
                                <div style="color: black">${oM.lastName} ${oM.firstName}</div>
                                <c:if test="${not empty sessionScope.user}">
                                    <c:if test="${m.username != sessionScope.user.username}">
                                        <a href="sendMessage?username=${m.username}" class="chat-button" style="background: #5e3fd3">Chat</a>
                                    </c:if>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <!-- CHAT -->
        <jsp:include page="chat.jsp"/>

        <!-- FOOTERS -->
        <jsp:include page="footer.jsp"/>
    </body>
</html>