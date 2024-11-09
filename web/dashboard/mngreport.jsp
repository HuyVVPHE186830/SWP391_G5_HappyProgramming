<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.*, dal.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Quản Lý Báo Cáo</title>
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

            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: red;
                color: white;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                font-size: 16px;
                font-family: Arial, sans-serif;
                z-index: 1000;
                opacity: 0;
                transform: scale(0.8) translateY(20px);
                transition: opacity 0.3s ease, transform 0.5s ease;
            }

            .notification.success {
                background-color: green;
            }

            .notification.reject {
                background-color: red;
            }

            .notification.error {
                background-color: yellow;
            }

            .hidden {
                display: none;
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
                                        <strong>Manage Report</strong>
                                    </h3>
                                </div>
                                <div class="col-lg-2"></div>

                            </div>
                        <div id="notification" class="notification hidden"></div>
                        <script>
                            const successMessage = '<c:out value="${succMsg}" />';
                            const errorMessage = '<c:out value="${failedMsg}" />';
                            const rejectMessage = '<c:out value="${rejectMsg}" />';

                            if (successMessage) {
                                showNotification(successMessage, 'success');
                            <% session.removeAttribute("succMsg"); %>
                            } else if (errorMessage) {
                                showNotification(errorMessage, 'error');
                            <% session.removeAttribute("failedMsg"); %>
                            } else if (rejectMessage) {
                                showNotification(rejectMessage, 'reject');
                            <% session.removeAttribute("rejectMsg"); %>
                            }

                            function showNotification(message, type) {
                                const notification = document.getElementById('notification');
                                notification.textContent = message;
                                notification.classList.remove('hidden', 'success', 'error', 'reject');
                                notification.classList.add(type);

                                setTimeout(() => {
                                    notification.style.opacity = '1';
                                    notification.style.transform = 'translateY(0)';
                                }, 100);

                                setTimeout(() => {
                                    notification.style.opacity = '0';
                                    notification.style.transform = 'scale(0.8) translateY(20px)';
                                    setTimeout(() => {
                                        notification.classList.add('hidden');
                                        notification.classList.remove(type); // Clean up class for next use
                                    }, 500);
                                }, 3000);
                            }
                        </script>
                        <div class="card-body" style="padding: 0">
                            <div class="table-responsive">
                                <table class="table table-hover text-nowrap">
                                    <thead>
                                        <tr>
                                            <th class="text_page_head">Comment</th>
                                            <th class="text_page_head">Reported By</th>
                                            <th class="text_page_head">Report Time</th>
                                            <th class="text_page_head">Report Type</th>
                                            <th class="text_page_head">Report Content</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${sessionScope.listReports}" var="t">
                                            <tr>
                                                <td class="text_page" style="font-weight: 500; max-width: 200px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">${t.comment.commentContent}</td>
                                                <td class="text_page" style="font-weight: 500">${t.user.username}</td>
                                                <td class="text_page" style="font-weight: 500">
                                                    <fmt:formatDate value="${t.reportTime}" pattern="dd-MM-yyyy, HH:mm"/>
                                                </td>
                                                <td class="text_page" style="font-weight: 500">${t.reportType.reportName}</td>
                                                <td class="text_page" style="font-weight: 500; max-width: 200px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">${t.reportContent}</td>
                                                <td>
                                                    <a class="btn btn-success" href="checkReport?id=${t.comment.commentId}"><i class="fa-solid fa-check"></i></a>
                                                    <form action="checkReport" method="post" style="display:inline;">
                                                        <input type="hidden" name="id" value="${t.comment.commentId}">
                                                        <input type="hidden" name="user" value="${t.user.username}">
                                                        <button class="btn btn-danger" type="submit"><i class="fa-solid fa-xmark"></i></button>
                                                    </form>
                                                </td>
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