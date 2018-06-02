<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>lab0</title>
</head>
<body>

<h1>Log in to continue</h1>
<h2>${msg}</h2>

<form action="LoginServlet" method="post">
    Login: <input type="text" name="login">
    <br>
    Password: <input type="password" name="password">
    <br><br>
    <input type="submit" value="Login">
</form>

<a href="/api/sign_up">Dont have an account?</a>

</body>
</html>
