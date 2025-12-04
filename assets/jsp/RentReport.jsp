<%@ page import="java.util.*" %>
<%@ page import="com.example.Model.User" %>
<%@ page import="com.example.Model.Book" %>

<%
    List<String[]> bookList = (List<String[]>) request.getAttribute("bookList");

    String Message = (String) request.getAttribute("Message");

%>


<% if (Message != null) { %>
<div style="padding:12px; background:#ffdddd; color:#b30000; border-left:5px solid red; border-radius:5px; margin-top:10px;">
    <%= Message %>
</div>
<% } %>

<div class="container">
    <h2>Rented Books</h2>

    <table>
        <tr>
            <th>User Id</th>
            <th>User Name</th>
            <th>Book Id</th>
            <th>Book Name</th>
            <th>Author</th>
            <th>Category</th>
            <th>Count</th>
        </tr>

        <%
            if (bookList != null && !bookList.isEmpty()) {
            for(String[] book : bookList){
        %>

        <tr>
            <td><%= book[0] %></td>
            <td><%= book[1] %></td>
            <td><%= book[2] %></td>

            <td><%= book[3] %></td>
            <td><%= book[4] %></td>
            <td><%= book[5] %></td>
            <td><%= book[6] %></td>
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

