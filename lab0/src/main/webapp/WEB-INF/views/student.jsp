<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />
<html lang="${language}">
<head>
    <title><fmt:message key="student.h1.title" /></title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ua" ${language == 'ua' ? 'selected' : ''}>Ukrainian</option>
    </select>
</form>
<fmt:message key="student.h1.hello" /> <%=session.getAttribute("name")%>
<br><br>
<form action="/api/newassignment" method="get">
    <input type="submit" value="<fmt:message key="student.link.new_assignment" />">
</form>

<h2><fmt:message key="student.h2.courses" />:</h2>
<table border="1">
    <thead>
    <tr>
        <th><fmt:message key="student.table.title" /></th>
        <th><fmt:message key="student.table.program" /></th>
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

</body>
</html>
