<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error Page</title>
    <style>
        body { font-family: Arial; background: #f2f2f2; padding: 40px; }
        .box { background: white; padding: 20px; border-radius: 10px; width: 500px; margin: auto; }
        h2 { color: red; }
    </style>
</head>
<body>
<div class="box">
    <h2>Oops, Something went wrong!</h2>

    <p><b>Error Type:</b> <%= exception.getClass().getName() %></p>
    <p><b>Message:</b> <%= exception.getMessage() %></p>

    <p><a href="index.jsp">Go Back to Home</a></p>
</div>
</body>
</html>
