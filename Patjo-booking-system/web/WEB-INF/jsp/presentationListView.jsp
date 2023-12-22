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
            .deleted-container {
                background-color: lightcoral;
                padding: 8px;
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
            .form-container {
                display: flex;
                gap: 8px; 
            }

        </style>
    </head>
    <body>
        <div class="main">
            <h1>Presentation Lists</h1>
            <c:if test="${not empty deletedlist}">
                <div class="deleted-container">
                    <p>List Deleted: The chosen presentation list has been removed successfully.</p>
                    <p>All related bookings have been eliminated.</p>

                </div>
                <br>
            </c:if>
            <c:choose>
                <c:when test="${not empty selectedPresentationList}">
                    <h3>List number: ${listId}</h3>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Type of Session</th>
                                <th>Location</th>
                                <th>Time slot</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="booking" items="${selectedPresentationList}">
                                <tr>
                                    <td>${booking.id}</td>
                                    <td>${booking.typeOfSession}</td>
                                    <td>${booking.location}</td>
                                    <td>${booking.startTime}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not booking.isAvailable}">
                                                BOOKED
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <input type="button" value="Back" onclick="toPresentationList()" class="button">
                </c:when>

                <c:otherwise>

                    <table border="1">
                        <thead>
                            <tr>
                                <th>Choose</th>
                                <th>List ID</th>
                                <th>Course</th>
                                <th>Creator</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="list" items="${activePresentationLists}">
                                <tr>
                                    <td>
                                        <label>
                                            <input type="radio" name="selectedList" value="${list.listId}">
                                        </label>
                                    </td>
                                    <td>${list.listId}</td>
                                    <td>${list.courseName}</td>
                                    <td>${list.creatorName}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <div class="form-container">

                        <form id="showListForm" action="showpresentationlist" method="get" onsubmit="return validateForm()">
                            <input type="submit" value="Show List" class="button">
                        </form>


                        <form id="deleteForm" action="deleteselectedlist" method="post" onsubmit="return deleteList()">
                            <input type="hidden" name="selectedList" id="listIdToDelete" value="">
                            <input type="submit" value="Delete List" class="button">
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>

    <script>
        function toPresentationList() {
            window.location.href = '/Patjo-booking-system/presentationlist';
        }

        function validateForm() {
            var selectedList = document.querySelector('input[name="selectedList"]:checked');

            if (!selectedList) {
                alert('Please select a list!');
                return false; // Prevent form submission if no course is selected
            }

            return true; // Proceed with form submission
        }

        function deleteList() {
            var selectedListToRemove = document.querySelector('input[name="selectedList"]:checked');

            if (!selectedListToRemove) {
                alert('Please select a list!');
                return false; // Prevent form submission if no list is selected
            }

            var listIdToDelete = selectedListToRemove.value;

            // Set the value of the hidden input
            document.getElementById('listIdToDelete').value = listIdToDelete;

            // Proceed with form submission for the second form
            document.getElementById('deleteForm').submit();

            // Prevent the first form from submitting
            return false;
        }

    </script>
</html>
