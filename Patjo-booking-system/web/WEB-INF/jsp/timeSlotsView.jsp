<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patjo Booking System</title>
        <script>
            function toMainView() {
                window.location.href = '/Patjo-booking-system/mainPage';
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
        <circle>

        <c:if test="${not empty bookingList}">
            <form action="book-time-slot" method="post" onsubmit="return validateForm()">
                <c:forEach var="booking" items="${bookingList}">
                    <label>
                        <input type="radio" name="selectedTimeSlot" value="${booking.id}">
                        ${booking.typeOfSession}
                        ${booking.location}
                        ${booking.startTime}
                    </label><br>
                </c:forEach>
                <input type="submit" value="Select time slot">
            </form>
        </c:if>
        <c:if test="${empty bookingList}">
            <p>No available time slots</p>
        </c:if>
        <input type="button" value="Main view" onclick="toMainView()">
    </body>
</html>
