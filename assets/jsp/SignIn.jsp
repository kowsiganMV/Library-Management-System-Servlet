<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title> Library Management System </title>
        <style>
            body {
                font-family: Arial;
                background: #f2f2f2;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .form-container {
                background: white;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px #cccccc;
                width: 300px;
            }

            .form-container h2 {
                text-align: center;
                margin-bottom: 20px;
                color: #333;
            }

            input[type="text"], input[type="password"] {
                width: 100%;
                padding: 12px;
                margin: 8px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
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
            }

            button:hover {
                background: #3c8c6c;
            }

        </style>
    </head>
    <body>
         <div class="form-container">
            <h2>SignIn</h2>


            <% String Message = (String) request.getAttribute("Message"); %>
            <form action="signin" method="post">
                <input type="text" name="username" placeholder="Enter Username" required>

                <input type="password" name="password" placeholder="Enter Password" required>
                <% if(Message!=null){ %>
                  <div style=" padding: 12px; background-color: #ffdddd; color: #b30000; border-left: 5px solid #ff0000; border-radius: 5px; margin-top: 10px;"> <%= Message %>
                  </div>
                <% } %>
                <button type="submit">SingIn</button>

            </form>

            <a href="signup">
                <button type="button">SignUp</button>
            </a>
         </div>
    </body>
</html>
