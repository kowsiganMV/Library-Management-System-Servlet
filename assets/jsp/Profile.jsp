<%@ page import="com.example.Model.User" %>
<style>
.profile-field {
    font-size: 18px;
    margin: 12px 0;
    padding: 12px;
    background: #e8f5e9;   /* very light green */
    border-radius: 6px;
    border-left: 5px solid #66bb8f;
}

.profile-field span {
    font-weight: bold;
}

</style>
<%
    User u = (User) session.getAttribute("User");

    String username = (u != null) ? u.getName() : "";
    String email = (u != null) ? u.getEmail() : "";
    String role = (u != null) ? u.getRole() : "";
    String sessionid = session.getId();
    Integer rentedCount = (Integer) request.getAttribute("RentedCount");
    if (rentedCount == null) rentedCount = 0;
%>

<div class="container">
    <h2>Your Profile</h2>

    <div class="profile-field">
        <span>Username:</span> <%= username %>
    </div>

    <div class="profile-field">
        <span>Email:</span> <%= email %>
    </div>

    <div class="profile-field">
        <span>Role:</span> <%= role %>
    </div>

    <% if("student".equals(role)){ %>
    <div class="profile-field">
        <span>Number of Books Rented:</span> <%= rentedCount %>
    </div>
    <% } %>
</div>
