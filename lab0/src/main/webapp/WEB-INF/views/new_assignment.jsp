<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student</title>
</head>
<body>
    <h2>Create new assignment</h2>

    <form action="NewAssignmentServlet" method="post">
        Title: <input type="text" name="title" value="${title}"><br>
        <input type="submit" value="Next">
    </form>
</body>
</html>
