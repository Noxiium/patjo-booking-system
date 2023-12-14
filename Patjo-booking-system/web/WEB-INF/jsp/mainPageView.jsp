<%-- 
    Document   : mainPageView
    Created on : Dec 13, 2023, 6:57:22 PM
    Author     : Indiana Johan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Student page!</h1>
        
        <p>Welcome, ${sessionScope.username}!</p>
        <p>Your User ID is: ${sessionScope.userId}</p>
        
        <form action="booking" method="get">
    <input type="submit" value="Show Time Slots" name="submit-button" />
</form>
    </body>
</html>
