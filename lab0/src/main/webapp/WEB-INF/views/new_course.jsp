<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Teacher</title>
</head>
<body>
    <h2>Create new course</h2>

    <form action="/api/newcourse" method="post">
        Title: <input type="text" name="title" value="${title}"><br>
        Program: <input type="text" name="program" value="${program}"><br>
        <input type="submit" value="Next">
    </form>
</body>
</html>
