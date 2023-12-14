<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
    <h1>Admin page!</h1>
    
    <p>Welcome, ${username}!</p>
    <p>Your User ID is: ${userId}</p>
    
    <form action="users" method="get">
    <input type="submit" value="Handle Users" name="submit-button" />
</form>
</body>
</html>
