<%-- 
 Student - Select course to show available time slots
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
                padding: 5px;
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
                                <td>  ${course.courseName} </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br>
                <input type="submit" value="Select course" class="button">
            </form>

        </div>

    </body>
    <script>
        function validateForm() {
            var selectedCourse = document.querySelector('input[name="selectedCourse"]:checked');

            if (!selectedCourse) {
                alert('Please select a course!');
                return false; // Prevent form submission if no course is selected
            }

            return true; // Proceed with form submission
        }
    </script>
</html>
