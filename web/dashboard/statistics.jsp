<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Statistics</title>
        <link rel="icon" href="images/logo1.png"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet" type="text/css"/> 
        <link href="css/manager.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script> <!-- Include Chart.js -->
        <style>
            body {
                display: flex;
                margin: 0;
                font-family: Arial, sans-serif;
                background-color: #fbfbfb;
            }
            #sidebarMenu {
                width: 200px;
                background-color: #edf2fa;
            }
            .content {
                flex: 1;
                padding: 20px;
                max-width: calc(100% - 200px); /* Leave space for the sidebar */
            }
            .container {
                max-width: 1400px;
            }
            .text_page_head {
                font-size: 18px;
                font-weight: 600;
            }
            .card {
                margin: 20px 0;
            }
            /* Adjust chart size */
            .chart-container {
                width: 100%;
                max-width: 500px; /* Limit maximum width */
                margin: auto; /* Center the chart */
            }
        </style>
    </head>
    <body>

        <!-- Sidebar -->
        <jsp:include page="leftadmin.jsp"></jsp:include>

        <!-- Main Content Area -->
        <div class="content">
            <main>
                <div class="container pt-4">
                    <section class="mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h3 class="text-center mb-4"><strong>System Statistics</strong></h3>

                                <!-- User Roles Breakdown -->
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="text_page_head">User Roles Breakdown</h4>
                                        <div class="chart-container">
                                            <canvas id="userRolesChart"></canvas>
                                        </div>
                                    </div>
                                </div>

                                <!-- Courses and Participants -->
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="text_page_head">Courses and Participants</h4>
                                        <div class="chart-container">
                                            <canvas id="coursesChart"></canvas>
                                        </div>
                                    </div>
                                </div>

                                <!-- Blog Posts by User with Filters -->
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="text_page_head">Blog Posts by User</h4>

                                        <!-- Filters for Time Range and User -->
                                        <form action="<%= request.getContextPath() %>/StatisticsController" method="get" class="form-inline">
                                            <label for="startDate" class="mr-2">From:</label>
                                            <input type="date" id="startDate" name="startDate" class="form-control mr-2">

                                            <label for="endDate" class="mr-2">To:</label>
                                            <input type="date" id="endDate" name="endDate" class="form-control mr-2">

                                            <label for="user" class="mr-2">User:</label>
                                            <select id="user" name="user" class="form-control mr-2">
                                                <option value="">All Users</option>
                                                <c:forEach var="user" items="${allUsers}">
                                                    <option value="${user}">${user}</option>
                                                </c:forEach>
                                            </select>

                                            <button type="submit" class="btn btn-primary">Filter</button>
                                        </form>

                                        <div class="chart-container mt-4">
                                            <canvas id="blogPostsChart"></canvas>
                                        </div>
                                    </div>
                                </div>

                                <!-- Requests Chart with Filters -->
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="text_page_head">Requests Statistics</h4>

                                        <!-- Filters for Request Stats -->
                                        <form action="<%= request.getContextPath() %>/StatisticsController" method="get" class="form-inline">
                                            <label for="createdDate" class="mr-2">Created Date:</label>
                                            <input type="date" id="createdDate" name="createdDate" class="form-control mr-2">

                                            <label for="username" class="mr-2">Username:</label>
                                            <select id="username" name="username" class="form-control mr-2">
                                                <option value="">All Users</option>
                                                <c:forEach var="user" items="${allUsers}">
                                                    <option value="${user}">${user}</option>
                                                </c:forEach>
                                            </select>

                                            <label for="status" class="mr-2">Status:</label>
                                            <select id="status" name="status" class="form-control mr-2">
                                                <option value="">All Statuses</option>
                                                <option value="1">Approved</option>
                                                <option value="0">Pending</option>
                                                <option value="-1">Rejected</option>
                                            </select>

                                            <button type="submit" class="btn btn-primary">Filter</button>
                                        </form>

                                        <div class="chart-container mt-4">
                                            <canvas id="requestsChart"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </main>
        </div>

        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

        <!-- Chart.js Scripts -->
        <script>
            // User Roles Breakdown Chart
            const userRolesCtx = document.getElementById('userRolesChart').getContext('2d');
            const userRolesChart = new Chart(userRolesCtx, {
                type: 'pie',
                data: {
                    labels: [
                        <c:forEach var="entry" items="${userRolesStats}">
                            "${entry.key}",
                        </c:forEach>
                    ],
                    datasets: [{
                        data: [
                            <c:forEach var="entry" items="${userRolesStats}">
                                ${entry.value},
                            </c:forEach>
                        ],
                        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF']
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                    responsive: true
                }
            });

            // Courses and Participants Chart
            const coursesCtx = document.getElementById('coursesChart').getContext('2d');
            const coursesChart = new Chart(coursesCtx, {
                type: 'bar',
                data: {
                    labels: [
                        <c:forEach var="entry" items="${courseStats}">
                            "${entry.key}",
                        </c:forEach>
                    ],
                    datasets: [{
                        label: 'Participants',
                        data: [
                            <c:forEach var="entry" items="${courseStats}">
                                ${entry.value},
                            </c:forEach>
                        ],
                        backgroundColor: '#36A2EB'
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                    responsive: true,
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });

            // Blog Posts by User Chart
            const blogPostsCtx = document.getElementById('blogPostsChart').getContext('2d');
            const blogPostsChart = new Chart(blogPostsCtx, {
                type: 'bar',
                data: {
                    labels: [
                        <c:forEach var="entry" items="${userBlogStats}">
                            "${entry.key}",
                        </c:forEach>
                    ],
                    datasets: [{
                        label: 'Blog Posts',
                        data: [
                            <c:forEach var="entry" items="${userBlogStats}">
                                ${entry.value},
                            </c:forEach>
                        ],
                        backgroundColor: '#FF6384'
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                    responsive: true,
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });

            // Requests Statistics Chart
            const requestsCtx = document.getElementById('requestsChart').getContext('2d');
            const requestsChart = new Chart(requestsCtx, {
                type: 'bar',
                data: {
                    labels: [
                        <c:forEach var="entry" items="${requestStats}">
                            "${entry.key}",
                        </c:forEach>
                    ],
                    datasets: [{
                        label: 'Requests',
                        data: [
                            <c:forEach var="entry" items="${requestStats}">
                                ${entry.value},
                            </c:forEach>
                        ],
                        backgroundColor: '#FFCE56'
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                    responsive: true,
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });
        </script>
    </body>
</html>
