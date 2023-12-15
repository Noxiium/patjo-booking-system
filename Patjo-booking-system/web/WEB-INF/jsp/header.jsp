<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Patjo Booking System</title>
<!DOCTYPE html>
    <style>
      

        #header {
            background-color: #d4d4d4; 
            padding: 10px;
            border-bottom: 2px solid #000;
        }
         .role {
            font-size: 18px;
            color: #ff0000;
            
        }

    </style>
</head>
<body>
    <div id="header">
        <p class="role"><b>${sessionScope.role}</b> </p>
        <p> You are logged in as: <b>${sessionScope.username}</b> </p>
        <a href="/Patjo-booking-system/logout">Logout</a>
        
    </div>
</body>
</html>
