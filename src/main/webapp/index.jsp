<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Simple login form</title>
</head>
<body>

<form id="loginForm" action="Login" method="post">
    <h2>Log in</h2>
    <input type="text" name="username" placeholder="Username" required><br>
    <input type="password" name="password" placeholder="Password" required> <br>
    <input type="submit" value="Login">
    ${errormessages}
</form>


</body>
</html>