<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patjo Booking System</title>
    </head>
    <body>
        <h1>Admin page</h1>

        <form action="users/showusers" method="get">
            <input type="submit" value="Handle Users" name="submit-button" />
        </form>
        <form action="presentationlist" method="get">
            <input type="submit" value="Handle Presentation Lists" name="submit-button" />
        </form>
        <form action="presentationlist/create" method="get">
            <input type="submit" value="Create Presentation List" name="submit-button" />
        </form>
    </body>
</html>
