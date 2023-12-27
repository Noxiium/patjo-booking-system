<%-- 
 Student - Main Page
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %> 
<%@ include file="sidebar.jsp" %> 
<!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                font-family: "serif";
                background-color: #ffffff;
                color: #000000;
            }
            .main {
                margin-left: 200px;
            }
            .confirmation-container {
                background-color: lightgreen;
                padding: 8px;
            }
            .deleted-container {
                background-color: lightcoral;
                padding: 8px;
            }
            .list-section {
                margin-bottom: 20px;
            }

            .list-title {
                font-weight: bold;
                margin-bottom: 5px;
            }

            .list-item {
                margin-left: 20px;
            }

        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patjo Booking System</title>

    </head>
    <body>


        <div class="main">
            <h1>Student page</h1>
            <c:if test="${not empty confirmation}">
                <div class="confirmation-container">
                    <p>Confirmed: Your reserved time slot has been confirmed.</p>
                    <p>View all your booked time slots under "My Bookings".</p>
                </div>
                <br>
            </c:if>
            <c:if test="${not empty deletedtimeslot}">
                <div class="deleted-container">
                    <p>Booking Canceled: Your reserved time slot has been canceled.</p>
                    <p>View all your booked time slots under "My Bookings".</p>
                </div>
                <br>
            </c:if>
            <div class="list-section">
                <div class="list-title">My Bookings:</div>
                <div class="list-item">- View your booked time slots for a course.</div>
                <div class="list-item">- Remove booked time slots if needed.</div>
            </div>

            <div class="list-section">
                <div class="list-title">Show Course:</div>
                <div class="list-item">- Explore and book available time slots for courses you are enrolled in.</div>  
            </div>

        </div>
    </body>
</html>
