<%-- 
    Document   : mainPageView
    Created on : Dec 13, 2023, 6:57:22 PM
    Author     : Indiana Johan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %> 
<!DOCTYPE html>
<html>
    <head>
        <style>
            .confirmation-container {
                background-color: lightgreen;
                padding: 8px;
            }
            .deleted-container {
                background-color: lightcoral;
                padding: 8px;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patjo Booking System</title>

    </head>
    <body>

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

        <form action="showcourses" method="get">
            <input type="submit" value="Show Courses"/>
        </form>
        <form action="mybookings" method="post">
            <input type="submit" value=" My Bookings"/>
        </form>

    </body>
</html>
