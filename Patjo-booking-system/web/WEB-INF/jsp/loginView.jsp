<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patjo Booking System</title>
    <style>
        body {
            text-align: center;
            margin-top: 50px; /* Adjust margin as needed */
        }
        h2 {
            margin-bottom: 20px; /* Add space below the title */
        }
        form {
            display: inline-block;
            text-align: left; /* Align form elements to the left */
            margin-top: 20px; /* Adjust spacing between form and title */
            width: 250px; /* Set the width of the form */
            margin-left: auto;
            margin-right: auto;
        }
        input[type="email"],
        input[type="password"],
        input[type="submit"] {
            width: calc(100% - 16px); /* Adjust width to maintain consistency, considering padding */
            margin-bottom: 15px; /* Adjust spacing between elements */
            box-sizing: border-box; /* Include padding and border in the element's total width and height */
            padding: 8px; /* Add some padding for better appearance */
        }
    </style>
    <script>
        function validateEmail() {
            var emailInput = document.getElementById("username");
            var isValid = emailInput.checkValidity(); // Check if it's a valid email

            if (!isValid) {
                alert("Please enter a valid email address!"); // Show an alert if the email is invalid
                emailInput.value = ""; // Clear the incorrect input
                emailInput.focus(); // Focus on the email input field for correction
            }
        }
    </script>
</head>
<body>

<h2>Patjo Booking System</h2>

<form action="" method="post">
    <input type="email" id="username" name="username" placeholder="Email" required onblur="validateEmail()"><br>
    <input type="password" id="password" name="password" placeholder="Password" required><br>
    <input type="submit" value="Login">
</form>

</body>
</html>