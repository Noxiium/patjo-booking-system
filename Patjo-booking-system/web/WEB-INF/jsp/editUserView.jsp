<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %> 
<%@ include file="sidebar.jsp" %>
<%
    String userRole = (String)session.getAttribute("role");
    if((userRole == null) || !userRole.equals("Admin")) {
        response.sendRedirect("/Patjo-booking-system/main");
    }
%>

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
            .table-container {
                display: flex;
            }

            .table-container table {
                margin-right: 20px; /* Adjust margin as needed */
            }

            .table-container h4 {
                margin-bottom: 1px; /* Adjust margin as needed */
                margin-top: 1px;
            }
             .deleted-container {
                background-color: lightcoral;
                padding: 8px;
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
        <h1>Admin Handle User Page</h1>
        <hr style="margin-top: 20px; margin-bottom: 20px;">
        
        
    <form id="editUser" method="post" action="editUser" autocomplete="off" style="margin-top: 20px;" onsubmit="return validateEditUserForms()">
    <input type="hidden" name="userId" value="${user.userId}">
    <h2> Edit User: </h2>
    <label for="username">Username:</label>
    <input type="email" id="username" name="username" value="${user.username}">

    <label for="password">Password:</label>
    <input type="text" id="password" name="password" value="${user.password}">

    <label for="isAdmin">Admin:</label>
    <input type="checkbox" id="isAdmin" name="isAdmin" ${user.isAdmin == 1 ? 'checked' : ''}>
    
    <select id="courseId" name="courseId" required multiple>
                <option value="" disabled selected>Select a course</option>
                    <c:forEach var="course" items="${courseList}">
                        <option value="${course.courseId}">${course.courseName}</option>
                    </c:forEach>
    </select>
    <input type="submit" value="Update user" class="button">
    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>
</form>

<div>
    <h3>Courses Currently Assigned to ${user.username}:</h3>
    <ul>
        <c:forEach var="usersCourses" items="${usersCourseList}">
            <li>${usersCourses.courseName}</li>
        </c:forEach>
    </ul>
</div>
    
        <script>
            function validateEditUserForms() {
                const editUserForm = document.getElementById('editUser');
                const username = editUserForm.querySelector('#username').value.trim();
                const password = editUserForm.querySelector('#password').value.trim();
                const course = editUserForm.querySelector('#courseId').value.trim();
                
                // Check if all fields in the "Add User" form are filled
                if (username === '' || password === '' || course === '') {
                    alert('Please fill in all fields');
                    return false; // Prevent form submission
                }

                // If the "Add User" form is validated, proceed to the next form
                return true;
            }
        </script>
    </body>
</html>
