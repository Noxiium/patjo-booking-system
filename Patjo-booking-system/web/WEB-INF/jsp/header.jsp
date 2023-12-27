
<%-- 
 HEADER
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Patjo Booking System</title>
        <style>
            #header {
                background-color: #f2f2f2;
                padding: 10px;
                border: 2px solid #ddd;
                box-sizing: border-box;
                color: #333;
                height: 120px; 
                margin-top: 3px;
            }

            .role {
                font-size: 18px;
                color: #ff0000;
            }

            #header a {
                color: #333;
                text-decoration: none;
                transition: background-color 0.3s ease;
            }

            #header a:hover {
                color: #555;
                background-color: #ddd;
            }

        </style>
    </head>
    <body>
        <div id="header">
             <p class="role"><b>${sessionScope.role}</b></p>
            You are logged in as: <b>${sessionScope.username}</b>
            <br>
            <a href="/Patjo-booking-system/logout">Logout</a>
           
        </div>
    </body>
</html>
