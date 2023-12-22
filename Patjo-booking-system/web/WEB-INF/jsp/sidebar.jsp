<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
            .sidebar {
                background-color: #001f3f;
                height: 100%;
                width: 200px;
                position: fixed;
                z-index: 1;
                top: 130px;
                left: 0;
                overflow-x: hidden;
                padding-top: 20px;
            }

            .sidebar a {
                padding: 6px 6px 6px 32px;
                text-decoration: none;
                font-size: 20px;
                color: #818181;
                display: block;
            }

            .sidebar a:hover {
                color: #f1f1f1;
            }

        </style>
    </head>

    <body>

        <div class="sidebar">
            <c:choose>
                <c:when test="${sessionScope.role eq 'Admin'}">
                    <a href="/Patjo-booking-system/adminmain">Main Page</a>
                    <a href="/Patjo-booking-system/users/showusers">Handle Users</a>
                    <a href="/Patjo-booking-system/presentationlist">See Lists</a>
                    <a href="/Patjo-booking-system/presentationlist/create">Create new List</a>
                </c:when>
                <c:otherwise>
                    <a href="/Patjo-booking-system/main">Main Page</a>
                    <a href="/Patjo-booking-system/mybookings">My bookings</a>
                    <a href="/Patjo-booking-system/showcourses">Show courses</a>
                </c:otherwise>
            </c:choose>

        </div>
    </body>

</html>
