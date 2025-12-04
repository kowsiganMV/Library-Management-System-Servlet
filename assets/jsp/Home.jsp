<%@ page import="java.util.List" %>
<%@ page import="com.example.Model.Book" %>
<%@ page import="com.example.Model.User" %>

<!-- MAIN CONTENT -->
<%
    User u = (User) session.getAttribute("User");
    String role = (u != null) ? u.getRole() : "";
    List<Book> books = (List<Book>) request.getAttribute("Books");
    String Message = (String) request.getAttribute("Message");
    String BookRented = (String) request.getAttribute("BookRented");
%>
<% if(Message!=null){ %>
<div style=" padding: 12px; background-color: #ffdddd; color: #b30000; border-left: 5px solid #ff0000; border-radius: 5px; margin-top: 10px;"> <%= Message %>
</div>
<% } %>
<% if(BookRented!=null){ %>
<div style=" padding: 12px; background-color: #cdf5dd; color: green; border-left: 5px solid green; border-radius: 5px; margin-top: 10px;"> <%= BookRented %>
</div>
<% } %>

<div class="container">
     <h2>Available Books</h2>

     <table>
        <tr>
             <th>Book ID</th>
                <th>Name</th>
                <th>Author</th>
                <th>Category</th>
                <th>Available</th>
                <% if ("student".equals(role)) { %>
                    <th>Action</th>
                <% } %>
            </tr>
            <%
                if (books != null && !books.isEmpty()) {
                    for (Book b : books) {
            %>

                <tr>
                    <td><%= b.getBookId() %></td>
                    <td><%= b.getBookName() %></td>
                    <td><%= b.getAuthor() %></td>
                    <td><%= b.getCategory() %></td>
                    <td><%= b.getNoOfBooks() %></td>
                    <% if("student".equals(role)){ %>
                    <td>
                        <form action="rentbook" method="post" style="display: flex; align-items:">
                            <input type="hidden" name="bookid" value="<%= b.getBookId() %>">
                            <button type="submit" style="margin-left=5px;">Rent</button>
                        </form>
                    </td>
                    <% } %>
                </tr>

            <%
                    }
                } else {
            %>

                <tr>
                    <td colspan="5">No Books Available</td>
                </tr>

            <% } %>
        </table>
    </div>


