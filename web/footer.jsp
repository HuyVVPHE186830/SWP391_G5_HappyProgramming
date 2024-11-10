<%-- 
    Document   : footer
    Created on : Sep 20, 2024, 12:44:39 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="CSS/footer.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Poppins:wght@400;600&display=swap" rel="stylesheet">
        <style>
            .footer {
                font-family: 'Roboto', sans-serif; /* Main font for the footer */
                color: #452cbf;
                padding: 20px;
            }

            .footer h5 {
                font-family: 'Poppins', sans-serif; /* Font for headings */
                color: #333;
            }

            .footer p {
                font-family: 'Roboto', serif; /* Font for paragraph text */
            }

            .footer a {
                font-family: 'Roboto', sans-serif; /* Font for links */
                text-decoration: none;
                color: #452cbf;
            }

            .footer .back-to-top {
                font-family: 'Poppins', sans-serif; /* Font for the "back to top" button */
            }

        </style> 
    </head>
    <body>
        <!-- footer.jsp -->
        <div class="footer" style="color: #452cbf; padding: 20px;">
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <h5 style="color: #333">About Us</h5>
                        <hr class=" mb-2 mt-0 d-inline-block mx-auto w-25">
                        <div class="logo">
                            <img src="img/logocolor.png" width="50px" alt="alt"/>
                            <a href="home" class="icon"><span style="font-size: 22px">LEARNING CONNECT SITE</span></a>
                        </div>
                        <p style="margin-top: 30px; font-size: 16px">We provide quality courses to help you achieve your learning goals. Join us and explore new knowledge today.</p>
                    </div>

                    <div class="col-md-2">
                    </div>

                    <div class="col-md-3">
                        <h5 style="color: #333">Quick Links</h5>
                        <hr class=" mb-2 mt-0 d-inline-block mx-auto w-25">
                        <ul style="list-style: none; padding: 0;">
                            <li style="margin-bottom: 8px;"><a href="home" style="text-decoration: none;">Home</a></li>
                            <li style="margin-bottom: 8px;"><a href="allCourse" style="text-decoration: none;">Courses</a></li>
                            <li style="margin-bottom: 8px;"><a href="" style="text-decoration: none;">About Us</a></li>
                            <li><a href="" style="text-decoration: none;">Contact</a></li>
                        </ul>
                    </div>

                    <div class="col-md-3">
                        <h5 style="color: #333">Contact Us</h5>
                        <hr class=" mb-2 mt-0 d-inline-block mx-auto w-25">
                        <p>Email: yeudangyeunuoc2424@gmail.com</p>
                        <p>Phone: </p>
                        <p>Address: </p>
                        <div>
                            <a href="https://www.facebook.com/huyvo0234" target="_blank" style="color: #452cbf; margin-right: 10px; font-size: 24px;">
                                <i class="fa-brands fa-facebook"></i>
                            </a>
                            <!--                            <a href="https://www.twitter.com" target="_blank" style="color: white; margin-right: 10px;">
                                                            <i class="fa fa-twitter"></i>
                                                        </a>
                                                        <a href="https://www.instagram.com" target="_blank" style="color: white; margin-right: 10px;">
                                                            <i class="fa fa-instagram"></i>
                                                        </a>
                                                        <a href="https://www.linkedin.com" target="_blank" style="color: white;">
                                                            <i class="fa fa-linkedin"></i>
                                                        </a>-->
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12 text-center" style="margin-top: 15px;">
                        <p>&copy; 2024 LEARNING CONNECT SITE - All Rights Reserved</p>
                    </div>
                </div>

                <button onclick="scrollToTop()" class="back-to-top" style="position: fixed; bottom: 20px; left: 20px; display: none; background-color: #452cbf; color: white; border: none; border-radius: 50%; padding: 5px 12px; font-size: 15px; cursor: pointer;">
                    &uarr;
                </button>
            </div>
        </div>

        <script>
            window.onscroll = function () {
                const backToTopButton = document.querySelector('.back-to-top');
                if (document.body.scrollTop > 200 || document.documentElement.scrollTop > 200) {
                    backToTopButton.style.display = 'block';
                } else {
                    backToTopButton.style.display = 'none';
                }
            };

            function scrollToTop() {
                window.scrollTo({
                    top: 0,
                    behavior: 'smooth'
                });
            }
        </script>

    </body>
</html>
