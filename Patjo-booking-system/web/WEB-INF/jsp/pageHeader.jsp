
<%-- 
 HEADER
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Patjo Booking System</title>
        <style>
            #header {
                background-color: #f2f2f2;
                padding: 10px;
                border: 2px solid #ddd;
                box-sizing: border-box;
                color: #333;
                height: 120px; 
                margin-top: 3px;
            }

            .role {
                font-size: 18px;
                color: #ff0000;
            }

            #header a {
                color: #333;
                text-decoration: none;
                transition: background-color 0.3s ease;
            }

            #header a:hover {
                color: #555;
                background-color: #ddd;
            }

        </style>
         <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
         <script>
               var socket = new WebSocket("ws://localhost:8080/Patjo-booking-system/endpoint");
                
                
                socket.onmessage = function (event) {
                    if (event.data === "updateBooking" + ${sessionScope.userId}) {
                        console.log(event.data);
                        console.log(${sessionScope.userId});
                        Swal.fire({
                        title: 'Your account has been deleted',
                        icon: 'warning',
                        showCancelButton: false,
                        confirmButtonText: 'OK',
                    }).then((result) => {
          
                        window.location.href = "/Patjo-booking-system/";
                        });
                    }else {
                        
                    }
                    
            };
             
        </script>
    </head>
    <body>
        <div id="header">
             <p class="role"><b>${sessionScope.role}</b></p>
            You are logged in as: <b>${sessionScope.username}</b>
            <br>
            <a href="/Patjo-booking-system/logout">Logout</a>
           
        </div>
    </body>
</html>
