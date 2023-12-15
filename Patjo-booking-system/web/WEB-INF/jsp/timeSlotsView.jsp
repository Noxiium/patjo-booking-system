<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patjo Booking System</title>
        <script>
            function toMainView() {
                window.location.href = '/Patjo-booking-system/mainPage';
            }
            
            function toSelectCourse(){
                 window.location.href = '/Patjo-booking-system/booking/showcourses';
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
        <h1>Select a time slot</h1>
   
        <c:if test="${not empty bookingList}">
            <form action="book-time-slot" method="post" onsubmit="return validateForm()">
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
                        <c:forEach var="booking" items="${bookingList}">
                            <tr>
                                <td>
                                    <label>
                                        <input type="radio" name="selectedTimeSlot" value="${booking.id}">
                                    </label>
                                </td>
                                <td>${booking.typeOfSession}</td>
                                <td>${booking.location}</td>
                                <td>${booking.startTime}</td>
                            </tr>
                    
                        </c:forEach>
                    </tbody>
                </table>
                <br>
                <input type="submit" value="Select time slot">
                <input type="button" value="Select another course" onclick="toSelectCourse()">
            </form>
        </c:if>
        <c:if test="${empty bookingList}">
            <p>No available time slots</p>
            <input type="button" value="Select another course" onclick="toSelectCourse()">
        </c:if>
        
    </body>
</html>
