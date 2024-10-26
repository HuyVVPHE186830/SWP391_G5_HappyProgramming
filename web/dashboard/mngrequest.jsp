<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.*, dal.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Khóa Học</title>
    <link rel="icon" href="images/logo1.png"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet" type="text/css"/> 
    <link href="css/manager.css" rel="stylesheet" type="text/css"/>
    <style>
        body {
            margin: 0;
            padding: 0;
        }
        .text_page_head {
            font-size: 18px;
            font-weight: 600;
        }
        .text_page {
            font-size: 14px;
            font-weight: 600;
        }
        .modal-dialog {
            max-width: 1400px;
        }
        .modal-content {
            padding: 20px;
        }
        .badge {
            background-color: red;
            color: white;
            border-radius: 50%;
            padding: 2px 6px;
            font-size: 12px;
            position: absolute;
            top: -5px;
            right: -10px;
        }
        .icon-container {
            position: relative;
        }
    </style>
</head>
<body>

    <!-- Main Navigation -->
    <header>
        <jsp:include page="header_right.jsp"></jsp:include>
    </header>
    
    <div class="container-fluid">
        <div class="row">
            <!-- Left Navigation -->
            <div class="col-md-2">
                <jsp:include page="leftadmin.jsp"></jsp:include>
            </div>

            <!-- Main Content -->
            <div class="col-md-10">
                <main>
                    <div class="container pt-4" style="max-width: 2400px">
                        <section class="mb-4">
                            <div class="card">
                                <div class="row">
                                    <div class="col-sm-4 text-center my-3">
                                        <h3 class="mb-0"><strong>Mentor List</strong></h3>
                                    </div>
                                    <div class="col-lg-2"></div>
                                    <div class="col-lg-6 text-center my-3">
                                        <form action="<%= request.getContextPath() %>/ManagerCourse" method="post" style="display: flex; justify-content: center">
                                            <input name="valueSearch" value="${requestScope.searchValue != null ? requestScope.searchValue : ""}" id="searchId" type="text" placeholder="Search course" style="width: 60%; padding: 4px 10px; border-radius: 15px">
                                            <button type="submit" style="border-radius: 50%; width: 40px; font-size: 18px; margin-left: 10px"><i class="fa fa-search"></i></button>
                                        </form>
                                    </div>
                                </div>

                                <c:if test="${not empty succMsg}">
                                    <div class="alert alert-success" role="alert">${succMsg}</div>
                                    <c:remove var="succMsg" scope="session"/>
                                </c:if>
                                <c:if test="${not empty failedMsg}">
                                    <div class="alert alert-danger" role="alert">${failedMsg}</div>
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
                                                    <th class="text_page_head">Date of Birth</th>
                                                    <th class="text_page_head">CV</th>
                                                    <th class="text_page_head">Action</th>
                                                    <th>
                                                        <div class="icon-container">
                                                            <a style="margin-left: 5px" href="#mentorListModal" class="btn btn-success" data-toggle="modal">
                                                                <i class="fa-solid fa-list"></i>
                                                                <span class="badge">${fn:length(mentorList)}</span>
                                                            </a>
                                                        </div>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${mentorList}" var="t">
                                                    <tr>
                                                        <td>
                                                            <img src="data:image/jpeg;base64,${t.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%; object-fit: cover;">
                                                        </td>
                                                        <td class="text_page" style="font-weight: 500">${t.lastName}</td>
                                                        <td class="text_page" style="font-weight: 500">${t.mail}</td>
                                                        <td class="text_page" style="font-weight: 500">${t.dob}</td>
                                                        <td class="text_page" style="font-weight: 500">${tCVPath}</td>
                                                        <td class="text_page" style="font-weight: 500"></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </main>
            </div>
        </div>
    </div>

    <!-- Modal Danh Sách Mentor -->
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
                                    <th class="text_page_head">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${mentorList}" var="mentor">
                                    <tr>
                                        <td>
                                            <img src="data:image/jpeg;base64,${mentor.avatarPath}" alt="Avatar" class="avatar-image" style="width:40px; height:40px; border-radius:50%; object-fit: cover;">
                                        </td>
                                        <td class="text_page" style="font-weight: 500">${mentor.lastName}</td>
                                        <td class="text_page" style="font-weight: 500">${mentor.mail}</td>
                                        <td class="text_page" style="font-weight: 500">${mentor.dob}</td>
                                        <td class="text_page" style="font-weight: 500">${mentor.cvPath}</td>
                                        <td>
                                            <form action="<%= request.getContextPath() %>/approveMentor" method="post">
                                                <input type="hidden" name="mentorId" value="${mentor.id}">
                                                <input type="submit" class="btn btn-success" value="Approve">
                                            </form>
                                        </td>
                                    </tr>
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

    <!-- Các script khác -->
    <script>
        function resetForm() {
            document.getElementById("addCourseForm").reset();
        }
        
        // Function to populate the update modal
        function populateUpdateModal(courseId, courseName, categories, courseDescription) {
            document.getElementById('updateCourseId').value = courseId;
            document.getElementById('updateCourseName').value = courseName;
            document.getElementById('updateDescription').value = courseDescription;
        }
    </script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>   
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="js/countdown.js"></script>
    <script src="js/clickevents.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>