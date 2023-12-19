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
                window.location.href = '/Patjo-booking-system/adminmain';
            }

            function toPresentationList() {
                window.location.href = '/Patjo-booking-system/presentationlist';
            }

            function validateForm() {
                var selectedCourse = document.querySelector('input[name="selectedList"]:checked');

                if (!selectedCourse) {
                    alert('Please select a list!');
                    return false; // Prevent form submission if no course is selected
                }

                return true; // Proceed with form submission
            }
        </script>
    </head>
    <body>
        <h1>Presentation Lists</h1>

        <c:choose>
            <c:when test="${not empty selectedPresentationList}">
                <h3>List number: ${listId}</h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Type of Session</th>
                            <th>Location</th>
                            <th>Time slot</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="booking" items="${selectedPresentationList}">
                            <tr>
                                <td>${booking.id}</td>
                                <td>${booking.typeOfSession}</td>
                                <td>${booking.location}</td>
                                <td>${booking.startTime}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not booking.isAvailable}">
                                            BOOKED
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br>
                <input type="button" value="Back" onclick="toPresentationList()">
            </c:when>
                
                <c:otherwise>
                    <form action="showpresentationlist" method="get" onsubmit="return validateForm()">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Choose</th>
                                    <th>List ID</th>
                                    <th>Course</th>
                                    <th>Creator</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="list" items="${activePresentationLists}">
                                    <tr>
                                        <td>
                                            <label>
                                                <input type="radio" name="selectedList" value="${list.listId}">
                                            </label>
                                        </td>
                                        <td>${list.listId}</td>
                                        <td>${list.courseName}</td>
                                        <td>${list.creatorName}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        <input type="submit" value="Show List">
                        <input type="button" value="Delete List" onclick="deleteList()">
                        <input type="button" value="Main view" onclick="toMainView()">
                    </form>
                </c:otherwise>
        </c:choose>
    </body>
</html>
