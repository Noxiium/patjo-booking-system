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
            <h1>Create Presentation List</h1>
            <form id="bookingForm" action="savepresentationlist" method="post" onsubmit="return validateForm()">
                <select id="courseId" name="courseId" required>
                    <option value="" disabled selected>Select a course</option>
                    <c:forEach var="course" items="${courseList}">
                        <option value="${course.courseId}">${course.courseName}</option>
                    </c:forEach>
                </select>

                <br>
                <br>
                <div id="bookingRows"></div>

                <button type="button" onclick="addRow()" class="button">Add Row</button>
                <input type="submit" value="Submit" class="button">
            </form>
        </div>
        <script>
            window.onload = function () {
                addRow();
            };

            function validateForm() { 
                var courseId = document.getElementById("courseId").value;

                if (courseId === "") {
                    alert("Please select a course before submitting the form.");
                    return false;
                } else {
                    return true;
                }
            }

            function addRow() {
                console.log("addrow!");
                var bookingRows = document.getElementById("bookingRows");
                var newRow = document.createElement("div");

                newRow.innerHTML = `
                    <label>Type of Session:</label>
                    <input type="text" name="typeOfSession" required>

                    <label>Location:</label>
                    <input type="text" name="location" required>

                    <label>Start Time:</label>
                    <input type="datetime-local" name="startTime" required>
        
                    <button type="button" onclick="removeRow(this)">X</button>
    
                    <hr>
                `;

                bookingRows.appendChild(newRow);
            }

            function removeRow(button) {
                var row = button.parentNode;
                row.parentNode.removeChild(row);
            }
        </script>

    </body>

</html>
