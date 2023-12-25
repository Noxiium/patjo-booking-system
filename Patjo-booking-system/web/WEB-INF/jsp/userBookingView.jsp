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
        .red {
            color: red;
        }
        .side-by-side {
            display: inline-block;
            margin-right: 20px;
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
        .disabled-label {
            color: #808080;
            font-weight: lighter;
        }
   
            </style>
    </head>
    <body>
        <div class="main">
        <h1> Manage User Booking</h1>
            <h4 class="red">Selected student: ${userName}</h4>
            
        
        <h3>Student's Active Booked Time Slots</h3>
         <c:if test="${empty userBookings}">
                No booked time slots.
                <br>
                <br>
         </c:if>
            <c:if test="${not empty userBookings}">
                <form action="deletetimeslot" method="post" onsubmit="return validateForm()">
                    <input type="hidden" name="userId" value="${userId}" />
                    <input type="hidden" name="userName" value="${userName}" />
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
                    <input type="submit" value="Delete booked time slot" class="button">
                </c:if>
            </form>
                
                <hr>
                
                <h3>Assign a new time slot to student</h3>
                <c:if test="${empty bookingList}">
                     No available time slots for selected student.
                <br>
                <br>
                </c:if>
                 <c:if test="${not empty bookingList}">
                <form action="assigntimeslot" method="post" onsubmit="return validateForm()">
                    <input type="hidden" name="userId" value="${userId}" />
                    <input type="hidden" name="userName" value="${userName}" />
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
                                    <td class="${not booking.isAvailable ? 'disabled-label' : ''}">
                                        ${booking.courseName}
                                    </td>
                                </tr>

                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <input type="submit" value="Assign time slot" class="button">
                </form>
                 </c:if>
                
            </div>
        </body>
    <script>
        function validateForm() {
            var selectedTimeSlot = document.querySelector('input[name="selectedTimeSlot"]:checked');

            if (!selectedTimeSlot) {
                alert('Please select a time slot!');
                return false; // Prevent form submission if no course is selected
            }

            return true; // Proceed with form submission
        }
    </script>
    </body>
</html>