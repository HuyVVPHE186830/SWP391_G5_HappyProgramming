<%-- 
    Document   : homeuser.jsp
    Created on : Sep 18, 2024, 4:25:30 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <style>

            /* MIDDLE */
            .header-content-middle {
                padding: 100px 10% 40px;
                background-color: #edf2fa;
            }

            .header-content-middle h1 {
                font-size: 54px;
            }

            .header-content-middle p {
                font-size: 20px;
                width: 500px;
                margin-bottom: 40px;
            }

            .search-bar {
                display: flex;
                align-items: center;
                width: 100%;
                max-width: 500px;
                border: 1px solid #ccc;
                border-radius: 10px;
                overflow: hidden;
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
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 20px;
                cursor: pointer;
                border-radius: 0 10px 10px 0;
                font-size: 16px;
                transition: all 1s ease;
            }

            .button-submit:hover {
                background-color: #0033cc;
            }

            .sale {
                position: relative;
                width: 100%;
                height: 500px;
                overflow: hidden;
            }

            .sale img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                display: block;
            }
            
            .sale h1 {
                position: absolute;
                top: 20px;
                left: 90px;
                transform: translate(-50%, -50%);
                color: white;
                text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
                font-size: 2em;
                margin: 0;
            }
        </style>
    </head>
    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- MIDDLE -->
        <div class="content-middle">

            <!-- HEAD CONTENT MIDDLE -->
            <div class="header-content-middle">
                <!-- TEXT -->
                <h1>Ở ???</h1>
                <h1><a href = "changePass.jsp">Học gì cũng có</a></h1>
                <p>Con đường hiệu quả nhất để bạn có thêm kiến thức và năng lực trong mọi lĩnh vực. Tham gia học cùng phương pháp học tập hoàn toàn mới từ FUNiX ngay!</p>

                <!-- SEARCH BAR -->
                <form action="#" class="search-bar">
                    <input type="text" class="input-submit" placeholder="Bạn muốn học gì" name="search">
                    <input type="submit" class="button-submit" value="Tìm kiếm">
                </form>
            </div>

            <!-- SALE -->
            <div class="sale">
                <h1>SALE 30%</h1>
                <img src="https://zpsocial2-f4-org.zadn.vn/9790ea297aa49bfac2b5.jpg" alt="alt"/>
            </div>

            <!-- MENTOR SLIDE -->
            <div>
                <h1>MENTOR</h1>
            </div>

            <!-- COURSES SLIDE -->
            <div>
                <h1>COURSES</h1>
            </div>
        </div>
    </body>
</html>
