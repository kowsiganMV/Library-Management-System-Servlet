<head>
        <title> Addbook</title>
        <style>
            .form-container {
                margin: 30px auto;
                background: white;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px #cccccc;
                width: 40vh;
            }

            .form-container h2 {
                text-align: center;
                margin-bottom: 20px;
                color: #333;
            }

            input[type="text"], input[type="password"] {
                width: 95%;
                padding: 12px;
                margin: 8px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
            }


        </style>
</head>
<body>
<div class="form-container">
            <h2>Add Book</h2>


            <%
            String Message = (String) request.getAttribute("Message");
            String BookAdded = (String) request.getAttribute("BookAdded");
            %>
            <form action="addbook" method="post">
                <% if(BookAdded!=null){ %>
                     <div style=" padding: 12px; background-color: #cdf5dd; color: green; border-left: 5px solid green; border-radius: 5px; margin-top: 10px;"> <%= BookAdded %>
                     </div>
                <% } %>
                <input type="text" name="bookname" placeholder="Enter Book Name" required>

                <input type="text" name="author" placeholder="Enter Author" required>

                <input type="text" name="category" placeholder="Enter Category" required>

                <input type="text" name="noofbooks" placeholder="Enter No of Books" required>

                <% if(Message!=null){ %>
                  <div style=" padding: 12px; background-color: #ffdddd; color: #b30000; border-left: 5px solid #ff0000; border-radius: 5px; margin-top: 10px;"> <%= Message %>
                  </div>
                <% } %>
                <button type="submit">Add Book</button>

            </form>

         </div>
</body>

