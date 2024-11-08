<%-- 
    Document   : request
    Created on : Oct 15, 2024, 11:57:35 AM
    Author     : Huy Võ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                background-color: #f4f4f4; /* Light gray background for a clean look */
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh; /* Full screen height */
            }

            .container {
                text-align: start;
                background: white;
                padding: 30px 20px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                max-width: 90%;
                width: 90%; /* Responsive for smaller screens */
                height: 70%;
            }

            h2 {
                text-transform: capitalize;
                color: black;
                font-size: 60px;
                margin-top: 100px;
                margin-bottom: 20px;
                text-align: center;
                font-style: italic;
            }

            a.button {
                display: inline-block;
                background-color: #5d3fd3; 
                color: white;
                padding: 14px 35px;
                font-size: 1em;
                border: none;
                border-radius: 5px;
                text-decoration: none;
                transition: background-color 0.3s ease;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            }

            a.button:hover {
                background-color: #541371; 
            }

            a.button:active {
                background-color: #3e8e41; 
                box-shadow: inset 0 2px 5px rgba(0, 0, 0, 0.2);
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <a href="home" class="button">Back</a>
            <h2>${message}</h2>
            <h2>Please Wait!</h2>
        </div>
    </body>
</body>
</html>
