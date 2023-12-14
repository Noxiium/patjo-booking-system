<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ultimate PATJO Quiz Game</title>
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
    <h1>Select a course</h1>

    <form action="showtimeslots" method="get" onsubmit="return validateForm()">
        <c:forEach var="course" items="${courseList}">
            <label>
                <input type="radio" name="selectedCourse" value="${course.courseId}">
                ${course.courseName}
            </label><br>
        </c:forEach>
        <input type="submit" value="Select course">
        <input type="button" value="Main view" onclick="toMainView()">
    </form>
</body>
</html>
