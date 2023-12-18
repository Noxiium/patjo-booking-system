<%-- 
    Document   : bookedTimeSlotsView
    Created on : 18 Dec 2023, 10:43:50
    Author     : patricialagerhult
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patjo Booking System</title>
        <style>
            td {
                padding: 4px;

            }
            th {
                padding: 4px;
                text-align: center;
            }
        </style>
        <script>
            function toMainView() {
                window.location.href = '/Patjo-booking-system/main';
            }
            function validateForm() {
                var selectedCourse = document.querySelector('input[name="selectedTimeSlot"]:checked');

                if (!selectedCourse) {
                    alert('Please select a time slot!');
                    return false; // Prevent form submission if no course is selected
                }

                return true; // Proceed with form submission
            }
        </script>
    </head>
    <body>
        <h1>My booked Time Slots!</h1>
        <c:if test="${empty userBookings}">
            No booked time slots.
            <br>
            <br>
        </c:if>
        <c:if test="${not empty userBookings}">
            <form action="removetimeslot" method="post" onsubmit="return validateForm()">
                <table border="1">
                    <thead>
                        <tr>
                            <th>Choose</th>
                            <th>Type of Session</th>
                            <th>Location</th>
                            <th>Time slot</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="booking" items="${userBookings}">
                            <tr>
                                <td>
                                    <label>
                                        <input type="radio" name="selectedTimeSlot" value="${booking.id}">
                                    </label>
                                <td> 
                                    ${booking.typeOfSession}
                                </td>
                                <td>
                                    ${booking.location}
                                </td>
                                <td>
                                    ${booking.startTime}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br>
                <input type="submit" value="Remove booked time slot">
            </c:if>
            <input type="button" value="Main view" onclick="toMainView()">
        </form>
    </body>
</html>
