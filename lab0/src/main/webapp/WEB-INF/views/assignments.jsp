<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Assignments</title>
</head>
<body>
<br><br>

<h2>All assignmets:</h2>
<table border="1">
    <thead>
    <tr>
        <th>Course</th>
        <th>Student</th>
        <th>Mark</th>
        <th>Response</th>
        <th>Status</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ass" items="${assignments}">
        <tr>
            <form action="UpdateAssignmentServlet" method="post">
                <td><input type="text" name="title" value="<c:out value="${ass.getCourse().getTitle()}"/>"><br></td>
                <td><input type="text" name="surname" value="<c:out value="${ass.getStudent().getSurname()}"/>"><br></td>
                <td><input type="text" name="mark" value="<c:out value="${ass.getMark()}"/>"><br></td>
                <td><input type="text" name="response" value="<c:out value="${ass.getResponse()}"/>"><br></td>
                <td><c:out value="${ass.getStatus()}"/></td>
                <td><input type="submit" value="Next"></td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
