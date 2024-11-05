<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.*, dal.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Quản Lý Khóa Học</title>
        <link rel="icon" href="images/logo1.png"/>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <link href="css/style.css" rel="stylesheet" type="text/css"/> 

        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://mdbootstrap.com/previews/ecommerce-demo/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://mdbootstrap.com/previews/ecommerce-demo/css/mdb-pro.min.css">
        <link rel="stylesheet" href="https://mdbootstrap.com/previews/ecommerce-demo/css/mdb.ecommerce.min.css">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <link href="css/style.css" rel="stylesheet" type="text/css"/> 
        <link href="css/manager.css" rel="stylesheet" type="text/css"/>
        <style>
            body {
                margin: 0;
                padding: 0;
            }

            select option {
                font-size: 16px;
                padding: 5px;
            }

            select {
                width: 32.3%;
                margin: 0;
                font-size: 16px;
                padding: 7px 10px;
                font-family: Segoe UI, Helvetica, sans-serif;
                border: 1px solid #D0D0D0;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                box-sizing: border-box;
                border-radius: 10px;
                outline: none;
            }
            .modal-dialog {
                max-width: 1400px;
            }

            .modal-content {
                padding: 20px;
            }
        </style>
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"><link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&amp;display=swap"><link rel="stylesheet" type="text/css" href="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/css/mdb5/3.8.1/compiled.min.css"><link rel="stylesheet" type="text/css" href="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/css/mdb-plugins-gathered.min.css"><style>body {
                background-color: #fbfbfb;
            }
            @media (min-width: 991.98px) {
                main {
                    padding-left: 240px;
                }
            }
            .text_page_head{
                font-size: 18px;
                font-weight: 600;
            }
            .text_page {
                font-size: 14px;
                font-weight: 600;
            }
            /* Sidebar */
            .sidebar {
                position: fixed;
                top: 0;
                bottom: 0;
                left: 0;
                padding: 58px 0 0; /* Height of navbar */
                box-shadow: 0 2px 5px 0 rgb(0 0 0 / 5%), 0 2px 10px 0 rgb(0 0 0 / 5%);
                width: 240px;
                z-index: 600;
            }

            @media (max-width: 991.98px) {
                .sidebar {
                    width: 100%;
                }
            }
            .sidebar .active {
                border-radius: 5px;
                box-shadow: 0 2px 5px 0 rgb(0 0 0 / 16%), 0 2px 10px 0 rgb(0 0 0 / 12%);
            }

            .sidebar-sticky {
                position: relative;
                top: 0;
                height: calc(100vh - 48px);
                padding-top: 0.5rem;
                overflow-x: hidden;
                overflow-y: auto; /* Scrollable contents if viewport is shorter than content. */
            }
        </style>
    </head>
    <body>

        <!--Main Navigation-->
        <header>
            <jsp:include page="leftadmin.jsp"></jsp:include>
                <div class="notification-icon" style="position: relative; display: inline-block; margin-left: 20px;">
                    <i class="fas fa-bell" style="font-size: 24px;"></i>
                    <span class="badge badge-danger" style="position: absolute; top: -10px; right: -10px; font-size: 12px;">
                        5
                    </span>
                </div>
            </header>
            <!--Main Navigation-->
        <jsp:include page="header_right.jsp"></jsp:include>
            <!--Main layout-->
            <main>
                <div class="container pt-4" style="max-width: 2400px">
                    <section class="mb-4">
                        <div class="card">
                            <div class="row" style="">
                                <div class="col-sm-4" style="text-align: center; margin-top: 20px; margin-bottom: 20px;padding-top: 20px">
                                    <h3 class="mb-0" id="">
                                        <strong>Mentor List</strong>
                                    </h3>
                                </div>
                                <div class="col-lg-2"></div>
                                <div class="col-lg-6" style="text-align: center; margin-top: 20px; margin-bottom: 20px;padding-top: 20px"F>
                                    <form action="<%= request.getContextPath() %>/ManagerCourse" method="post" style="display: flex; justify-content: center">
                                    <input name="valueSearch" value="${requestScope.searchValue != null ? requestScope.searchValue : ""}" id="searchId" type="text" placeholder="Search course" style="width: 60%; padding: 4px 10px; border-radius: 15px">
                                    <button type="submit" style="border-radius: 50%; width: 40px; font-size: 18px; margin-left: 10px"><i class="fa fa-search"></i></button>
                                </form>
                            </div>
                        </div>
                        <c:if test="${not empty succMsg}">
                            <div style="margin-top: 20px" class="alert alert-success" role="alert">
                                ${succMsg}
                            </div>
                            <c:remove var="succMsg" scope="session"/>
                        </c:if>
                        <c:if test="${not empty failedMsg}">
                            <div style="margin-top: 20px" class="alert alert-danger" role="alert">
                                ${failedMsg}
                            </div>
                            <c:remove var="failedMsg" scope="session"/>
                        </c:if>
                        <div class="card-body" style="padding: 0">
                            <div class="table-responsive">
                                <table class="table table-hover text-nowrap">
                                    <thead>
                                        <tr>
                                            <th class="text_page_head">Avatar</th>
                                            <th class="text_page_head">Name</th>
                                            <th class="text_page_head">Email</th>
                                            <th class="text_page_head">Date of birth</th>
                                            <th class="text_page_head">CV</th>
                                            <th class="text_page_head">Action</th>
                                            <th>
                                                <c:set var="waitingListSize" value="${fn:length(sessionScope.requestWaitingList)}" />

                                                <div class="icon-container">
                                                    <a style="margin-left: 5px; background-color: #5e3fd3; color: white; border-radius: 5px; padding: 8px 12px; text-decoration: none;" href="#mentorListModal" class="btn" data-toggle="modal">
                                                        <i class="fa-solid fa-list"></i>
                                                        <span class="badge">${waitingListSize}</span>
                                                    </a>
                                                </div>


                                            </th>
                                        </tr>
                                    </thead>
                                    <c:forEach items="${sessionScope.mentorList}" var="t">
                                        <c:set var="username" value="${t.username}"/>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <img src="data:image/jpeg;base64,${t.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%; object-fit: cover;">
                                                </td>
                                                <td class="text_page" style="font-weight: 500">${t.lastName}</td>
                                                <td class="text_page" style="font-weight: 500">${t.mail}</td>
                                                <td class="text_page" style="font-weight: 500">${t.dob}</td>
                                                <td class="text_page" style="font-weight: 500">${tCVPath}</td>
                                                <td>
                                                    <a href="#mentorCoursesModal_${username}" data-toggle="modal" class="btn" style="background-color: #5e3fd3; color: white;"> <i class="fa-solid fa-eye"></i></a>
                                                </td>
                                            </tr>

                                            <!-- Modal to Display Mentor's Courses -->
                                        <div id="mentorCoursesModal_${username}" class="modal fade">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h4 class="modal-title">Courses for Mentor: ${t.lastName}</h4>
                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <c:forEach items="${sessionScope.participateListByStatus}" var="p">
                                                            <c:if test="${p.username == username}">
                                                                <c:forEach items="${sessionScope.courseList}" var="c">
                                                                    <c:if test="${c.courseId == p.courseId}">
                                                                        <div class="d-flex justify-content-between align-items-center mb-2">
                                                                            <div>
                                                                                Course ID: ${p.courseId} - ${c.courseName}
                                                                            </div>
                                                                            <a href="MangeRequest?action=RemoveMentor&courseId=${p.courseId}&mentorUsername=${username}" class="btn" style="background-color: #5e3fd3; color: white;" title="Ban Mentor">
                                                                                <i class="fas fa-trash"></i>
                                                                            </a>
                                                                        </div>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        </tbody>
                                    </c:forEach>

                                </table>

                            </div>
                        </div>
                    </div>
                </section>
                <!--Section: Quan Ly tai Khoan-->
            </div>
        </main>

        <!-- Modal Danh Sách Mentor's Request -->
        <div id="mentorListModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Mentor's Request List</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="table-responsive">
                            <table class="table table-hover text-nowrap">
                                <thead>
                                    <tr>
                                        <th class="text_page_head">Avatar</th>
                                        <th class="text_page_head">Name</th>
                                        <th class="text_page_head">Email</th>
                                        <th class="text_page_head">Date of Birth</th>
                                        <th class="text_page_head">CV</th>
                                        <th class="text_page_head">Request Time</th>
                                        <th class="text_page_head">Request Reason</th>
                                        <th class="text_page_head">Course</th>
                                        <th class="text_page_head">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${sessionScope.requestWaitingList}" var="request">
                                        <c:set var="username" value="${request.username}"/>
                                        <c:forEach items="${mentorList}" var="mentor">
                                            <c:if test="${mentor.username == username}">
                                                <tr>
                                                    <td>
                                                        <img src="data:image/jpeg;base64,${mentor.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%; object-fit: cover;">
                                                    </td>
                                                    <td class="text_page" style="font-weight: 500">${mentor.lastName}</td>
                                                    <td class="text_page" style="font-weight: 500">${mentor.mail}</td>
                                                    <td class="text_page" style="font-weight: 500">${mentor.dob}</td>
                                                    <td class="text_page" style="font-weight: 500">${mentor.cvPath}</td>
                                                    <td class="text_page" style="font-weight: 500">${request.requestTime}</td>
                                                    <td class="text_page" style="font-weight: 500">${request.requestReason}</td>
                                                    <td class="text_page" style="font-weight: 500">
                                                        <c:forEach items="${courseList}" var="course">
                                                            <c:if test="${course.courseId == request.courseId}">
                                                                ${course.courseName}
                                                            </c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        <form action="MangeRequest?action=Approve" method="post" style="display:inline;">
                                                            <input type="hidden" name="mentorId" value="${mentor.username}">
                                                            <input type="hidden" name="courseId" value="${request.courseId}"> <!-- Đảm bảo rằng courseId thuộc về request -->
                                                            <button type="submit" class="btn btn-success" title="Approve">
                                                                <i class="fa-solid fa-check"></i> <!-- Thay đổi thành biểu tượng dấu tick -->
                                                            </button>
                                                        </form>
                                                        <form action="MangeRequest?action=Reject" method="post" style="display:inline;">
                                                            <input type="hidden" name="mentorId" value="${mentor.username}">
                                                            <input type="hidden" name="courseId" value="${request.courseId}">
                                                            <button type="submit" class="btn btn-danger" title="Reject">
                                                                <i class="fa-solid fa-times"></i>
                                                            </button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </tbody>
                            </table>   
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>   
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.2.1/owl.carousel.min.js"></script>
        <script src="js/countdown.js"></script>
        <script src="js/clickevents.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js"></script>
        <script src="js/main.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://mdbootstrap.com/previews/ecommerce-demo/js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="https://mdbootstrap.com/previews/ecommerce-demo/js/popper.min.js"></script>
        <script type="text/javascript" src="https://mdbootstrap.com/previews/ecommerce-demo/js/bootstrap.js"></script>
        <script type="text/javascript" src="https://mdbootstrap.com/previews/ecommerce-demo/js/mdb.min.js"></script>
        <script type="text/javascript" src="https://mdbootstrap.com/previews/ecommerce-demo/js/mdb.ecommerce.min.js"></script>
        <script type="text/javascript" src="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/js/plugins/mdb-plugins-gathered.min.js"></script>

        <script src="js/calender.js"></script>
        <script type="text/javascript">
            // Function to populate the update modal
            function populateUpdateModal(courseId, courseName, categories, courseDescription) {
                document.getElementById('updateCourseId').value = courseId;
                document.getElementById('updateCourseName').value = courseName;
                document.getElementById('updateDescription').value = courseDescription;
            }
        </script>
    </body>
</html>
</html>
