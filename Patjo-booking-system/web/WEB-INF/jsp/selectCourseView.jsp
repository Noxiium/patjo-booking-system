
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
        
        function validateForm() {
            var selectedCourse = document.querySelector('input[name="selectedCourse"]:checked');
            
            if (!selectedCourse) {
                alert('Please select a course!');
                return false; // Prevent form submission if no course is selected
            }
                   
            return true; // Proceed with form submission
        }
    </script>
</head>
<body>
    <h1>Select a course to show available time slots</h1>

    <form action="showtimeslots" method="get" onsubmit="return validateForm()">
        <table border="1">
            <thead>
                <tr>
                    <th>Choose</th>
                    <th>Course Name</th>
                </tr>
            </thead>
            <tbody>
        <c:forEach var="course" items="${courseList}">
            <tr>
                <td>
                    <label>
                        <input type="radio" name="selectedCourse" value="${course.courseId}">
                    </label>
                </td>
                <td>  ${course.courseName}</td>
            </tr>
        </c:forEach>
            </tbody>
        </table>
        <br>
        <input type="submit" value="Select course">
        <input type="button" value="Main view" onclick="toMainView()">
    </form>

    
    
</body>
</html>
