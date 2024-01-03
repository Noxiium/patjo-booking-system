<%-- 
 Admin - Select student to delete/assign time slots
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %> 
<%@ include file="sidebar.jsp" %>

<%
    String userRole = (String)session.getAttribute("role");
    if(!userRole.equals("Admin")) {
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
        </style>
    </head>
    <body>
        <div class="main">
        <h1>Manage User Booking</h1>
         <h3>Select a student to manage bookings</h3>
         <form id="userForm" action="showuserbooking" method="get" onsubmit="return validateForm()">
                <select id="userId" name="userId" required>
                    <option value="" disabled selected>Select student</option>
                    <c:forEach var="user" items="${userList}">
                        <option value="${user.userId}">${user.username}</option>
                    </c:forEach>
                </select>
              <input type="hidden" id="username" name="username" value="">
             <input type="submit" value="Select user" class="button">
         </form>
        </div>
    </body>
    <script>
          function validateForm() { 
            var userId = document.getElementById("userId").value;
            var username = document.getElementById("userId").options[document.getElementById("userId").selectedIndex].text;

       
            document.getElementById("username").value = username;

                if (userId === "") {
                    alert("Please select a student");
                    return false;
                } else {
                    return true;
                }
            }
    </script>
</html>
