<%@ page import="com.example.Model.User" %>

<%
    User u = (User) session.getAttribute("User");
    String role = (u != null) ? u.getRole() : "";
%>

<!-- NAVBAR -->
<div class="navbar">
    <a href="home">Home</a>
    <a href="profile">Profile</a>

    <% if ("student".equals(role)) { %>
        <a href="rentedbooks">Rented Books</a>
    <% } else { %>
        <a href="addbook">Add Book</a>
        <a href="rentreport">Rent Report</a>
    <% } %>

    <a href="logout" style="color: red;">Logout</a>
</div>
