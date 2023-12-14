<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Patjo Booking System</title>
    
    <body>
    <h1>Users:</h1>
        <form id="userForm">
            <c:forEach var="user" items="${userList}">
                <label>
                    <input type="checkbox" name="selectedUsers" value="${user.userId}">
                    ${user.username}
                </label><br>
            </c:forEach>
        <input type="button" value="Add User" onclick="addUser()">
        <input type="button" value="Delete selected users" onclick="deleteUsers()">
    </form>
    
    <script>
        function addUser() {
            console.log('add user')
        }

        function deleteUsers() {
            // Get selected users
            const selectedUsers = document.querySelectorAll('input[name="selectedUsers"]:checked');
            const userIds = Array.from(selectedUsers).map(user => user.value);

            // Log the IDs of selected users
            console.log('Selected User IDs:', userIds);
        }
    </script>
    </body>
</html>
