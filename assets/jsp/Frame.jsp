<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Library Home</title>

    <style>
        body {

                    margin: 0;
                    font-family: Arial, sans-serif;
                    background: #f1fff4; /* faint light green */
                }

                /* Navbar */
                .navbar {
                    background: #66bb8f; /* medium light green */
                    padding: 15px;
                    display: flex;
                    gap: 20px;
                    justify-content: center;
                }

                .navbar a {
                    text-decoration: none;
                    color: white;
                    font-weight: bold;
                    font-size: 16px;
                }

                /* Main container */
                .container {
                    margin: 30px auto;
                    width: 80%;
                    background: white;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 0 10px #c8e6c9;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 15px;
                }

                th, td {
                    border: 1px solid #a5d6a7;
                    padding: 10px;
                    text-align: left;
                }

                th {
                    background: transparent;
                }
                button {
                 width: 100%;
                 padding: 12px;
                 border: none;
                 background: #4EA685;
                 color: white;
                 font-size: 16px;
                 border-radius: 5px;
                 cursor: pointer;
                 margin-top: 15px;
             }

             button:hover {
                 background: #3c8c6c;
             }
    </style>
</head>
<body>

    <!-- Use dynamic include -->
    <jsp:include page="Header.jsp" />

    <%
    String currentPage = (String) request.getAttribute("Page");
    %>
    <% if ("home".equals(currentPage)) { %>
    <jsp:include page="Home.jsp" />
    <% } else if ("profile".equals(currentPage)) { %>
    <jsp:include page="Profile.jsp" />
    <% } else if ("rentedbooks".equals(currentPage)) { %>
    <jsp:include page="RentedBooks.jsp" />
    <% } else if ("addbook".equals(currentPage)) { %>
    <jsp:include page="AddBook.jsp" />
    <% } else if ("rentreport".equals(currentPage)) { %>
    <jsp:include page="RentReport.jsp" />
    <% } %>

</body>
</html>
