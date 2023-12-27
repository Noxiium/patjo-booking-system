<%-- 
 Student - My booked Time Slots view
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %> 
<%@ include file="sidebar.jsp" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patjo Booking System</title>
        <style>
            body {
                font-family: "serif";
                background-color: #ffffff;
                color: #000000;
            }
            .main {
                margin-left: 200px;

            }
            td {
                padding: 4px;

            }
            th {
                padding: 4px;
                text-align: center;
            }
            .button {
                padding: 8px 16px;
                font-size: 14px;
                cursor: pointer;
                background-color: #f5f5f5;
                color: #333;
                border: 1px solid #ccc;
                border-radius: 4px;
                transition: background-color 0.3s ease;
            }

            .button:hover {
                background-color: #e0e0e0;
            }
        </style>
    </head>
    <body>
        <div class="main">
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
                                <th>Course</th>
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
                                     <td>
                                        ${booking.courseName}
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <input type="submit" value="Delete booked time slot" class="button">
                </c:if>
            </form>
        </div>
    </body>
    <script>

        function validateForm() {
            var selectedCourse = document.querySelector('input[name="selectedTimeSlot"]:checked');

            if (!selectedCourse) {
                alert('Please select a time slot!');
                return false; // Prevent form submission if no course is selected
            }

            return true; // Proceed with form submission
        }
    </script>
</html>
