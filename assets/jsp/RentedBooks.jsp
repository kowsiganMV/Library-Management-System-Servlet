<%@ page import="java.util.*" %>
<%@ page import="com.example.Model.User" %>
<%@ page import="com.example.Model.Book" %>

<%
    List<String[]> bookList = (List<String[]>) request.getAttribute("bookList");

    String Message = (String) request.getAttribute("Message");

    String SuccessMessage = (String) request.getAttribute("SuccessMessage");
%>


<% if (Message != null) { %>
<div style="padding:12px; background:#ffdddd; color:#b30000; border-left:5px solid red; border-radius:5px; margin-top:10px;">
    <%= Message %>
</div>
<% } %>
<% if (SuccessMessage != null) { %>
<div style=" padding: 12px; background-color: #cdf5dd; color: green; border-left: 5px solid green; border-radius: 5px; margin-top: 10px;">
    <%= SuccessMessage %>
</div>
<% } %>

<div class="container">
    <h2>Rented Books</h2>

    <table>
        <tr>
            <th>Book Name</th>
            <th>Author</th>
            <th>Category</th>
            <th>Count</th>
            <th>Action</th>
        </tr>

        <%
            if (bookList != null && !bookList.isEmpty()) {
            for(String[] book : bookList){
        %>

        <tr>
            <td><%= book[1] %></td>
            <td><%= book[2] %></td>

            <td><%= book[3] %></td>
            <td><%= book[4] %></td>
            <td>
                <form action="returnbook" method="post" style="display: flex; align-items:">
                    <input type="hidden" name="bookid" value="<%= book[0] %>">
                    <button type="submit" style="margin-left=5px;">Return</button>
                </form>
            </td>
        </tr>

        <%
                }
            } else {
        %>

        <tr>
            <td colspan="7" style="text-align:center;">No Rented Books</td>
        </tr>

        <% } %>

    </table>
</div>

