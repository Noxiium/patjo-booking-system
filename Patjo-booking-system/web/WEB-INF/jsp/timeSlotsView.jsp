<%-- 
 Student - Select time slot view
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
            .disabled-label {
                color: #808080;
                font-weight: lighter;
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
            <h1>Select a time slot</h1>

            <c:if test="${not empty bookingList}">
                <form action="booktimeslot" method="post" onsubmit="return validateForm()">
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
                                            <input type="radio" name="selectedTimeSlot" value="${booking.id}" 
                                                   ${not booking.isAvailable ? 'disabled' : ''} 
                                                   class="${not booking.isAvailable ? 'disabled-label' : ''}">
                                            <span class="${not booking.isAvailable ? 'disabled-label' : ''}">
                                                ${booking.isAvailable ? '' : 'Full '}
                                            </span>
                                        </label>
                                    </td>
                                    <td class="${not booking.isAvailable ? 'disabled-label' : ''}">
                                        ${booking.typeOfSession}
                                    </td>
                                    <td class="${not booking.isAvailable ? 'disabled-label' : ''}">
                                        ${booking.location}
                                    </td>
                                    <td class="${not booking.isAvailable ? 'disabled-label' : ''}">
                                        ${booking.startTime}
                                    </td>
                                </tr>

                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <input type="submit" value="Select time slot" class="button">
                    <input type="button" value="Select another course" onclick="toSelectCourse()" class="button">
                </form>
            </c:if>
            <c:if test="${empty bookingList}">
                <p>No available time slots</p>
                <input type="button" value="Select another course" onclick="toSelectCourse()" class="button">
            </c:if>
        </div>
    </body>
    <script>
        function toSelectCourse() {
            window.location.href = '/Patjo-booking-system/showcourses';
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
</html>
