<%-- 
 Admin - Create / Delete user
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
        <c:if test="${not empty deleteduser}">
            <div class="deleted-container">
                <p>User(s) deleted: The selected user accounts have been successfully removed.</p>
                <p>Any associated reservations have been canceled. </p>

            </div>
            <br>
        </c:if>
        <form id="addUser" method="post" action="addUser" autocomplete="off" style="margin-top: 20px;" onsubmit="return validateAddUserForms()">
            <h2> Create new User: </h2>
            <label for="username">Username:</label>
            <input type="email" id="username" name="username">

            <label for="password">Password:</label>
            <input type="text" id="password" name="password">

            <label for="isAdmin">Admin:</label>
            <input type="checkbox" id="isAdmin" name="isAdmin">
            <input type="submit" value="Create new user" class="button">
            <% if (request.getAttribute("errorMessage") != null) {%>
            <div class="error-message">
                <%= request.getAttribute("errorMessage")%>
            </div>
            <%          }%>
        </form>

        <hr style="margin-top: 20px; margin-bottom: 20px;">
        <div>
        <form id="selectUsersForm" method="post" action="removeUsers" onsubmit="return validateForm()">
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
            <input id="deleteButton" type="submit" value="Delete selected users" style="margin-top: 10px;" class="button">
        </form>
        </div>
         </div>
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
            function validateForm() {
                var radios = document.getElementsByName('userIds');
                var checked = false;
                for (var i = 0; i < radios.length; i++) {
                    if (radios[i].checked) {
                        checked = true;
                        break;
                    }
                }
                if (!checked) {
                    alert('You have not selected any users');
                    return false; // Prevent form submission if no subject is selected
                }

                if (confirmDelete()) {

                } else {

                    return false;
                }
                return true; // Proceed with form submission
            }
            function confirmDelete() {
                var confirmation = confirm('Are you sure you want to delete the selected user(s)?');
                return confirmation;
            }

            function validateAddUserForms() {
                const addUserForm = document.getElementById('addUser');
                const username = addUserForm.querySelector('#username').value.trim();
                const password = addUserForm.querySelector('#password').value.trim();

                // Check if all fields in the "Add User" form are filled
                if (username === '' || password === '') {
                    alert('Please fill in all fields');
                    return false; // Prevent form submission
                }

                // If the "Add User" form is validated, proceed to the next form
                return true;
            }
        </script>
    </body>
</html>
