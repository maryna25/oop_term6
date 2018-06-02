<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Teacher</title>
</head>
<body>
Hello, teacher <%=session.getAttribute("name")%>
<br><br>

<form action="/api/newcourse" method="get">
    <input type="submit" value="New course">
</form>

<h2>All courses:</h2>
<table border="1">
    <thead>
    <tr>
        <th>Title</th>
        <th>Program</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="course" items="${courses}">
        <tr>
            <td><c:out value="${course.getTitle()}"/></td>
            <td><c:out value="${course.getProgram()}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<a href="/api/assignments">My assignments</a>

</body>
</html>
