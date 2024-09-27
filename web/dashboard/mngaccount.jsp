<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Quản Lý Tài Khoản</title>
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
        <jsp:include page="addAccount.jsp" />
        <jsp:include page="updateAccount.jsp" />
        <jsp:include page="deactivateAccount.jsp" />
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
                                        <strong>Manage Account</strong>
                                    </h3>
                                </div>
                                <div class="col-lg-2"></div>
                                <div class="col-lg-6" style="text-align: center; margin-top: 20px; margin-bottom: 20px;padding-top: 20px"F>
                                    <form action="<%= request.getContextPath() %>/ManagerAccount" method="post" style="display: flex; justify-content: center">
                                    <input name="valueSearch" value="${requestScope.searchValue != null ? requestScope.searchValue : ""}" id="searchId" type="text" placeholder="Search user name" style="width: 60%; padding: 4px 10px; border-radius: 15px">
                                    <button type="submit" style="border-radius: 50%; width: 40px; font-size: 18px; margin-left: 10px"><i class="fa fa-search"></i></button>
                                </form>
                            </div>
                        </div>
                        <c:if test="${error!=null }">
                            <div style="margin-top: 20px" class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </c:if>
                        <c:if test="${mess!=null }">
                            <div style="margin-top: 20px" class="alert alert-success" role="alert">
                                ${mess}
                            </div>
                        </c:if>
                        <div class="card-body" style="padding: 0">
                            <div class="table-responsive">
                                <table class="table table-hover text-nowrap">
                                    <thead>
                                        <tr>
                                            <th class="text_page_head">Username</th>
                                            <th class="text_page_head">First name</th>
                                            <th class="text_page_head">Last name</th>
                                            <th class="text_page_head">DOB</th>
                                            <th class="text_page_head">Mail</th>
                                            <th class="text_page_head">Created Date</th>
                                            <th class="text_page_head">Avatar Path</th>
                                            <th class="text_page_head">CV Path</th>
                                            <th class="text_page_head">Active Status</th>
                                            <th class="text_page_head">Is Verified</th>
                                            <th class="text_page_head">Role ID</th>
                                            <th class="text_page_head">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.listUsers}" var="t">
                                            <tr>
                                                <td class="text_page" style="font-weight: 500">${t.username}</td>
                                                <td class="text_page" style="font-weight: 500">${t.firstName}</td>
                                                <td class="text_page" style="font-weight: 500">${t.lastName}</td>
                                                <td class="text_page" style="font-weight: 500">${t.dob}</td>
                                                <td class="text_page" style="font-weight: 500">${t.mail}</td>
                                                <td class="text_page" style="font-weight: 500">${t.createdDate}</td>
                                                <td class="text_page" style="font-weight: 500">${t.avatarPath}</td>
                                                <td class="text_page" style="font-weight: 500">${t.cvPath}</td>
                                                <td class="text_page" style="font-weight: 500">${t.activeStatus}</td>
                                                <td class="text_page" style="font-weight: 500">${t.isVerified}</td>
                                                <td class="text_page" style="font-weight: 500">${t.roleId}</td>
                                                <td>

                                                    <a style="margin-left: 5px" href="#addEmployeeModal" class="btn btn-success" data-toggle="modal">
                                                        <i class="fa-solid fa-plus"></i>
                                                    </a>
                                                    <!-- Update Button -->
                                                    <button type="button" class="btn btn-primary" 
                                                            data-toggle="modal" 
                                                            data-target="#updateUserModal" 
                                                            onclick="populateUpdateModal('${t.username}', '${t.firstName}', '${t.lastName}', '${t.dob}', '${t.mail}', '${t.avatarPath}', '${t.cvPath}', ${t.activeStatus}, ${t.isVerified}, ${t.roleId})">
                                                        <i class="fa-solid fa-edit"></i>
                                                    </button>

                                                    <!-- Delete Button -->
                                                    <button type="button" class="btn btn-danger" 
                                                            data-toggle="modal" 
                                                            data-target="#deleteUserModal" 
                                                            onclick="populateDeleteModal('${t.username}')">
                                                        <i class="fa-solid fa-trash"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </section>
                <!--Section: Quan Ly tai Khoan-->
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
                                                                function populateUpdateModal(username, firstName, lastName, dob, email, avatarPath, cvPath, activeStatus, isVerified, roleId) {
                                                                    document.getElementById('updateUsername').value = username;
                                                                    document.getElementById('updateFirstName').value = firstName;
                                                                    document.getElementById('updateLastName').value = lastName;
                                                                    document.getElementById('updateDob').value = dob.split("T")[0]; // Format to YYYY-MM-DD
                                                                    document.getElementById('updateEmail').value = email;
                                                                    document.getElementById('updateAvatarPath').value = avatarPath;
                                                                    document.getElementById('updateCvPath').value = cvPath;
                                                                    document.getElementById('updateActiveStatus').checked = activeStatus;
                                                                    document.getElementById('updateIsVerified').checked = isVerified;
                                                                    document.getElementById('updateRoleId').value = roleId;
                                                                }
                                                                // Function to populate the update modal
                                                                function populateUpdateModal(username, firstName, lastName, dob, mail, avatarPath, cvPath, activeStatus, isVerified, roleId) {
                                                                    document.getElementById('updateUsername').value = username;
                                                                    document.getElementById('updateFirstName').value = firstName;
                                                                    document.getElementById('updateLastName').value = lastName;
                                                                    document.getElementById('updateDob').value = dob;
                                                                    document.getElementById('updateEmail').value = mail;
                                                                    document.getElementById('updateAvatarPath').value = avatarPath;
                                                                    document.getElementById('updateCvPath').value = cvPath;
                                                                    document.getElementById('updateActiveStatus').checked = activeStatus;
                                                                    document.getElementById('updateIsVerified').checked = isVerified;
                                                                    document.getElementById('updateRoleId').value = roleId;
                                                                }

                                                                // Function to populate the delete modal
                                                                function populateDeleteModal(username) {
                                                                    document.getElementById('deleteUsername').value = username;
                                                                }

        </script>
    </body>
</html>