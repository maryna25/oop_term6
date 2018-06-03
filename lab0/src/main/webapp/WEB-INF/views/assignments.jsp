<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/custom_tags/assignment.tld" prefix="ct" %>
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
        <tr><ct:assignment item="${ass}" /></tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
