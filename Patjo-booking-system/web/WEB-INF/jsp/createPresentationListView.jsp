<%-- 
 Admin - create new presentation list view
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="pageHeader.jsp" %> 
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
            #generateList label {
                display: inline-block;
                width: 150px; 
                margin-bottom: 10px; 
            }

            #generateList input,
            #generateList select {
                width: 200px; 
                margin-bottom: 10px; 
            }
            .response-container{
                background-color: lightsteelblue;
            }
             td {
                padding: 4px;

            }
            th {
                padding: 4px;
                text-align: center;
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
            
            <hr>
            
            <h1>Quick Presentation List Creator</h1>
            <form id="generateList" action="generatepresentationlist" method="post" onsubmit="return validate()">
                <label>Course: </label>
                 <select id="course" name="course" required>
                    <option value="" disabled selected>Select a course</option>
                    <c:forEach var="course" items="${courseList}">
                        
                        <option value="${course.courseId}">${course.courseName}</option>
                    </c:forEach>
                </select>
                <br>
                <label>Number of time slots: </label>
                    <input type="number" name="numberOfTimeSlots" required min="1" step="1">
                    <br>
                  <label>Type of Session:</label>
                    <input type="text" name="typeOfSession" required>
                    <br>

                    <label>Location:</label>
                    <input type="text" name="location" required>
                    <br>

                    <label>Start Time:</label>
                    <input type="datetime-local" name="startTime" required>
                    <br>
                    <label>Interval: </label>
                    <input type="number" name="intervalBetweenTimeSlots" required min="0" step="5">
                <br>
                <br>
                <input type="submit" value="Submit" class="button">
                
                <div class="response-container">  
                    <h3 id="listIdHeader"></h3>  
                    <div id="listContainer"></div>
                </div>
              
        </div>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script>
            window.onload = function () {
                addRow();
            };
            
       $(document).ready(function() {
            $("#generateList").submit(function(event) {
                event.preventDefault();
                handleFormSubmit("generatepresentationlist", "#generateList");
            });

            $("#bookingForm").submit(function(event) {
                event.preventDefault();
                handleFormSubmit("savepresentationlist", "#bookingForm");
            });

    function handleFormSubmit(url, formId) {
        $.ajax({
            type: "POST",
            url: url,
            data: $(formId).serialize(),
            success: function(data) {
                console.log(data);
                var listId = data.listId;
                var courseName = data.courseName;
                var bookingDTOList = data.bookingDTOList;

                $("#listIdHeader").text("List Created! \n\
                        List ID: " + listId);

                var tableHtml = "<table><thead><tr>\n\
                        <th>Course</th>\n\
                        <th>Type of Session</th>\n\
                        <th>Location</th>\n\
                        <th>Start Time</th>\n\
                        </tr></thead><tbody>";
                $.each(bookingDTOList, function(index, bookingDTO) {
                    tableHtml += "<tr>\n\
                            <td>" + courseName + "</td>\n\
                            <td>" + bookingDTO.typeOfSession + "</td>\n\
                            <td>" + bookingDTO.location + "</td>\n\
                            <td>" + bookingDTO.startTime + "</td>\n\
                            </tr>";
                });
                tableHtml += "</tbody></table>";

                $("#listContainer").html(tableHtml);

                var form = $(formId);
                form[0].reset();
            }
        });
    }
});

            
            function validateForm() { 
                var courseId = document.getElementById("courseId").value;

                if (courseId === "") {
                    alert("Please select a course before submitting the form.");
                    return false;
                } else {
                    return true;
                }
            }
              function validate() { 
                var courseId = document.getElementById("course").value;

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
                    <br>
                    <br>
                   
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
