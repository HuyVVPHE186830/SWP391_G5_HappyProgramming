<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Sidebar -->
<nav id="sidebarMenu" class="collapse d-lg-block sidebar collapse bg-black" style="padding: 0px; width: 240px; background-color: #edf2fa">
    <div class="position-sticky" >
        <div class="list-group list-group-flush mx-3 mt-4" style="margin: 0">
            <a href="<%= request.getContextPath() %>/StatisticsController" class="list-group-item list-group-item-action" aria-current="true" style="margin-top: 10px;">
                <i style="margin-right: 10px; font-size: 18px" class="fas  fa-bar-chart fa-fw me-3"></i>
                <span style="font-size: 16px; font-weight: 600">Main dashboard</span>
            </a>
            <a href="<%= request.getContextPath() %>/ManagerAccount" class="list-group-item list-group-item-action" style="margin-top: 10px">
                <i style="margin-right: 10px; font-size: 18px" class="fas fa-user-circle fa-fw me-3"></i>
                <span style="font-size: 16px; font-weight: 600">Accounts</span>
            </a>
            <a href="<%= request.getContextPath() %>/managerBlog" class="list-group-item list-group-item-action" style="margin-top: 10px">
                <i style="margin-right: 10px; font-size: 18px" class="fas  fa-file-text fa-fw me-3"></i>
                <span style="font-size: 16px; font-weight: 600">Blogs</span>
            </a>
            <a href="<%= request.getContextPath() %>/ManagerCourse" class="list-group-item list-group-item-action" style="margin-top: 10px">
                <i style="margin-right: 10px; font-size: 18px" class="fa-solid fa-laptop-code"></i>
                <span style="font-size: 16px; font-weight: 600">Courses</span>
            </a>
            <a href="<%= request.getContextPath() %>/MangeRequest" class="list-group-item list-group-item-action" style="margin-top: 10px">
                <i style="margin-right: 10px; font-size: 18px" class="fa-solid fa-check-square"></i>
                <span style="font-size: 16px; font-weight: 600">Mentor Request</span>
            </a>
            <a href="<%= request.getContextPath() %>/ManageReport" class="list-group-item list-group-item-action" style="margin-top: 10px">
                <i style="margin-right: 10px; font-size: 18px" class="fa-solid fa-circle-exclamation"></i>
                <span style="font-size: 16px; font-weight: 600">Reports</span>
            </a>
            <a href="logout.jsp" class="list-group-item list-group-item-action" style="margin-top: 10px">
                <i style="margin-right: 10px; font-size: 18px" class="fa-solid  fa-sign-out"></i>
                <span style="font-size: 16px; font-weight: 600">Log Out</span>
            </a>
        </div>
    </div>
</nav>