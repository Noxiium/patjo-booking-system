<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patjo Booking System</title>
        <style>
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
        </style>
    </head>
    <body>
        <h1>Admin Handle User Page</h1>
        <hr style="margin-top: 20px; margin-bottom: 20px;">

        <form id="addUser123" method="post" action="addUser" autocomplete="off" style="margin-top: 20px;">
            <h2> Add User: </h2>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username">
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password">
            
            <label for="isAdmin">Admin:</label>
            <input type="checkbox" id="isAdmin" name="isAdmin">
            <input type="submit" value="Create new user">
        </form>

        <hr style="margin-top: 20px; margin-bottom: 20px;">

        <form id="selectUsersForm" method="post" action="removeUsers">
            <h2> Select and remove users: </h2>
            <div class="table-container">
                <div>
                    <h4>Admin Users</h4>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Select</th>
                                <th>Username</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="adminUser" items="${adminList}">
                                <tr>
                                    <td><input type="checkbox" name="userIds" value="${adminUser.userId}"></td>
                                    <td>${adminUser.username}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div>
                    <h4>Non-Admin Users</h4>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Select</th>
                                <th>Username</th>
                                <!-- Add more columns if needed -->
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="nonAdminUser" items="${nonAdminList}">
                                <tr>
                                    <td><input type="checkbox" name="userIds" value="${nonAdminUser.userId}"></td>
                                    <td>${nonAdminUser.username}</td>
                                    <!-- Add more cells if needed -->
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <input type="submit" value="Delete selected users" style="margin-top: 10px;">
        </form>

        <script>
            function addUser() {
                console.log('add user');
            }

            function deleteSelectedUsers() {
                // Get selected users
                const selectedUsers = document.querySelectorAll('input[name="selectedUsers"]:checked');
                const userIds = Array.from(selectedUsers).map(user => user.value);

                // Log the IDs of selected users
                console.log('Selected User IDs:', userIds);

                // Create a hidden input field to hold user IDs
                const hiddenInput = document.createElement('input');
                hiddenInput.type = 'hidden';
                hiddenInput.name = 'userIds';
                hiddenInput.value = userIds.join(','); // Convert array to comma-separated string
                document.getElementById('selectUsersForm').appendChild(hiddenInput);
            }
        </script>
    </body>
</html>
