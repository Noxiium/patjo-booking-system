<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %> 
<%@ include file="adminSidebar.jsp" %> 

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

                .list-section {
                    margin-bottom: 20px;
                }

                .list-title {
                    font-weight: bold;
                    margin-bottom: 5px;
                }

                .list-item {
                    margin-left: 20px;
                }
                
            </style>
        </head>
        <body>
            <div class="main">
                <h1>Admin page</h1>

                <div class="list-section">
                    <div class="list-title">Handle Users:</div>
                    <div class="list-item">- Create: Add new users to the system.</div>
                    <div class="list-item">- See: View details of existing users.</div>
                    <div class="list-item">- Remove: Delete users from the system.</div>
                </div>

                <div class="list-section">
                    <div class="list-title">See Lists:</div>
                    <div class="list-item">- View: Explore details of active presentation lists.</div>
                    <div class="list-item">- Remove: Delete presentation lists as needed.</div>
                </div>

                <div class="list-section">
                    <div class="list-title">Create List:</div>
                    <div class="list-item">- Create: Generate a new presentation list for a course.</div>
                </div>

            </div>
        </body>
    </html>
